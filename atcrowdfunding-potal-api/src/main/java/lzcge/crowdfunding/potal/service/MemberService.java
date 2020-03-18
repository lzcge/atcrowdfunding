package lzcge.crowdfunding.potal.service;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.result.JsonResult;

import java.util.List;
import java.util.Map;


public interface MemberService {

	JsonResult insert(Member Member);

	Member login(Member member);

	//更新账户类型
	void updateAcctType(Member member);

	//更新账户认证真实信息
	void updateBasicinfo(Member member);

	Member selectById(Member member);



}
