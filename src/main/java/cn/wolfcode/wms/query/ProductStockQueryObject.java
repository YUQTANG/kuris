package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductStockQueryObject extends QueryObject {
    private String keyword;

    private Long depotId = -1L;
    private Long brandId = -1L;

    private BigDecimal limitNum;//阈值

    public String getKeyword() {
        return empty2Null(keyword);
    }
}
