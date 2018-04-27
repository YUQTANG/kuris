package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.StockIncomeBillQueryObject;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockIncomeBill")
public class StockIncomeBillController {

    @Autowired
    private IStockIncomeBillService stockIncomeBillService;
    @Autowired
    private IDepotService depotService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") StockIncomeBillQueryObject qo, Model model) {
        PageResult result = stockIncomeBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("depots",depotService.list());
        return "stockIncomeBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", stockIncomeBillService.get(id));
        }
        model.addAttribute("depots",depotService.list());
        return "stockIncomeBill/input";
    }

    @RequestMapping("view")
    public String view(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", stockIncomeBillService.get(id));
        }
        model.addAttribute("depots",depotService.list());
        return "stockIncomeBill/view";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockIncomeBill stockIncomeBill) {
        stockIncomeBillService.saveOrUpdate(stockIncomeBill);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            stockIncomeBillService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) {
        if (id != null) {
            stockIncomeBillService.audit(id);
        }
        return new JSONResult();//象征性的返回
    }
}
