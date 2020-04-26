package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.entity.Return;
import lzcge.crowdfunding.manager.dao.ProjectMapper;
import lzcge.crowdfunding.manager.dao.ReturnMapper;
import lzcge.crowdfunding.po.ProjectDetailPO;
import lzcge.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ReturnServiceImpl implements ReturnService {

	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private ReturnMapper returnMapper;

	@Override
	public Return selectByProjectId(Integer projectid) {
		return returnMapper.selectByProjectId(projectid);
	}
}
