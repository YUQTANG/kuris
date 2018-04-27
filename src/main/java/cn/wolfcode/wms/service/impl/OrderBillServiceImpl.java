package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper billMapper;//订单
    @Autowired
    private OrderBillItemMapper itemMapper;//明细

    public void saveOrUpdate(OrderBill orderBill) {
        if (orderBill.getId() == null) {
            saveBill(orderBill);
        } else {
            updateBill(orderBill);
        }
    }

    //新增操作
    private void saveBill(OrderBill orderBill) {
        //设置录入人
        orderBill.setInputUser(UserContext.getCurrentUser());
        //设置录入时间
        orderBill.setInputTime(new Date());
        //设置订单状态
        orderBill.setStatus(OrderBill.NORMAL);
        //设置总金额和总数量两个变量
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        //循环迭代明细
        for (OrderBillItem item : orderBill.getItems()) {
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
        orderBill.setTotalAmount(totalAmount);
        orderBill.setTotalNumber(totalNumber);
        //保存到订单中
        billMapper.insert(orderBill);

        //再次迭代item
        for (OrderBillItem item : orderBill.getItems()) {
            item.setBillId(orderBill.getId());
            //保存到明细中
            itemMapper.insert(item);
        }
    }

    //更新操作
    private void updateBill(OrderBill orderBill) {
        /**
         * 更新核心
         * 重新计算明细的小计 单据的总数量和总价格
         */
        OrderBill bill = billMapper.selectByPrimaryKey(orderBill.getId());
        //在未审核的状态下才能进行更新
        if (bill.getStatus() == OrderBill.NORMAL) {
            //先删除旧订单的明细
            itemMapper.deleteByBillId(orderBill.getId());
            //设置总价和总数量的变量
            BigDecimal totalNumber = BigDecimal.ZERO;
            BigDecimal totalAmount = BigDecimal.ZERO;
            //循环迭代items
            for (OrderBillItem item : orderBill.getItems()) {
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
            //设置总金额和总数量到orderBill对象中
            orderBill.setTotalNumber(totalNumber);
            orderBill.setTotalAmount(totalAmount);
            //保存到订单到数据库
            billMapper.updateByPrimaryKey(orderBill);
        }
    }

    //删除操作
    public void delete(Long id) {
        //判断需要时未审核状态才能够被删除
        OrderBill bill = billMapper.selectByPrimaryKey(id);
        if (bill.getStatus() == OrderBill.NORMAL) {
            //先删除明细 再删除订单
            itemMapper.deleteByBillId(id);
            billMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 审核订单
     * 核心 设置审核时间 审核人 状态改为2
     * @param orderBill
     */
    public void audit(OrderBill orderBill) {
        //判断需要时未审核状态才能够被审核
        OrderBill bill = billMapper.selectByPrimaryKey(orderBill.getId());
        if (bill.getStatus() == OrderBill.NORMAL) {
            //设置审核时间
            orderBill.setAuditTime(new Date());
            //设置审核人
            orderBill.setAuditor(UserContext.getCurrentUser());
            //设置审核状态
            orderBill.setStatus(OrderBill.AUDIT);
            billMapper.audit(orderBill);
        }
    }

    public OrderBill get(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    public List<OrderBill> list() {
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
