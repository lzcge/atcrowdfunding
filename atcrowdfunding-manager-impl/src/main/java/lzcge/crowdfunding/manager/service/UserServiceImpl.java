package lzcge.crowdfunding.manager.service;


import lzcge.crowdfunding.entity.Permission;
import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.exception.LoginFailException;
import lzcge.crowdfunding.manager.dao.PermissionMapper;
import lzcge.crowdfunding.manager.dao.UserMapper;
import lzcge.crowdfunding.util.Const;
import lzcge.crowdfunding.util.MD5Util;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryIndexVo;
import lzcge.crowdfunding.vo.RoleListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-07-28
 **/

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	public User selectById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}


	/**
	 * 查询获取用户数据

	 * @return
	 */
	@Override
	public Page queryPage(QueryIndexVo queryIndexVo) {
		Page page = new Page(queryIndexVo.getPageNo(), queryIndexVo.getPageSize());
		Map<String,Object> map = new HashMap<>();
		map.put("startIndex",page.getStartIndex());
		map.put("queryIndexVo", queryIndexVo);
		List<User> userList = userMapper.queryPage(map);
		page.setDatas(userList);
		Integer totalSize = userMapper.selectAllCount(queryIndexVo);
		page.setTotalSize(totalSize);
		return page;
	}


	@Override
	public User login(User user) {
		//密码加密
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		User user1 = userMapper.login(user);
		if(user1==null){
			throw new LoginFailException("登录失败，账号或密码错误");
		}
		return user1;
	}


	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@Transactional
	@Override
	public int insert(User user) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String createTime = simpleDateFormat.format(date);
		user.setCreatetime(createTime);
		user.setUserpswd(MD5Util.digest(Const.LOGIN_PASSWORD));
		return userMapper.insert(user);

	}


	@Transactional
	@Override
	public void updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
	}


	@Transactional
	@Override
	public int deleteUser(User user) {
		return userMapper.deleteByPrimaryKey(user.getId());
	}


	@Transactional
	@Override
	public void deleteUserList(List<User> userList) {
		userMapper.deleteUserList(userList);
	}


	/**
	 * 查询所有当前用户已将分配的角色
	 * @param id
	 * @return
	 */
	@Override
	public List<Role> selectRoleByUserId(Integer id) {
		return userMapper.selectRoleByUserId(id);
	}


	/**
	 * 查询所有角色
	 * @return
	 */
	@Override
	public List<Role> selectAllRole() {
		return userMapper.selectAllRole();
	}

	/**
	 * 给用户分配角色，批量插入
	 * @param roleListVo
	 */
	@Transactional
	@Override
	public void saveUserAndRoleList(RoleListVo roleListVo) {
		userMapper.saveUserAndRoleList(roleListVo);
	}


	/**
	 * 删除用户角色关联
	 * @param roleListVo
	 */
	@Transactional
	@Override
	public void deleteUserAndRoleList(RoleListVo roleListVo) {
		userMapper.deleteUserAndRoleList(roleListVo);
	}


	@Override
	public Permission getUserPermission(User user) {
		Permission permission = null;
		List<Permission> permissionList = permissionMapper.selectPermissionsByUser(user.getId());
		Map<Integer,Permission> map = new HashMap<>();
		for (Permission per:permissionList) {
			map.put(per.getId(),per);
		}

		for(Permission per:permissionList){
			if(per.getPid()==null) permission = per;
			else{
				Permission parent = map.get(per.getPid());
				parent.getChildren().add(per);
			}
		}

		return permission;
	}
}
