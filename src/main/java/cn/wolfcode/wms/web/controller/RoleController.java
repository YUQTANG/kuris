package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private ISystemMenuService systemMenuService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = roleService.query(qo);
        model.addAttribute("result", result);
        return "role/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", roleService.get(id));
        }
        model.addAttribute("permissions", permissionService.list());
        model.addAttribute("systemMenus", systemMenuService.list());
        return "role/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Role role, Long[] permissionIds, Long[] systemMenuIds) {
        roleService.saveOrUpdate(role, permissionIds, systemMenuIds);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            roleService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
