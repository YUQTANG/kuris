package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class OrderSaleQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//结束时间

    private String keyword;

    private Long clientId = -1L;//客户ID
    private Long brandId = -1L;//品牌ID

    private String groupType = "e.name";//分组类型

    public Date getEndDate() {
        return DateUtil.getEndDate(endDate);
    }

    public String getKeyword() {
        return empty2Null(keyword);
    }
}
