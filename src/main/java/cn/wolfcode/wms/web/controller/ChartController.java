package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.OrderSaleQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IChartService;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.Dictionary;
import cn.wolfcode.wms.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private IChartService chartService;
    @Autowired
    private ISupplierService supplierService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IClientService clientService;

    @RequestMapping("order")
    public String order(@ModelAttribute("qo") OrderChartQueryObject qo, Model model) {
        List<Map<String, Object>> list = chartService.selectOrderChart(qo);
        model.addAttribute("list",list);
        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("brands",brandService.list());
        return "chart/order";
    }

    @RequestMapping("sale")
    public String sale(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {
        List<Map<String, Object>> list = chartService.selectOrderSale(qo);
        model.addAttribute("list",list);
        model.addAttribute("clients",clientService.list());
        model.addAttribute("brands",brandService.list());
        return "chart/sale";
    }

    @RequestMapping("saleByBar")
    public String saleByBar(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {

        List<Object> x = new ArrayList<>();
        List<Object> y = new ArrayList<>();

        List<Map<String, Object>> maps = chartService.selectOrderSale(qo);
        for (Map<String, Object> map : maps) {
            x.add(map.get("groupType"));
            y.add(map.get("totalAmount"));
        }
        model.addAttribute("groupType", Dictionary.SALE_MAP.get(qo.getGroupType()));
        model.addAttribute("x", JSONUtil.toJSONString(x));
        model.addAttribute("y", JSONUtil.toJSONString(y));
        return "chart/saleByBar";
    }

    @RequestMapping("saleByPie")
    public String saleByPie(@ModelAttribute("qo") OrderSaleQueryObject qo, Model model) {

        List<Object> x = new ArrayList<>();
        List<Object> y = new ArrayList<>();

        List<Map<String, Object>> maps = chartService.selectOrderSale(qo);
        for (Map<String, Object> map : maps) {
            Map<String,Object> obj = new HashMap<>();
            obj.put("name",map.get("groupType"));
            obj.put("value",map.get("totalAmount"));
            x.add(map.get("groupType"));
            y.add(obj);
        }
        model.addAttribute("groupType", Dictionary.SALE_MAP.get(qo.getGroupType()));
        model.addAttribute("x", JSONUtil.toJSONString(x));
        model.addAttribute("y", JSONUtil.toJSONString(y));
        return "chart/saleByPie";
    }
}
