package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill record);

    StockOutcomeBill selectByPrimaryKey(Long id);

    List<StockOutcomeBill> selectAll();

    int updateByPrimaryKey(StockOutcomeBill record);

    Integer queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    /**
     * 审核功能
     * @param StockOutcomeBill
     */
    void audit(StockOutcomeBill StockOutcomeBill);
}