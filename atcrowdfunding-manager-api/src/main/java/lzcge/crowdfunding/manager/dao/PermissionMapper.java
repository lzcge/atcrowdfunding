package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Permission;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Integer> selectPermissionIdsForRole(@Param("roleId") Integer roleId);

	void deleteRoleAndPermission(@Param("roleid") Integer roleId);

	void saveRoleAndPermission(@Param("roleid") Integer roleId,@Param("permissionid") Integer permissionId);

	List<Permission> selectPermissionsByUser(@Param("userid") Integer userid);
}