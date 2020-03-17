package lzcge.crowdfunding.potal.dao;

import lzcge.crowdfunding.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberMapper {

	int insert(Member member);

	Member login(Member member);


	List<Member> selectByAcct(Member member);

	List<Member> selectByEmail(Member member);

	List<Member> selectByPhone(Member member);


}