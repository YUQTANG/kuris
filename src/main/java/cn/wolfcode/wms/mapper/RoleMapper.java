package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    Integer queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    void insertRelation(@Param("roleId") Long roleId,
                        @Param("permissionId") Long permissionId);

    void deleteRelation(Long id);

    void insertMenuRelation(@Param("roleId") Long id,
                            @Param("menuId") Long sId);

    void deleteMenuRelation(Long id);
}