package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Permission;

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
}
