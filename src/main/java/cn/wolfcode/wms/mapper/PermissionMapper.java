package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    List<Permission> selectAll();

    Integer queryForCount(QueryObject qo);

    List<?> queryForList(QueryObject qo);

    List<String> selectAllExpression();

    List<String> selectExpressionByEmployeeId(Long id);
}