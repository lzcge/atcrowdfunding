package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryUserVo;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-07-28
 **/
public interface UserService {

	User login(User user);

	Page queryPage(QueryUserVo queryUserVo);

	int insert(User user);

	User selectById(Integer id);

	void updateUser(User user);

	int deleteUser(User user);
}
