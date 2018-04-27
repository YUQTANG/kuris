package cn.wolfcode.wms.domain;

import cn.wolfcode.wms.util.JSONUtil;
import cn.wolfcode.wms.util.UploadUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Product extends BaseDomain {
    private String name;
    private String sn;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private String imagePath;
    private String intro;

    //打破第三范式
    private Long brandId;//品牌ID
    private String brandName;//品牌名称

    //小图片的路径
    public String getSmallImagePath() {
        if (StringUtils.hasLength(imagePath)) {
            //拿到图片最后一个索引的点
            int lastIndexOf = imagePath.lastIndexOf(".");
            //截取点之前的字符串
            String filename = imagePath.substring(0, lastIndexOf);
            //截取点之后的字符串
            String ext = imagePath.substring(lastIndexOf);
            //返回小图片的完整路径
            return filename + "_small" + ext;
        }
        return null;
    }

    //封装需要共享到父窗口的数据
    public String getJsonString() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("sn", sn);
        map.put("costPrice", costPrice);
        map.put("salePrice", salePrice);
        map.put("brandName", brandName);
        return JSONUtil.toJSONString(map);
    }
}
