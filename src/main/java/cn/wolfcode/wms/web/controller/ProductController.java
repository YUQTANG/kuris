package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ServletContext ctx;

    @RequestMapping("list")
    public String list(@ModelAttribute("qo") ProductQueryObject qo, Model model) {
        PageResult result = productService.query(qo);
        model.addAttribute("result", result);
        model.addAttribute("brands", brandService.list());
        return "product/list";
    }

    @RequestMapping("view")
    public String view(@ModelAttribute("qo") ProductQueryObject qo, Model model) {
        //先设置每页显示条数
        qo.setPageSize(20);
        PageResult result = productService.query(qo);
        model.addAttribute("result", result);
        model.addAttribute("brands", brandService.list());
        return "product/view";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", productService.get(id));
        }
        model.addAttribute("brands", brandService.list());
        return "product/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Product product, MultipartFile pic) {
        //处理上传上来的图片
        if (pic != null && pic.getSize() > 0 && pic.getContentType().startsWith("image/")) {
            //更新照片的时候 需要删除就得图片
            if (StringUtils.hasLength(product.getImagePath())) {
                //删除旧路径的图片
                UploadUtil.deleteFile(ctx, product.getImagePath());
            }
            //把图片保存到文件夹中
            String dir = ctx.getRealPath("/upload");
            String imagePath = UploadUtil.upload(pic, dir);
            //把图片的路径保存到对象中
            product.setImagePath(imagePath);
        }
        //再保存的时候 需要查询商品品牌的ID 并保存到商品中
        Long brandId = product.getBrandId();
        String brandName = brandService.get(brandId).getName();
        product.setBrandName(brandName);
        productService.saveOrUpdate(product);
        return new JSONResult();//象征性返回
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id,String imagePath) {
        if (id != null) {
            productService.delete(id);
            //当前图片路径不为空的时候 做删除操作的时候 顺便把图片也删除
            if(StringUtils.hasLength(imagePath)){
                UploadUtil.deleteFile(ctx,imagePath);
            }
        }
        return new JSONResult();//象征性的返回
    }
}
