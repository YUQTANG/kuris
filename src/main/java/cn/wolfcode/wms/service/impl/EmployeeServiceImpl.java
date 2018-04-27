package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.MD5Util;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public void login(String username, String password) {
        //传入密码的时候 记得加密
        Employee emp = employeeMapper.login(username,MD5Util.encoder(password));
        if (emp == null) {
            throw new RuntimeException("账号与密码不匹配,请重新登录");
        }
        //把用户信息存入session中
        UserContext.setCurrentUser(emp);
        if (!emp.isAdmin()) {
            //如果不是超级管理员 则把当前用户的权限信息存入到session中
            List<String> exps = permissionMapper.selectExpressionByEmployeeId(emp.getId());
            UserContext.setExpression(exps);
        }
    }

    public void saveOrUpdate(Employee employee, Long[] roleIds) {
        if (employee.getId() == null) {
            //在员工保存之前 进行MD5密码加密
            String newPwd = MD5Util.encoder(employee.getPassword());
            //然后把密码加入到数据库中
            employee.setPassword(newPwd);
            employeeMapper.insert(employee);
        } else {
            employeeMapper.deleteRelation(employee.getId());
            employeeMapper.updateByPrimaryKey(employee);
        }

        if (roleIds != null) {
            for (Long roleId : roleIds) {
                employeeMapper.insertRelation(employee.getId(),roleId);
            }
        }
    }

    public void delete(Long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    public PageResult query(EmployeeQueryObject qo) {
        Integer totalCount = employeeMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<?> data = employeeMapper.queryForList(qo);
        return new PageResult(qo.getPageSize(), qo.getCurrentPage(), totalCount, data);
    }
}
