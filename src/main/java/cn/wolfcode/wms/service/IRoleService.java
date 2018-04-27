package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IRoleService {
    void saveOrUpdate(Role role, Long[] permissionIds, Long[] systemMenuIds);

    void delete(Long id);

    Role get(Long id);

    List<Role> list();

    PageResult query(QueryObject qo);
}