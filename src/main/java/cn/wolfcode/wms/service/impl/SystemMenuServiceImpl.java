package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.mapper.SystemMenuMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    @Autowired
    private SystemMenuMapper SystemMenuMapper;

    public void saveOrUpdate(SystemMenu SystemMenu) {
        if (SystemMenu.getId() == null) {
            SystemMenuMapper.insert(SystemMenu);
        } else {
            SystemMenuMapper.updateByPrimaryKey(SystemMenu);
        }
    }

    public void delete(Long id) {
        SystemMenuMapper.deleteByPrimaryKey(id);
    }

    public SystemMenu get(Long id) {
        return SystemMenuMapper.selectByPrimaryKey(id);
    }

    public List<SystemMenu> list() {
        return SystemMenuMapper.selectAll();
    }

    public List<SystemMenu> query(SystemMenuQueryObject qo) {
        return SystemMenuMapper.query(qo);
    }

    public void deleteChild(Long id) {
        SystemMenuMapper.deleteChild(id);
    }

    public List<SystemMenu> getParentMenus(Long parentId) {
        //定义集合 并存储菜单
        List<SystemMenu> menus = new ArrayList<>();
        //根据传过来的ID查询出SystemMenu对象
        SystemMenu menu = SystemMenuMapper.selectByPrimaryKey(parentId);
        if (menu != null) {
            menus.add(menu);
            //获取当前菜单的父菜单
            menu = menu.getParent();
            while (menu != null){
                menus.add(menu);
                menu = menu.getParent();
            }
        }
        //反转菜单
        Collections.reverse(menus);
        return menus;
    }

    public List<Map<String, Object>> getMenusBySn(String menuSn) {
        return SystemMenuMapper.selectMenuBySn(menuSn);
    }

    public List<Map<String,Object>> getMenusBySnAndEmployeeId(String menuSn, Long empId) {
        return SystemMenuMapper.getMenusBySnAndEmployeeId(menuSn,empId);
    }
}
