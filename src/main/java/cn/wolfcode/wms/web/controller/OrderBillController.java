package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.service.IProductService;
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
@RequestMapping("orderBill")
public class OrderBillController {

    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private ISupplierService supplierService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") OrderBillQueryObject qo, Model model) {
        PageResult result = orderBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("suppliers",supplierService.list());
        return "orderBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", orderBillService.get(id));
        }
        model.addAttribute("suppliers",supplierService.list());
        return "orderBill/input";
    }

    @RequestMapping("view")
    public String view(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", orderBillService.get(id));
        }
        model.addAttribute("suppliers",supplierService.list());
        return "orderBill/view";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(OrderBill orderBill) {
        orderBillService.saveOrUpdate(orderBill);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            orderBillService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(OrderBill orderBill) {
        if (orderBill.getId() != null) {
            orderBillService.audit(orderBill);
        }
        return new JSONResult();//象征性的返回
    }
}
