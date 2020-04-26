package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

	List<Project> selectProjectAuth(Map<String, Object> projectMap);

	int queryCount(Map<String, Object> projectMap);

	List<Project> pageQueryProject(Map<String, Object> projectMap);

	Project queryById(Integer id);

	public int deleteProjects(Data ds);

	void updateProject(Project project);
}