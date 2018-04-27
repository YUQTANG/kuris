package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class StockIncomeBillQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//结束时间

    private Long depotId = -1L;//仓库ID
    private Integer status = -1;//状态

    public Date getEndDate(){
        return DateUtil.getEndDate(endDate);
    }
}
