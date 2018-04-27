package cn.wolfcode.wms.util;

import lombok.Getter;

//返回JSON
@Getter
public class JSONResult {
    private boolean success = true;
    private String msg;

    public void mask(String msg){
        this.msg = msg;
        success = false;
    }
}
