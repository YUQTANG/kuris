package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.mapper.DepartmentMapper;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductStockServiceImpl implements IProductStockService {
    @Autowired
    private ProductStockMapper productStockMapper;

    public PageResult queryForList(ProductStockQueryObject qo) {
        int totalCount = productStockMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<Map<String, Object>> data = productStockMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
