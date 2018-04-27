package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.mapper.DepartmentMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public void saveOrUpdate(Department department) {
        if (department.getId() == null) {
            departmentMapper.insert(department);
        } else {
            departmentMapper.updateByPrimaryKey(department);
        }
    }

    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> list() {
        return departmentMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = departmentMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = departmentMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
