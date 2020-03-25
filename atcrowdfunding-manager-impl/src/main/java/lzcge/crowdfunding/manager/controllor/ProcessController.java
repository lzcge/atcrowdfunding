package lzcge.crowdfunding.manager.controllor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Controller
@RequestMapping("/process")
public class ProcessController {

	@Autowired
	private RepositoryService repositoryService ;
	
	@RequestMapping("/index")
	public String index(){		
		return "process/index";
	}
	
	@RequestMapping("/showimg")
	public String showimg(){		
		return "process/showimg";
	}


	/**
	 * 展示流程图片
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/showimgProDef")
	public void showimgProDef(String id,HttpServletResponse response) throws IOException{	 //流程定义id
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
		
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());

		ServletOutputStream outputStream = response.getOutputStream();
		
		IOUtils.copy(resourceAsStream, outputStream);		
	}

	/**
	 * 删除流程定义
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(String id){	 //流程定义id
		
		JsonResult result = new JsonResult();
		
		try {
			
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			
			repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);//true表示级联删除.
			
			result.setInfo("success");
			result.setData(200);
			
		} catch (Exception e) {
			result.setInfo("false");
			result.setData("部署流程定义失败!");
			e.printStackTrace();
		}
		
		
		return result;
	}


	/**
	 * 流程定义部署
	 * @param request
	 * @return
	 */
	@ResponseBody
	@PostMapping("/deploy")
	public Object deploy(HttpServletRequest request){	
		
		JsonResult result = new JsonResult();
		
		try {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			
			MultipartFile multipartFile  = multipartHttpServletRequest.getFile("processDefFile");

			repositoryService.createDeployment().addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream()).deploy();

			result.setInfo("success");
			result.setData(200);
		} catch (Exception e) {
			result.setInfo("false");
			result.setData("部署流程定义失败!");
			e.printStackTrace();
		}
		
		
		return result;
	}

	/**
	 * 流程定义查询
	 * @param queryIndexVo
	 * @return
	 */
	@ResponseBody
	@PostMapping("/doIndex")
	public Object doIndex(QueryIndexVo queryIndexVo){
		JsonResult result = new JsonResult();
		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();



		try {
			Page page = new Page(pageno,pagesize);

			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			//查询流程定义集合数据,可能出现了自关联,导致Jackson组件无法将集合序列化为JSON串.
			List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
			
			List<Map<String,Object>> mylistPage = new ArrayList<Map<String,Object>>();

			boolean condition = false;


			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
				condition = true;
			}

			if(condition){
				//模糊匹配，用于模糊查询(大小写不敏感)
				Pattern pattern = Pattern.compile(pagetext,Pattern.CASE_INSENSITIVE);
				for (ProcessDefinition processDefinition : listPage) {
					Map<String,Object> pd = new HashMap<String,Object>();
					String matchString = processDefinition.getName()+processDefinition.getKey()+processDefinition.getVersion();
					Matcher matcher = pattern.matcher(matchString);
					if(matcher.find()){
						pd.put("id", processDefinition.getId());
						pd.put("name", processDefinition.getName());
						pd.put("key", processDefinition.getKey());
						pd.put("version", processDefinition.getVersion());
						mylistPage.add(pd);
					}
				}
			}else{
				for (ProcessDefinition processDefinition : listPage) {
					Map<String,Object> pd = new HashMap<String,Object>();
					pd.put("id", processDefinition.getId());
					pd.put("name", processDefinition.getName());
					pd.put("key", processDefinition.getKey());
					pd.put("version", processDefinition.getVersion());
					mylistPage.add(pd);
				}
			}

			
			Long totalsize = processDefinitionQuery.count();
			
			page.setDatas(mylistPage);
			
			page.setTotalSize(totalsize.intValue());
			
//			result.setPage(page);
			result.setInfo("success");
			result.setData(page);
		} catch (Exception e) {
			result.setInfo("false");
			result.setData("查询流程定义失败!");
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
}
