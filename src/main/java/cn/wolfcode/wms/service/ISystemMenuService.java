package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
    void saveOrUpdate(SystemMenu SystemMenu);

    void delete(Long id);

    SystemMenu get(Long id);

    List<SystemMenu> list();

    List<SystemMenu> query(SystemMenuQueryObject qo);

    void deleteChild(Long id);

    List<SystemMenu> getParentMenus(Long parentId);

    List<Map<String,Object>> getMenusBySn(String menuSn);

    List<Map<String,Object>> getMenusBySnAndEmployeeId(String menuSn, Long id);
}
