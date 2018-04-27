package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class QueryObject {
    private int currentPage = 1;
    private int pageSize = 5;

    public int getStart() {
        return (currentPage - 1) * pageSize;
    }

    public String empty2Null(String s){
        return StringUtils.hasLength(s) ? s : null;
    }
}
