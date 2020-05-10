package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Advert;
import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.manager.service.AdvertService;
import lzcge.crowdfunding.manager.service.ProjectService;
import lzcge.crowdfunding.po.MemberDetail;
import lzcge.crowdfunding.po.ProjectDetailPO;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Const;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.Data;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	

	@RequestMapping("/authproject/index")
	public String index() {
		return "project/index";
	}


	@RequestMapping("/show")
	public String show(@RequestParam("id") Integer id, Model model){
		Project project = projectService.queryById(id);
		model.addAttribute("project",project);
		return "project/show";
	}


	
	/**
	 * 分页查询资质数据
	 * @advert pageno
	 * @advert pagesize
	 * @return
	 */
	@ResponseBody
	@PostMapping("/auth/pageQuery.do")
	public Object authStatusProjects(QueryIndexVo queryIndexVo) {
		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();
		JsonResult result = new JsonResult();
		
		try {
			// 查询project
			Map<String, Object> projectMap = new HashMap<String, Object>();
			projectMap.put("pageno", pageno);
			projectMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			projectMap.put("pagetext", pagetext);
			
			// 分页查询
			Page<ProjectDetailPO> page = projectService.pageQuery(projectMap);
			//result.setPage(page);
			result.setData(page);
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}
		
		return result;
	}



	/**
	 * 审核
	 * @advert pageno
	 * @advert pagesize
	 * @return
	 */
	@ResponseBody
	@PostMapping("/update/project.do")
	public Object authStatusProjects(Project project) {

		JsonResult result = new JsonResult();
		try {
			projectService.updateProject(project);
			//result.setPage(page);
			result.setData("111");
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}
}
