package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryIndexVo;
import lzcge.crowdfunding.vo.RoleListVo;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-07-28
 **/
public interface UserService {

	User login(User user);

	Page queryPage(QueryIndexVo queryIndexVo);

	int insert(User user);

	User selectById(Integer id);

	void updateUser(User user);

	int deleteUser(User user);

	void deleteUserList(List<User> userList);

	List<Role> selectRoleByUserId(Integer id);

	List<Role> selectAllRole();

	void saveUserAndRoleList(RoleListVo roleListVo);

	void deleteUserAndRoleList(RoleListVo roleListVo);
}
