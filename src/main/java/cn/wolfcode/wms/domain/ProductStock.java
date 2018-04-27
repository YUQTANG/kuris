package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductStock extends BaseDomain {
    private BigDecimal price;//成本价
    private BigDecimal storeNumber;//库存数量
    private BigDecimal amount;//商品总价值

    //关联关系
    private Product product;
    private Depot depot;
}
