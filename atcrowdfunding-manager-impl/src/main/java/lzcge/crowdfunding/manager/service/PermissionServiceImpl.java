package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.manager.dao.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-28
 **/

@Service
public class PermissionServiceImpl implements PermissionService{


	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public List<Permission> selectAll() {
		return permissionMapper.selectAll();
	}


	@Transactional
	@Override
	public int insert(Permission permission) {
		return permissionMapper.insert(permission);
	}


	@Transactional
	@Override
	public int delete(Permission permission) {
		return permissionMapper.deleteByPrimaryKey(permission.getId());
	}



	@Transactional
	@Override
	public int update(Permission permission) {
		return permissionMapper.updateByPrimaryKey(permission);
	}

	@Override
	public Permission selectByid(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}
}
