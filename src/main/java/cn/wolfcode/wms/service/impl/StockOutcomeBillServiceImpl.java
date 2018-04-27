package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    @Autowired
    private StockOutcomeBillMapper billMapper;//出库
    @Autowired
    private StockOutcomeBillItemMapper itemMapper;//明细
    @Autowired
    private ProductStockMapper psMapper;//库存
    @Autowired
    private SaleAccountMapper saMapper;//账单

    public void saveOrUpdate(StockOutcomeBill stockOutcomeBill) {
        if (stockOutcomeBill.getId() == null) {
            saveBill(stockOutcomeBill);
        } else {
            updateBill(stockOutcomeBill);
        }
    }

    //新增操作
    private void saveBill(StockOutcomeBill stockOutcomeBill) {
        //设置录入人
        stockOutcomeBill.setInputUser(UserContext.getCurrentUser());
        //设置录入时间
        stockOutcomeBill.setInputTime(new Date());
        //设置入库状态
        stockOutcomeBill.setStatus(StockOutcomeBill.NORMAL);
        //设置总金额和总数量两个变量
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        //循环迭代明细
        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            BigDecimal number = item.getNumber();
            BigDecimal salePrice = item.getSalePrice();
            BigDecimal amount = salePrice.multiply(number)
                    .setScale(2, RoundingMode.HALF_UP);
            //设总金额进去 item
            item.setAmount(amount);

            //叠加数量和金额
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        //把得到的总金额和总数量设进对象中
        stockOutcomeBill.setTotalAmount(totalAmount);
        stockOutcomeBill.setTotalNumber(totalNumber);
        //保存到入库中
        billMapper.insert(stockOutcomeBill);

        //再次迭代item
        for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
            item.setBillId(stockOutcomeBill.getId());
            //保存到明细中
            itemMapper.insert(item);
        }
    }

    //更新操作
    private void updateBill(StockOutcomeBill stockOutcomeBill) {
        /**
         * 更新核心
         * 重新计算明细的小计 单据的总数量和总价格
         */
        StockOutcomeBill bill = billMapper.selectByPrimaryKey(stockOutcomeBill.getId());
        //在未审核的状态下才能进行更新
        if (bill.getStatus() == StockOutcomeBill.NORMAL) {
            //先删除旧入库的明细
            itemMapper.deleteByBillId(stockOutcomeBill.getId());
            //设置总价和总数量的变量
            BigDecimal totalNumber = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            //循环迭代items
            for (StockOutcomeBillItem item : stockOutcomeBill.getItems()) {
                BigDecimal amount = item.getSalePrice().multiply(item.getNumber())
                        .setScale(2, RoundingMode.HALF_UP);
                //设值进去amount
                item.setAmount(amount);
                //叠加总金额和总数量
                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(item.getNumber());
                //把明细保存到数据库
                item.setBillId(bill.getId());
                itemMapper.insert(item);
            }
            //设置总金额和总数量到stockOutcomeBill对象中
            stockOutcomeBill.setTotalNumber(totalNumber);
            stockOutcomeBill.setTotalAmount(totalAmount);
            //保存到入库到数据库
            billMapper.updateByPrimaryKey(stockOutcomeBill);
        }
    }

    //删除操作
    public void delete(Long id) {
        //判断需要时未审核状态才能够被删除
        StockOutcomeBill bill = billMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockOutcomeBill.NORMAL) {
            //先删除明细 再删除出库
            itemMapper.deleteByBillId(id);
            billMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 审核入库
     * 核心 设置审核时间 审核人 状态改为2
     * @param id
     */
    public void audit(Long id) {
        StockOutcomeBill bill = billMapper.selectByPrimaryKey(id);
        //判断需要时未审核状态才能够被审核
        if (bill.getStatus() == StockOutcomeBill.NORMAL) {
            //获取当前仓库
            Depot depot = bill.getDepot();
            //获取当前出库单的明细
            for (StockOutcomeBillItem item : bill.getItems()) {
                //获取当前商品
                Product p = item.getProduct();
                //查询当前仓库是否有当前商品的库存
                ProductStock ps = psMapper.selectByProductIdAndDepotId(p.getId(), depot.getId());
                //判断在仓库中是否有当前商品的库存
                if (ps == null) {
                    //ps为null 则说明当前库存没有该件商品 抛出一个异常返回界面
                    String msg = "当前: [" + depot.getName() + "],没有该商品: [" + p.getName() + "]的库存";
                    throw new RuntimeException(msg);
                }
                //ps不为null 则判断出库单上商品的数量是否大于当前库存的数量
                if (item.getNumber().compareTo(ps.getStoreNumber()) > 0) {
                    //当前出库商品数量超过库存数量 抛出一个异常
                    String msg = "当前: [" + depot.getName() + "],库存数量: [" +
                            ps.getStoreNumber() + "],无法满足出库数量: [" + item.getNumber() + "]的申请";
                    throw new RuntimeException(msg);
                }
                //条件满足出库要求 可以通过审核
                //库存数量 - 明细数量  库存总值 - 明细总值 重新更新回库存
                BigDecimal number = ps.getStoreNumber().subtract(item.getNumber());//剩余库存数量
                BigDecimal amount = ps.getAmount().subtract(item.getAmount());
                //更新库存
                ps.setStoreNumber(number);
                ps.setAmount(amount);
                psMapper.updateByPrimaryKey(ps);

                //===============创建账单=================
                SaleAccount sa = new SaleAccount();
                sa.setVdate(new Date());
                sa.setNumber(item.getNumber());
                sa.setCostPrice(ps.getPrice());
                sa.setCostAmount(ps.getPrice().multiply(item.getNumber())
                        .setScale(2,RoundingMode.HALF_UP));
                sa.setSalePrice(item.getSalePrice());
                sa.setSaleAmount(item.getNumber().multiply(item.getSalePrice())
                        .setScale(2,RoundingMode.HALF_UP));
                sa.setProduct(p);
                sa.setSaleman(bill.getInputUser());
                sa.setClient(bill.getClient());
                saMapper.insert(sa);
            }
            //更新出库单状态 时间 和 审核人
            bill.setStatus(StockOutcomeBill.AUDIT);
            bill.setAuditTime(new Date());
            bill.setAuditor(UserContext.getCurrentUser());
            //完成审核
            billMapper.audit(bill);
        }
    }

    public StockOutcomeBill get(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> list() {
        return billMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = billMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = billMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
