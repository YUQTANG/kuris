package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IStockIncomeBillService {
    void saveOrUpdate(StockIncomeBill stockIncomeBill);

    void delete(Long id);

    StockIncomeBill get(Long id);

    List<StockIncomeBill> list();

    PageResult query(QueryObject qo);

    /**
     * 根据订单ID进行审核
     * @param id
     */
    void audit(Long id);
}
