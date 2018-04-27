package cn.wolfcode.wms.web.interceptor;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.util.PermissionUtil;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class ExpressionInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        //从session中拿出emp对象信息
        Employee emp = UserContext.getCurrentUser();

        //拿出exp信息出来
        List<String> exps = UserContext.getExpression();

        if (emp.isAdmin()) {
            //如果是超级管理员直接放行
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        Method m = hm.getMethod();
        if (!m.isAnnotationPresent(RequiredPermission.class)) {
            //如果当前没有贴有RequiredPermission注解 直接放行
            return true;
        }

        String exp = PermissionUtil.buildExpression(m);
        if(exps.contains(exp)){
            //访问当前方法的权限和用户所拥有的权限一致 放行
            return true;
        }

        //自定义异常 mvc.xml配置了自定义异常
        throw new SecurityException();
    }
}
