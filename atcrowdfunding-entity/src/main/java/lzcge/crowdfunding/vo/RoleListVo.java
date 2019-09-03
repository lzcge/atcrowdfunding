package lzcge.crowdfunding.vo;

import lombok.Data;
import lzcge.crowdfunding.entity.Role;
import lzcge.crowdfunding.entity.User;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-22
 **/
@Data
public class RoleListVo {
	private Integer assignStatus;
	private User user;
	private List<Role> roleList;
}
