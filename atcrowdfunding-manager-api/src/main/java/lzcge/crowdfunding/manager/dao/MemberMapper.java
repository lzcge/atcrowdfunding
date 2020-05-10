package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.po.MemberCertPO;
import lzcge.crowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
	
	

	Member selectById(Member member);

	List<Member> selectMemberAuth(Map<String, Object> memberMap);

	List<Map<String,Object>> selectMemberCertByMemberId(@Param("memberid") Integer memberid);

	int queryCount(Map<String, Object> memberMap);

	List<Member> pageQueryMember(Map<String, Object> memberMap);

	void updateMember(Member member);

	int deleteByPrimaryKey(Integer id);

	int deleteMembers(Data ds);
	


}