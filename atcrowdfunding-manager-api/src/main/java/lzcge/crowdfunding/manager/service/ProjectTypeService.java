package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;


public interface ProjectTypeService {
	public ProjectType queryProjectType(Map<String, Object> paramMap);

	public Page<ProjectType> pageQuery(Map<String, Object> paramMap);

	public int queryCount(Map<String, Object> paramMap);

	public JsonResult insertProjectType(ProjectType ProjectType);

	public ProjectType queryById(Integer id);

	ProjectType queryProjectTypeByName(ProjectType ProjectType);

	public int updateProjectType(ProjectType ProjectType);

	public int deleteProjectType(Integer id);

	public int deleteProjectTypes(Data ds);



}
