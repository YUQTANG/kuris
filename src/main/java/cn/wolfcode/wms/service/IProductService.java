package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IProductService {
    void saveOrUpdate(Product product);

    void delete(Long id);

    Product get(Long id);

    List<Product> list();

    PageResult query(QueryObject qo);
}
