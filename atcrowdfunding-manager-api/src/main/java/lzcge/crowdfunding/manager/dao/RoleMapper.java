package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.vo.QueryIndexVo;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

	List<Role> queryPage(Map<String,Object> map);

	Integer selectAllCount(QueryIndexVo queryIndexVo);

	void deleteRoleList(List<Role> roleList);
}