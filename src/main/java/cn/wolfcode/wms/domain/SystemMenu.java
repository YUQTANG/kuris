package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SystemMenu extends BaseDomain {
    private String name;
    private String url;
    private String sn;

    @Override
    public String toString() {
        return "SystemMenu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", sn='" + sn + '\'' +
                ", id=" + id +
                '}';
    }

    //多对多关系
    private SystemMenu parent;
}
