package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.vo.QueryIndexVo;
import lzcge.crowdfunding.vo.RoleListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    void updateByPrimaryKey(User record);

	User login(User user);

	List<User> queryPage(Map<String,Object> map);

	Integer selectAllCount(QueryIndexVo queryIndexVo);

	void deleteUserList(List<User> userList);

	List<Role> selectRoleByUserId(Integer id);

	List<Role> selectAllRole();

	void saveUserAndRoleList(@Param("roleListVo") RoleListVo roleListVo);

	void deleteUserAndRoleList(@Param("roleListVo") RoleListVo roleListVo);

}