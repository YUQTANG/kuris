package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        PageResult result = brandService.query(qo);
        model.addAttribute("result",result);
        return "brand/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", brandService.get(id));
        }
        return "brand/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Brand brand) {
        brandService.saveOrUpdate(brand);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        if (id != null) {
            brandService.delete(id);
        }
        return new JSONResult();//象征性的返回
    }
}
