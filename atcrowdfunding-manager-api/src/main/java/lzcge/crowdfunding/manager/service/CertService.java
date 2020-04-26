package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;




public interface CertService {
	public Cert queryCert(Map<String, Object> paramMap);

	public Page<Cert> pageQuery(Map<String, Object> paramMap);

	public int queryCount(Map<String, Object> paramMap);

	public JsonResult insertCert(Cert cert);

	public Cert queryById(Integer id);

	Cert queryCertByName(Cert cert);

	public int updateCert(Cert cert);

	public int deleteCert(Integer id);

	public int deleteCerts(Data ds);

	public List<Cert> queryCertByAccttype(String accttype);

	public List<Cert> queryAllCert();

	public List<Map<String, Object>> queryAllAccttypeCert();

	public int insertAccttypeCert(Map<String, Object> map);

	public int deleteAccttypeCert(Map<String, Object> map);

	//public void saveMemberCert(List<MemberCert> certimgs);


}
