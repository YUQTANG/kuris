package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = supplierService.query(qo);
        model.addAttribute("result",result);
        return "supplier/list";
    }
    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", supplierService.get(id));
        }
        return "supplier/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Supplier supplier) {
        supplierService.saveOrUpdate(supplier);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            supplierService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
