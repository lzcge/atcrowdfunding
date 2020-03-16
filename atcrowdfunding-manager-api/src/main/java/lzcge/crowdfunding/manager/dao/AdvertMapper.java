package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;



public interface AdvertMapper {

	Advert queryAdvert(Map<String, Object> advertMap);

	List<Advert> pageQuery(Map<String, Object> advertMap);

	int queryCount(Map<String, Object> advertMap);

	int insertAdvert(Advert advert);

	Advert queryById(Integer id);

	int updateAdvert(Advert advert);

	int deleteAdvert(Integer id);

	int deleteAdverts(Data ds);
}
