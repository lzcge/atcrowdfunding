package lzcge.crowdfunding.vo;

import lzcge.crowdfunding.entity.User;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-03
 **/
public class LoginUserVo {
	private User user;
	private String type;
	private String verifyCode;

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
