package lzcge.crowdfunding.manager.service;

import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.manager.dao.AdvertMapper;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AdvertServiceImpl implements AdvertService {

	@Autowired
	private AdvertMapper advertDao;




	public Advert queryAdvert(Map<String, Object> advertMap) {
		return advertDao.queryAdvert(advertMap);
	}

	public Page<Advert> pageQuery(Map<String, Object> paramMap) {
		Page<Advert> advertPage = new Page<Advert>((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		paramMap.put("startIndex", advertPage.getStartIndex());
		List<Advert> advertList= advertDao.pageQuery(paramMap);
		// 获取数据的总条数
		int count = advertDao.queryCount(paramMap);
		
		advertPage.setDatas(advertList);
		advertPage.setTotalSize(count);
		return advertPage;
	}

	public int queryCount(Map<String, Object> advertMap) {
		return advertDao.queryCount(advertMap);
	}

	@Transactional
	public int insertAdvert(Advert advert) {
		return advertDao.insertAdvert(advert);
	}

	public Advert queryById(Integer id) {
		return advertDao.queryById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int updateAdvert(Advert advert) {
		Advert advert1 = advertDao.queryById(advert.getId());
		advert.setIconpath(advert1.getIconpath());
		advert.setStatus(advert1.getStatus());
		return advertDao.updateAdvert(advert);
	}

	@Transactional
	public int deleteAdvert(Integer id) {
		return advertDao.deleteAdvert(id);
	}

	@Override
	public int deleteAdverts(Data ds) {
		return advertDao.deleteAdverts(ds);
	}

}
