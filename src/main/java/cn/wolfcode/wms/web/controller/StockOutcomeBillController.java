package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController {

    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IClientService clientService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") StockOutcomeBillQueryObject qo, Model model) {
        PageResult result = stockOutcomeBillService.query(qo);
        model.addAttribute("result",result);
        model.addAttribute("depots",depotService.list());
        model.addAttribute("clients",clientService.list());
        return "stockOutcomeBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", stockOutcomeBillService.get(id));
        }
        model.addAttribute("clients",clientService.list());
        model.addAttribute("depots",depotService.list());
        return "stockOutcomeBill/input";
    }

    @RequestMapping("view")
    public String view(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", stockOutcomeBillService.get(id));
        }
        model.addAttribute("depots",depotService.list());
        return "stockOutcomeBill/view";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockOutcomeBill stockOutcomeBill) {
        stockOutcomeBillService.saveOrUpdate(stockOutcomeBill);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            stockOutcomeBillService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) {
        JSONResult result = new JSONResult();
        if (id != null) {
            try {
                stockOutcomeBillService.audit(id);
            }catch (Exception e){
                e.printStackTrace();
                result.mask(e.getMessage());
            }
        }
        return result;
    }
}
