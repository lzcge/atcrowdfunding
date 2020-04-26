package lzcge.crowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Cert;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.manager.dao.CertMapper;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

	@Transactional
	public JsonResult insertCert(Cert cert) {
		//判断资质是否已经存在
		Cert cert1 = certDao.queryCertByName(cert);
		JsonResult jsonResult = new JsonResult();
		if(cert1!=null){
			jsonResult.setInfo("资质已存在");
			jsonResult.setData(false);
			return jsonResult;
		}
		certDao.insertCert(cert);
		jsonResult.setData(true);
		 return jsonResult;
	}

	public Cert queryById(Integer id) {
		return certDao.queryById(id);
	}

	@Override
	public Cert queryCertByName(Cert cert) {
		return certDao.queryCertByName(cert);
	}

	@Transactional
	public int updateCert(Cert cert) {
		return certDao.updateCert(cert);
	}

	@Transactional
	public int deleteCert(Integer id) {
		return certDao.deleteCert(id);
	}


	@Transactional
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
	@Transactional
	public int insertAccttypeCert(Map<String, Object> map) {
		return certDao.insertAccttypeCert(map);
	}

	@Override
	@Transactional
	public int deleteAccttypeCert(Map<String, Object> map) {
		return certDao.deleteAccttypeCert(map);
	}




}
