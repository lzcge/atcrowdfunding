package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.entity.Return;
import lzcge.crowdfunding.manager.dao.AdvertMapper;
import lzcge.crowdfunding.manager.dao.ProjectMapper;
import lzcge.crowdfunding.manager.dao.ReturnMapper;
import lzcge.crowdfunding.po.ProjectDetailPO;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ReturnMapper returnMapper;

	@Override
	public Page<ProjectDetailPO> pageQuery(Map<String, Object> projectMap) {
		Page<ProjectDetailPO> projectDetailPOPage = new Page<ProjectDetailPO>((Integer)projectMap.get("pageno"),(Integer)projectMap.get("pagesize"));
		projectMap.put("startIndex", projectDetailPOPage.getStartIndex());
		List<Project> projectList = projectMapper.selectProjectAuth(projectMap);
		List<ProjectDetailPO> projectDetailPOS =new ArrayList<>();
		//查询对应的回报
		for (int i = 0; i < projectList.size(); i++) {
			ProjectDetailPO projectDetailPO = new ProjectDetailPO();
			Project project = projectList.get(i);
			Return aReturn = returnMapper.selectByProjectId(project.getId());
			projectDetailPO.setProject(project);
			projectDetailPO.setAreturn(aReturn);
			projectDetailPOS.add(projectDetailPO);
		}
		int count = projectMapper.queryCount(projectMap);

		projectDetailPOPage.setDatas(projectDetailPOS);
		projectDetailPOPage.setTotalSize(count);
		return projectDetailPOPage;
	}



	@Override
	@Transactional
	public void  updateProject(Project project) {
		projectMapper.updateProject(project);
	}


	@Override
	public Page<Project> pageQueryProject(Map<String, Object> projectMap) {
		Page<Project> projectPage = new Page<Project>((Integer)projectMap.get("pageno"),(Integer)projectMap.get("pagesize"));
		projectMap.put("startIndex", projectPage.getStartIndex());
		List<Project> projectList= projectMapper.pageQueryProject(projectMap);
		// 获取数据的总条数
		int count = projectMapper.queryCount(projectMap);

		projectPage.setDatas(projectList);
		projectPage.setTotalSize(count);
		return projectPage;
	}

	@Override
	public int queryCount(Map<String, Object> projectMap) {
		return projectMapper.queryCount(projectMap);
	}

	@Override
	public Project queryById(Integer id) {
		return projectMapper.queryById(id);
	}

	@Override
	@Transactional
	public int deleteProject(Integer id) {
		return projectMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int deleteProjects(Data ds) {
		return projectMapper.deleteProjects(ds);
	}
}
