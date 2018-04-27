package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductStockMapper {

    int insert(ProductStock record);

    void updateByPrimaryKey(ProductStock record);

    ProductStock selectByProductIdAndDepotId(@Param("ProductId") Long ProductId,
                                        @Param("DepotId") Long DepotId);

    int queryForCount(ProductStockQueryObject qo);

    List<Map<String,Object>> queryForList(ProductStockQueryObject qo);
}