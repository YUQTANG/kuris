package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBillItem;

public interface StockOutcomeBillItemMapper {

    int insert(StockOutcomeBillItem record);

    /**
     * 根据订单ID删除明细
     * @param billId
     */
    void deleteByBillId(Long billId);
}