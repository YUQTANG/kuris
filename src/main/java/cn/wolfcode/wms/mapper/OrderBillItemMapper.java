package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.OrderBillItem;
import java.util.List;

public interface OrderBillItemMapper {

    int insert(OrderBillItem record);

    /**
     * 根据订单ID删除明细
     * @param billId
     */
    void deleteByBillId(Long billId);
}