package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Return;
import java.util.List;

public interface ReturnMapper {



	Return selectByProjectId(Integer projectid);


}