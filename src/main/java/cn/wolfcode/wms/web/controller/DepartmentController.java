package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
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
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = departmentService.query(qo);
        model.addAttribute("result",result);
        return "department/list";
    }

    @RequiredPermission("编辑部门")
    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", departmentService.get(id));
        }
        return "department/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Department department) {
        departmentService.saveOrUpdate(department);
        return new JSONResult();//象征性返回
    }

    @RequiredPermission("删除部门")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            departmentService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
