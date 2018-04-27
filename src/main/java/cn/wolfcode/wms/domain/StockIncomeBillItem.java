package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StockIncomeBillItem extends BaseDomain{
    private BigDecimal costPrice;//库存成本价
    private BigDecimal number;//库存数量
    private BigDecimal amount;//总金额
    private String remark;//备注

    //关联关系
    private Product product;//商品
    private Long billId;//单据ID
}
