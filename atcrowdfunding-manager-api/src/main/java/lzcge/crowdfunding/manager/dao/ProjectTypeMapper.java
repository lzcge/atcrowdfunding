package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface ProjectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectType record);

    ProjectType selectByPrimaryKey(Integer id);

    List<ProjectType> selectAll();

    int updateByPrimaryKey(ProjectType record);

	ProjectType queryProjectType(Map<String, Object> paramMap);

	List<ProjectType> pageQuery(Map<String, Object> paramMap);

	int queryCount(Map<String, Object> paramMap);

	void insertProjectType(ProjectType ProjectType);

	ProjectType queryById(Integer id);

	ProjectType queryProjectTypeByName(ProjectType ProjectType);

	int updateProjectType(ProjectType ProjectType);

	int deleteProjectType(Integer id);

	int deleteProjectTypes(Data ds);

}