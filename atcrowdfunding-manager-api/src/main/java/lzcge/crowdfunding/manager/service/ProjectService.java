package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.po.ProjectDetailPO;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.Map;


public interface ProjectService {

	public Page<ProjectDetailPO> pageQuery(Map<String, Object> projectMap);

	public Page<Project> pageQueryProject(Map<String, Object> projectMap);

	public int queryCount(Map<String, Object> projectMap);

	public Project queryById(Integer id);

	public int deleteProject(Integer id);

	public int deleteProjects(Data ds);

	public void updateProject(Project project);


}
