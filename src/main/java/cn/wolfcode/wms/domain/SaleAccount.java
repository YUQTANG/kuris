package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class SaleAccount extends BaseDomain {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;//创建账单时间
    private BigDecimal number;//数量
    private BigDecimal costPrice;//库存价
    private BigDecimal costAmount;//成本总价值
    private BigDecimal salePrice;//销售价
    private BigDecimal saleAmount;//销售总价值

    //关联关系
    private Product product;//商品
    private Employee saleman;//销售员
    private Client client;//客户
}
