package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill record);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    int updateByPrimaryKey(StockIncomeBill record);

    Integer queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    /**
     * 审核功能
     * @param StockIncomeBill
     */
    void audit(StockIncomeBill StockIncomeBill);
}