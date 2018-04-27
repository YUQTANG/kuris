package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;
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
@RequestMapping("depot")
public class DepotController {

    @Autowired
    private IDepotService depotService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = depotService.query(qo);
        model.addAttribute("result",result);
        return "depot/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", depotService.get(id));
        }
        return "depot/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Depot depot) {
        depotService.saveOrUpdate(depot);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            depotService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
