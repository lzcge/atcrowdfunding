package lzcge.crowdfunding.po;

import lombok.Data;
import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.MemberCert;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-04-28
 **/
@Data
public class MemberCertPO {
	private MemberCert memberCert;
	private Cert cert;
}
