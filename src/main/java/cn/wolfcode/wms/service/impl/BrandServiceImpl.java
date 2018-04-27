package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    public void saveOrUpdate(Brand brand) {
        if (brand.getId() == null) {
            brandMapper.insert(brand);
        } else {
            brandMapper.updateByPrimaryKey(brand);
        }
    }

    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    public Brand get(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<Brand> list() {
        return brandMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = brandMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = brandMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
