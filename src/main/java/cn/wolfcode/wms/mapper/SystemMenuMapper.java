package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    List<SystemMenu> query(SystemMenuQueryObject qo);

    void deleteChild(Long id);

    List<Map<String,Object>> selectMenuBySn(String menuSn);

    List<Map<String,Object>> getMenusBySnAndEmployeeId(@Param("menuSn") String menuSn,
                                     @Param("empId") Long empId);
}