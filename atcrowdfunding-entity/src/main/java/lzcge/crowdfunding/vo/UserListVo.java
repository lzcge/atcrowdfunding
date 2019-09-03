package lzcge.crowdfunding.vo;

import lombok.Data;
import lzcge.crowdfunding.entity.User;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-20
 **/

@Data
public class UserListVo {
	private List<User> userList;
}
