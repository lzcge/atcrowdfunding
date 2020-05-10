//package lzcge.crowdfunding.potal.dao;
//
//import lzcge.crowdfunding.entity.Member;
//import lzcge.crowdfunding.entity.MemberCert;
//
//import java.util.List;
//import java.util.Map;
//
//public interface MemberMapper {
//
//	int insert(Member member);
//
//	Member login(Member member);
//
//
//	List<Member> selectByAcct(Member member);
//
//	List<Member> selectByEmail(Member member);
//
//	List<Member> selectByPhone(Member member);
//
//	Member selectById(Member member);
//
//	void updateAcctType(Member member);
//
//	void updateBasicinfo(Member member);
//
//	void insertMemberCert(MemberCert memberCert);
//
//	void delteMemberCert(MemberCert memberCert);
//
//	/**
//	 * 更新会员认证状态
//	 * @param member
//	 */
//	void updateMemberAuthStatus(Member member);
//
//	/**
//	 * 查询会员的上传资质
//	 * @param member
//	 * @return
//	 */
//	List<Map<String, Object>> queryCertByMemberid(Member member);
//
//
//}