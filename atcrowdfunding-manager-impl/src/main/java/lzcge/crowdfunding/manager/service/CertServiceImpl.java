package lzcge.crowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.manager.dao.CertMapper;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CertServiceImpl implements CertService {



	@Autowired
	private CertMapper certDao;

	public Cert queryCert(Map<String, Object> paramMap) {
		return certDao.queryCert(paramMap);
	}

	public Page<Cert> pageQuery(Map<String, Object> paramMap) {
		Page<Cert> certPage = new Page<Cert>((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		paramMap.put("startIndex", certPage.getStartIndex());
		List<Cert> certs = certDao.pageQuery(paramMap);
		// 获取数据的总条数
		int count = certDao.queryCount(paramMap);
		certPage.setDatas(certs);
		certPage.setTotalSize(count);
		return certPage;
	}

	public int queryCount(Map<String, Object> paramMap) {
		return certDao.queryCount(paramMap);
	}

	public void insertCert(Cert cert) {
		certDao.insertCert(cert);
	}

	public Cert queryById(Integer id) {
		return certDao.queryById(id);
	}

	public int updateCert(Cert cert) {
		return certDao.updateCert(cert);
	}

	public int deleteCert(Integer id) {
		return certDao.deleteCert(id);
	}

	public int deleteCerts(Data ds) {
		return certDao.deleteCerts(ds);
	}

	public List<Cert> queryCertByAccttype(String accttype) {
		return certDao.queryCertByAccttype(accttype);
	}

	@Override
	public List<Cert> queryAllCert() {
		return certDao.queryAllCert();
	}

	@Override
	public List<Map<String, Object>> queryAllAccttypeCert() {
		return certDao.queryAllAccttypeCert();
	}

	@Override
	public int insertAccttypeCert(Map<String, Object> map) {
		return certDao.insertAccttypeCert(map);
	}

	@Override
	public int deleteAccttypeCert(Map<String, Object> map) {
		return certDao.deleteAccttypeCert(map);
	}


	@Override
	public void saveMemberCert(List<MemberCert> certimgs) {
		for (MemberCert memberCert : certimgs) {
			certDao.insertMemberCert(memberCert);
		}
	}

}
