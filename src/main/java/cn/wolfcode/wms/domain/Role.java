package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Role extends BaseDomain {
    private String name;
    private String sn;

    //多对多关系
    private List<Permission> permissions = new ArrayList<>();

    private List<SystemMenu> systemMenus = new ArrayList<>();
}
