package lzcge.crowdfunding.manager.dao;


import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface CertMapper {
	Cert queryCert(Map<String, Object> paramMap);

	List<Cert> pageQuery(Map<String, Object> paramMap);

	int queryCount(Map<String, Object> paramMap);

	void insertCert(Cert cert);

	Cert queryById(Integer id);

	Cert queryCertByName(Cert cert);

	int updateCert(Cert cert);

	int deleteCert(Integer id);

	int deleteCerts(Data ds);

	List<Cert> queryCertByAccttype(String accttype);

	List<Cert> queryAllCert();

	List<Map<String, Object>> queryAllAccttypeCert();

	int insertAccttypeCert(Map<String, Object> map);

	int deleteAccttypeCert(Map<String, Object> map);


}