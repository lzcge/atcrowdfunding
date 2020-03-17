package lzcge.crowdfunding.vo;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.User;


public class LoginUserVo {
	private User user;
	private Member member;
	private String type;
	private String verifyCode;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

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
