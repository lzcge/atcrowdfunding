package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.manager.dao.RoleMapper;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-25
 **/
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 查询所有的角色信息
	 * @param queryIndexVo
	 * @return
	 */
	@Override
	public Page queryPage(QueryIndexVo queryIndexVo) {
		Page page = new Page(queryIndexVo.getPageNo(),queryIndexVo.getPageSize());
		Map<String,Object> map = new HashMap<>();
		map.put("startIndex",page.getStartIndex());
		map.put("queryIndexVo",queryIndexVo);
		List<Role> roleList  = roleMapper.queryPage(map);
		page.setDatas(roleList);
		Integer totleSize = roleMapper.selectAllCount(queryIndexVo);
		page.setTotalSize(totleSize);
		return page;
	}


	@Transactional
	@Override
	public int insert(Role role) {
		return roleMapper.insert(role);

	}


	@Override
	public Role selectByRoleId(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}


	@Transactional
	@Override
	public void update(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}


	@Transactional
	@Override
	public void deleteRole(Role role) {
		roleMapper.deleteByPrimaryKey(role.getId());
	}


	@Transactional
	@Override
	public void deleteRoleList(List<Role> roleList) {
		roleMapper.deleteRoleList(roleList);
	}
}
