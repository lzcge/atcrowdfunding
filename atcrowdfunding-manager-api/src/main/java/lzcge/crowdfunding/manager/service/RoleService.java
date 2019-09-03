package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryIndexVo;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-25
 **/
public interface RoleService {



	Page queryPage(QueryIndexVo queryIndexVo);

	int insert(Role role);

	Role selectByRoleId(Integer id);

	void update(Role role);

	void deleteRole(Role role);

	void deleteRoleList(List<Role> roleList);
}
