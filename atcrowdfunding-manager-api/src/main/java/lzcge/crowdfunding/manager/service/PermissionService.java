package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-28
 **/
public interface PermissionService {

	List<Permission> selectAll();

	int insert(Permission permission);

	int delete(Permission permission);

	int update(Permission permission);

	Permission selectByid(Integer id);

	List<Integer> selectPermissionIdsForRole(Integer roleId);

	void deleteRoleAndPermission(Integer roleId);

	void saveRoleAndPermission(Integer roleId,Data datas);

	List<Permission> selectPermissionsByUser(Integer userid);
}
