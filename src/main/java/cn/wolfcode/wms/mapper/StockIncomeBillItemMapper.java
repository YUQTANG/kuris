package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBillItem;
import java.util.List;

public interface StockIncomeBillItemMapper {

    int insert(StockIncomeBillItem record);

    /**
     * 根据订单ID删除明细
     * @param billId
     */
    void deleteByBillId(Long billId);
}