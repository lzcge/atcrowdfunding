package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.entity.Return;
import lzcge.crowdfunding.po.ProjectDetailPO;
import lzcge.crowdfunding.util.Page;

import java.util.Map;


public interface ReturnService {



	public Return selectByProjectId(Integer projectid);


}
