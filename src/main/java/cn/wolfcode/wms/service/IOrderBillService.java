package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IOrderBillService {
    void saveOrUpdate(OrderBill orderBill);

    void delete(Long id);

    OrderBill get(Long id);

    List<OrderBill> list();

    PageResult query(QueryObject qo);

    /**
     * 根据订单ID进行审核
     * @param orderBill
     */
    void audit(OrderBill orderBill);
}
