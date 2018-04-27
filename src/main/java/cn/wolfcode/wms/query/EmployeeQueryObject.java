package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class EmployeeQueryObject extends QueryObject {
    private String keyword;
    private Long deptId = -1L;

    public String getKeyword() {
        return empty2Null(keyword);
    }
}
