package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface OrderBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderBill record);

    OrderBill selectByPrimaryKey(Long id);

    List<OrderBill> selectAll();

    int updateByPrimaryKey(OrderBill record);

    Integer queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    /**
     * 审核功能
     * @param orderBill
     */
    void audit(OrderBill orderBill);
}