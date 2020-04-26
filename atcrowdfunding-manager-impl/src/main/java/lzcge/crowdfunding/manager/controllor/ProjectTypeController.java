package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.entity.ProjectType;
import lzcge.crowdfunding.manager.service.ProjectTypeService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.Data;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/projectType")
public class ProjectTypeController {

	@Autowired
	private ProjectTypeService projectTypeService;
	

	@RequestMapping("/index")
	public String index() {
		return "projecttype/index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "projecttype/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		// 根据主键查询类别信息
		ProjectType projectType = projectTypeService.queryById(id);
		model.addAttribute("projectType", projectType);
		
		return "projecttype/edit";
	}
	
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes( Data ds ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = projectTypeService.deleteProjectTypes(ds);
			if ( count == ds.getIds().size() ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = projectTypeService.deleteProjectType(id);
			if ( count == 1 ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( ProjectType projectType ) {
		JsonResult result = new JsonResult();
		
		try {
			int count = projectTypeService.updateProjectType(projectType);
			if ( count == 1 ) {
				result.setData(true);
			} else {
				result.setData(false);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	/**
	 * 新增类别数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert( ProjectType projectType ) {
		JsonResult result = new JsonResult();
		
		try {
			result = projectTypeService.insertProjectType(projectType);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}
	
	/**
	 * 分页查询类别数据
	 * @return
	 */
	@ResponseBody
	@PostMapping("/pageQuery")
	public Object pageQuery(QueryIndexVo queryIndexVo) {

		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();

		JsonResult result = new JsonResult();
		
		try {
			// 查询类别数据
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				//pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			paramMap.put("pagetext", pagetext);
			
			// 分页查询数据
			Page<ProjectType> page = projectTypeService.pageQuery(paramMap);

			result.setInfo("success");
			result.setData(page);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
			result.setData(false);
		}
		
		return result;
	}
}
