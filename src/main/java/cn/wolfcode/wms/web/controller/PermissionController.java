package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("reload")
    @ResponseBody
    @RequiredPermission("加载权限")
    public Object reload(){
        permissionService.reload();
        return new JSONResult();
    }

    @RequestMapping("list")
    @RequiredPermission("权限列表")
    public Object list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = permissionService.query(qo);
        model.addAttribute("result",result);
        return "permission/list";
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            permissionService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}