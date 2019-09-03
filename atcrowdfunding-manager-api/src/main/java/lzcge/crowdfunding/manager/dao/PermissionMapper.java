package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);
}