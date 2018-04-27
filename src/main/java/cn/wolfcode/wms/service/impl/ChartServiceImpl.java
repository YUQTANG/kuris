package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.mapper.ChartMapper;
import cn.wolfcode.wms.mapper.ClientMapper;
import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.OrderSaleQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IChartService;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    private ChartMapper chartMapper;

    public List<Map<String, Object>> selectOrderChart(OrderChartQueryObject qo) {
        return chartMapper.selectOrderChart(qo);
    }

    public List<Map<String, Object>> selectOrderSale(OrderSaleQueryObject qo) {
        return chartMapper.selectOrderSale(qo);
    }
}
