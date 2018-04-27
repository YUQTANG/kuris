package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    @Autowired
    private StockIncomeBillMapper billMapper;//入库
    @Autowired
    private StockIncomeBillItemMapper itemMapper;//明细
    @Autowired
    private ProductStockMapper psMapper;//库存

    public void saveOrUpdate(StockIncomeBill stockIncomeBill) {
        if (stockIncomeBill.getId() == null) {
            saveBill(stockIncomeBill);
        } else {
            updateBill(stockIncomeBill);
        }
    }

    //新增操作
    private void saveBill(StockIncomeBill stockIncomeBill) {
        //设置录入人
        stockIncomeBill.setInputUser(UserContext.getCurrentUser());
        //设置录入时间
        stockIncomeBill.setInputTime(new Date());
        //设置入库状态
        stockIncomeBill.setStatus(StockIncomeBill.NORMAL);
        //设置总金额和总数量两个变量
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        //循环迭代明细
        for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
            BigDecimal number = item.getNumber();
            BigDecimal costPrice = item.getCostPrice();
            BigDecimal amount = costPrice.multiply(number)
                    .setScale(2, RoundingMode.HALF_UP);
            //设总金额进去 item
            item.setAmount(amount);

            //叠加数量和金额
            totalNumber = totalNumber.add(number);
            totalAmount = totalAmount.add(amount);
        }
        //把得到的总金额和总数量设进对象中
        stockIncomeBill.setTotalAmount(totalAmount);
        stockIncomeBill.setTotalNumber(totalNumber);
        //保存到入库中
        billMapper.insert(stockIncomeBill);

        //再次迭代item
        for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
            item.setBillId(stockIncomeBill.getId());
            //保存到明细中
            itemMapper.insert(item);
        }
    }

    //更新操作
    private void updateBill(StockIncomeBill stockIncomeBill) {
        /**
         * 更新核心
         * 重新计算明细的小计 单据的总数量和总价格
         */
        StockIncomeBill bill = billMapper.selectByPrimaryKey(stockIncomeBill.getId());
        //在未审核的状态下才能进行更新
        if (bill.getStatus() == StockIncomeBill.NORMAL) {
            //先删除旧入库的明细
            itemMapper.deleteByBillId(stockIncomeBill.getId());
            //设置总价和总数量的变量
            BigDecimal totalNumber = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            //循环迭代items
            for (StockIncomeBillItem item : stockIncomeBill.getItems()) {
                BigDecimal amount = item.getCostPrice().multiply(item.getNumber())
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
            //设置总金额和总数量到stockIncomeBill对象中
            stockIncomeBill.setTotalNumber(totalNumber);
            stockIncomeBill.setTotalAmount(totalAmount);
            //保存到入库到数据库
            billMapper.updateByPrimaryKey(stockIncomeBill);
        }
    }

    //删除操作
    public void delete(Long id) {
        //判断需要时未审核状态才能够被删除
        StockIncomeBill bill = billMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockIncomeBill.NORMAL) {
            //先删除明细 再删除入库
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
        //判断需要时未审核状态才能够被审核
        StockIncomeBill bill = billMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == StockIncomeBill.NORMAL) {
            //当前仓库
            Depot depot = bill.getDepot();
            //遍历明细 添加商品到库存中
            for (StockIncomeBillItem item : bill.getItems()) {
                //当前商品对象
                Product p = item.getProduct();
                ProductStock ps = psMapper.selectByProductIdAndDepotId(p.getId(), depot.getId());
                if (ps == null) {
                    ProductStock stock = new ProductStock();
                    //直接添加到库存中
                    stock.setPrice(item.getCostPrice());//库存价格
                    stock.setStoreNumber(item.getNumber());//库存数量
                    stock.setAmount(item.getNumber().multiply(item.getCostPrice())
                            .setScale(2,RoundingMode.HALF_UP));//当前商品库存总价值
                    stock.setProduct(p);
                    stock.setDepot(depot);
                    //保存到数据库中
                    psMapper.insert(stock);
                }else {
                    //当前仓库有该商品 使用移动加权平均添加
                    BigDecimal totalNumber = item.getNumber().add(ps.getStoreNumber());//明细商品数量 + 库存数量
                    BigDecimal totalAmount = item.getNumber().multiply(item.getCostPrice())
                            .setScale(2, RoundingMode.HALF_UP).add(ps.getAmount());//明细总价值 + 库存总价值
                    BigDecimal price = totalAmount.divide(totalNumber).setScale(2, RoundingMode.HALF_UP);
                    //重新给ps设进值
                    ps.setAmount(totalAmount);
                    ps.setStoreNumber(totalNumber);
                    ps.setPrice(price);
                    //更新库存信息
                    psMapper.updateByPrimaryKey(ps);
                }
            }
            //============入库订单状态==============
            //设置审核状态
             bill.setStatus(StockIncomeBill.AUDIT);
            //设置审核人
            bill.setAuditor(UserContext.getCurrentUser());
            //设置审核时间
            bill.setAuditTime(new Date());
            //更新入库单状态
            billMapper.audit(bill);
        }
    }

    public StockIncomeBill get(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    public List<StockIncomeBill> list() {
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
