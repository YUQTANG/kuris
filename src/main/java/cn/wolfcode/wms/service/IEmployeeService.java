package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IEmployeeService {
    void saveOrUpdate(Employee employee, Long[] roleIds);

    void delete(Long id);

    Employee get(Long id);

    List<Employee> list();

    PageResult query(EmployeeQueryObject qo);

    void login(String username, String password);
}
