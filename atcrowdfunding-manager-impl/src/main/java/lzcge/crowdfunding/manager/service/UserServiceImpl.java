package lzcge.crowdfunding.manager.service;


import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.exception.LoginFailException;
import lzcge.crowdfunding.manager.dao.UserMapper;
import lzcge.crowdfunding.util.Const;
import lzcge.crowdfunding.util.MD5Util;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.QueryUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.DateFormat;
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

	@Override
	public User selectById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}


	/**
	 * 查询获取用户数据

	 * @return
	 */
	@Override
	public Page queryPage(QueryUserVo queryUserVo) {
		Page page = new Page(queryUserVo.getPageNo(),queryUserVo.getPageSize());
		Map<String,Object> map = new HashMap<>();
		map.put("startIndex",page.getStartIndex());
		map.put("queryUserVo",queryUserVo);
		List<User> userList = userMapper.queryPage(map);
		page.setDatas(userList);
		Integer totalSize = userMapper.selectAllCount(queryUserVo);
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
}
