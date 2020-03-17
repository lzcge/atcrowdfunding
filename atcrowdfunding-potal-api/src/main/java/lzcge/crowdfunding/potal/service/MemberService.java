package lzcge.crowdfunding.potal.service;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.result.JsonResult;

import java.util.List;
import java.util.Map;


public interface MemberService {

	JsonResult insert(Member Member);

	Member login(Member member);


}
