package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = clientService.query(qo);
        model.addAttribute("result",result);
        return "client/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", clientService.get(id));
        }
        return "client/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Client client) {
        clientService.saveOrUpdate(client);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            clientService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
