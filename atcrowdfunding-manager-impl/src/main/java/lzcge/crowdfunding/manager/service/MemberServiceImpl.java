package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.*;
import lzcge.crowdfunding.manager.dao.CertMapper;
import lzcge.crowdfunding.manager.dao.MemberMapper;
import lzcge.crowdfunding.po.MemberCertPO;
import lzcge.crowdfunding.po.MemberDetail;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper ;



	@Override
	public Member selectById(Member member) {
		return memberMapper.selectById(member);
	}


	/**
	 * 用户实名认证时分页查询用户信息
	 * @param member
	 * @return
	 */
	@Override
	public MemberDetail selectMemberDetailByMemId(Member member) {
		MemberDetail memberDetail = new MemberDetail();
		List<MemberCertPO> memberCertPOList = new ArrayList<>();
		List<Map<String,Object>> mapList = memberMapper.selectMemberCertByMemberId(member.getId());
		for (Map<String,Object> map:mapList	 ) {
			MemberCertPO memberCertPO = new MemberCertPO();
			Cert cert = new Cert();
			MemberCert memberCert = new MemberCert();
			cert.setName(map.get("name").toString());
			cert.setId(Integer.valueOf(map.get("id").toString()));
			memberCert.setIconpath(map.get("iconpath").toString());
			memberCertPO.setCert(cert);
			memberCertPO.setMemberCert(memberCert);
			memberCertPOList.add(memberCertPO);
		}
		memberDetail.setMemberCertPOList(memberCertPOList);
		return memberDetail;
	}




	@Override
	public Page<MemberDetail> pageQueryMember(Map<String, Object> memberMap) {
		Page<MemberDetail> memberDetailPage = new Page<MemberDetail>((Integer)memberMap.get("pageno"),(Integer)memberMap.get("pagesize"));
		memberMap.put("startIndex", memberDetailPage.getStartIndex());
		List<Member> memberList = memberMapper.pageQueryMember(memberMap);
		List<MemberDetail> memberDetails =new ArrayList<>();
		//查询对应的资质图片
		for (int i = 0; i < memberList.size(); i++) {
			MemberDetail memberDetail = new MemberDetail();
			Member member = memberList.get(i);
			memberDetail.setMember(member);
//			List<MemberCertPO> memberCertPOList = memberMapper.selectMemberCertByMemberId(member.getId());
//			memberDetail.setMemberCertPOList(memberCertPOList);
			memberDetails.add(memberDetail);
		}
		int count = memberMapper.queryCount(memberMap);

		memberDetailPage.setDatas(memberDetails);
		memberDetailPage.setTotalSize(count);
		return memberDetailPage;
	}



	@Override
	public int queryCount(Map<String, Object> memberMap) {
		return memberMapper.queryCount(memberMap);
	}

	@Override
	@Transactional
	public void updateMember(Member member) {
		memberMapper.updateMember(member);
	}

	@Override
	public int deleteMember(Integer id) {
		return memberMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteMembers(Data ds) {
		return memberMapper.deleteMembers(ds);
	}
}
