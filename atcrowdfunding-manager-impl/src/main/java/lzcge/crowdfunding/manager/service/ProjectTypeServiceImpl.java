package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.manager.dao.ProjectTypeMapper;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {



	@Autowired
	private ProjectTypeMapper ProjectTypeDao;

	public ProjectType queryProjectType(Map<String, Object> paramMap) {
		return ProjectTypeDao.queryProjectType(paramMap);
	}

	public Page<ProjectType> pageQuery(Map<String, Object> paramMap) {
		Page<ProjectType> ProjectTypePage = new Page<ProjectType>((Integer)paramMap.get("pageno"),(Integer)paramMap.get("pagesize"));
		paramMap.put("startIndex", ProjectTypePage.getStartIndex());
		List<ProjectType> ProjectTypes = ProjectTypeDao.pageQuery(paramMap);
		// 获取数据的总条数
		int count = ProjectTypeDao.queryCount(paramMap);
		ProjectTypePage.setDatas(ProjectTypes);
		ProjectTypePage.setTotalSize(count);
		return ProjectTypePage;
	}

	public int queryCount(Map<String, Object> paramMap) {
		return ProjectTypeDao.queryCount(paramMap);
	}

	@Transactional
	public JsonResult insertProjectType(ProjectType ProjectType) {
		//判断资质是否已经存在
		ProjectType ProjectType1 = ProjectTypeDao.queryProjectTypeByName(ProjectType);
		JsonResult jsonResult = new JsonResult();
		if(ProjectType1!=null){
			jsonResult.setInfo("该类别已存在");
			jsonResult.setData(false);
			return jsonResult;
		}
		ProjectTypeDao.insertProjectType(ProjectType);
		jsonResult.setData(true);
		 return jsonResult;
	}

	public ProjectType queryById(Integer id) {
		return ProjectTypeDao.queryById(id);
	}

	@Override
	public ProjectType queryProjectTypeByName(ProjectType ProjectType) {
		return ProjectTypeDao.queryProjectTypeByName(ProjectType);
	}

	@Transactional
	public int updateProjectType(ProjectType ProjectType) {
		return ProjectTypeDao.updateProjectType(ProjectType);
	}

	@Transactional
	public int deleteProjectType(Integer id) {
		return ProjectTypeDao.deleteProjectType(id);
	}


	@Transactional
	public int deleteProjectTypes(Data ds) {
		return ProjectTypeDao.deleteProjectTypes(ds);
	}






}
