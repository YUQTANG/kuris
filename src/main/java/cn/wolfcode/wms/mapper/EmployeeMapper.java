package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    Integer queryForCount(EmployeeQueryObject qo);

    List<?> queryForList(EmployeeQueryObject qo);

    void insertRelation(@Param("employeeId") Long employeeId,
                        @Param("roleId") Long roleId);

    void deleteRelation(Long id);

    Employee login(@Param("username") String username,
                   @Param("password") String password);
}