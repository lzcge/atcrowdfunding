package lzcge.crowdfunding.po;

import lombok.Data;
import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.MemberCert;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-04-28
 **/
@Data
public class MemberDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<MemberCertPO> memberCertPOList;
	private Member member;
}
