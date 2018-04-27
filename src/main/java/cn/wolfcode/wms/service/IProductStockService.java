package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IProductStockService {
    PageResult queryForList(ProductStockQueryObject qo);
}
