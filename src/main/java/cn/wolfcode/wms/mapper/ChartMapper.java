package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.OrderSaleQueryObject;
import cn.wolfcode.wms.query.QueryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ChartMapper {

    List<Map<String,Object>> selectOrderChart(OrderChartQueryObject qo);

    List<Map<String,Object>> selectOrderSale(OrderSaleQueryObject qo);
}
