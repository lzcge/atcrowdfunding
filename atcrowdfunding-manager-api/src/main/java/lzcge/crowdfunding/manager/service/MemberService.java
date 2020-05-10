package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.po.MemberDetail;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;


public interface MemberService {



	Member selectById(Member member);

	MemberDetail selectMemberDetailByMemId(Member member);


	public Page<MemberDetail> pageQueryMember(Map<String, Object> memberMap);


	public int queryCount(Map<String, Object> memberMap);

	public void updateMember(Member member);

	public int deleteMember(Integer id);

	public int deleteMembers(Data ds);


}
