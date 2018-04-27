package cn.wolfcode.wms.util;

import cn.wolfcode.wms.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

public abstract class UserContext {
    public static final String EMP_IN_SESSION = "EMP_IN_SESSION";
    public static final String EXP_IN_SESSION = "EXP_IN_SESSION";

    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).
                getRequest().getSession();
    }

    public static void setCurrentUser(Employee emp){
        getSession().setAttribute(EMP_IN_SESSION,emp);
    }

    public static Employee getCurrentUser(){
        return (Employee) getSession().getAttribute(EMP_IN_SESSION);
    }

    public static void setExpression(List<String> exps){
        getSession().setAttribute(EXP_IN_SESSION,exps);
    }

    public static List<String> getExpression(){
        return (List<String>) getSession().getAttribute(EXP_IN_SESSION);
    }

}
