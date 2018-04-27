package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController {

    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") SystemMenuQueryObject qo, Model model) {
        //面包屑 显示当前菜单层级
        if(qo.getParentId() != null){
            List<SystemMenu> menus = systemMenuService.getParentMenus(qo.getParentId());
            //共享到页面
            model.addAttribute("menus",menus);
        }

        List<SystemMenu> list = systemMenuService.query(qo);
        model.addAttribute("list",list);
        return "systemMenu/list";
    }

    @RequestMapping("input")
    public String input(Long id,Long parentId, Model model) {
        if (id != null) {
            model.addAttribute("entity", systemMenuService.get(id));
        }
        if (parentId != null) {
            model.addAttribute("parent", systemMenuService.get(parentId));
        }
        return "systemMenu/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(SystemMenu systemMenu) {
        systemMenuService.saveOrUpdate(systemMenu);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            //先删除子菜单
            systemMenuService.deleteChild(id);
            //再删除父菜单
            systemMenuService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }

    @RequestMapping("getMenusBySn")
    @ResponseBody
    public Object getMenusBySn(String menuSn) {
        Employee emp = UserContext.getCurrentUser();
        if (emp.isAdmin()) {
            //如果是超级管理员 则是执行所有界面的权限
            List<Map<String,Object>> list = systemMenuService.getMenusBySn(menuSn);
            return list;
        }
        //如果不是超级管理员 则根据登录进来的用户ID来检测当前用户拥有哪些菜单角色
        return systemMenuService.getMenusBySnAndEmployeeId(menuSn,emp.getId());
    }
}
