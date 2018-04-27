package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class StockOutcomeBill extends BaseDomain {
    public static final Integer NORMAL = 1;
    public static final Integer AUDIT = 2;

    private String sn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;
    private Integer status = NORMAL;//状态
    private BigDecimal totalAmount;//总金额
    private BigDecimal totalNumber;//总数量
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditTime;//审核时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputTime;//录入时间

    //关联关系
    private Employee inputUser;//录入人
    private Employee auditor;//审核人
    private Depot depot;//仓库
    private Client client;//客户

    //组合关系 出库明细
    private List<StockOutcomeBillItem> items = new ArrayList<>();
}
