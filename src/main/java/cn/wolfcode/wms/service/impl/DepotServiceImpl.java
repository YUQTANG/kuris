package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.mapper.DepotMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;

    public void saveOrUpdate(Depot depot) {
        if (depot.getId() == null) {
            depotMapper.insert(depot);
        } else {
            depotMapper.updateByPrimaryKey(depot);
        }
    }

    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> list() {
        return depotMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = depotMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = depotMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
