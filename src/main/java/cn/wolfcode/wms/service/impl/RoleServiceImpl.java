package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    public void saveOrUpdate(Role role, Long[] permissionIds, Long[] systemMenuIds) {
        if (role.getId() == null) {
            roleMapper.insert(role);
        } else {
            roleMapper.deleteRelation(role.getId());
            roleMapper.deleteMenuRelation(role.getId());
            roleMapper.updateByPrimaryKey(role);
        }
        if (permissionIds != null) {
            for (Long pId : permissionIds) {
                roleMapper.insertRelation(role.getId(),pId);
            }
        }
        if (systemMenuIds != null) {
            for (Long sId : systemMenuIds) {
                roleMapper.insertMenuRelation(role.getId(),sId);
            }
        }
    }

    public void delete(Long id) {
        roleMapper.deleteRelation(id);
        roleMapper.deleteMenuRelation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> list() {
        return roleMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        Integer totalCount = roleMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = roleMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
