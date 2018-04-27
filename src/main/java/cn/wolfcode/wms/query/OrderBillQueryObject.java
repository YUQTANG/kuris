package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class OrderBillQueryObject extends QueryObject{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//结束时间

    private Long supplierId = -1L;//供应商ID
    private Integer status = -1;//状态
}
