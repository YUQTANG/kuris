package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") EmployeeQueryObject qo, Model model) {
        model.addAttribute("result", employeeService.query(qo));
        model.addAttribute("depts",departmentService.list());
        return "employee/list";
    }

    @RequiredPermission("编辑员工")
    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", employeeService.get(id));
        }
        model.addAttribute("depts",departmentService.list());
        model.addAttribute("roles",roleService.list());
        return "employee/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Employee employee,Long[] roleIds) {
        employeeService.saveOrUpdate(employee,roleIds);
        return new JSONResult();
    }

    @RequiredPermission("删除员工")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            employeeService.delete(id);
        }
        return new JSONResult();
    }
}
