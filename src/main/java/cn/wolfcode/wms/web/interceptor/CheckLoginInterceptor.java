package cn.wolfcode.wms.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        Object emp = request.getSession().getAttribute("EMP_IN_SESSION");
        if (emp == null) {
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
