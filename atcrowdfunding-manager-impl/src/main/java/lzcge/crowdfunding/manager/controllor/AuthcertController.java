package lzcge.crowdfunding.manager.controllor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.potal.service.MemberService;
import lzcge.crowdfunding.potal.service.TicketService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/authcert")
public class AuthcertController {
	
	@Autowired
	private TaskService taskService ;
	
	@Autowired
	private RepositoryService repositoryService ;
	
	@Autowired
	private TicketService ticketService ;
	
	@Autowired
	private MemberService memberService ;

	@RequestMapping("/index")
	public String index(){
		return "authcert/index";
	}
	
	
	@ResponseBody
	@RequestMapping("/pass")
	public Object pass( String taskid, Integer memberid ) {
		JsonResult result = new JsonResult();
		
		try {
			taskService.setVariable(taskid, "flag", true);
			taskService.setVariable(taskid, "memberid", memberid);
			// 传递参数，让流程继续执行
			taskService.complete(taskid);
			result.setData(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping("/refuse")
	public Object refuse(String taskid, Integer memberid) {
		JsonResult result = new JsonResult();
		
		try {
			taskService.setVariable(taskid, "flag", false);
			taskService.setVariable(taskid, "memberid", memberid);
			// 传递参数，让流程继续执行
			taskService.complete(taskid);
			result.setData(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}		
		
		return result;
	}

	
	
	@RequestMapping("/show")
	public String show(Integer memberid,Map<String,Object> map){

		Member me = new Member();
		me.setId(memberid);
		Member member = memberService.selectById(me);
		
		List<Map<String,Object>> list = memberService.queryCertByMemberid(me);

		map.put("member", member);
		map.put("certimgs", list);
		
		return "authcert/show";
	}
	
	
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(@RequestParam(value="pageno",required=false,defaultValue="1") Integer pageno,
			@RequestParam(value="pagesize",required=false,defaultValue="10") Integer pagesize){

		JsonResult result = new JsonResult();
		
		try {
			
			Page page = new Page(pageno,pagesize);
			
			//1.查询后台authusers委托组的任务
			TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth")
			.taskCandidateGroup("authusers");
			List<Task> listPage = taskQuery
						.listPage(page.getStartIndex(), pagesize);


			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

			for (Task task : listPage) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("taskid", task.getId());
				map.put("taskName", task.getName());
				
				//2.根据任务查询流程定义(流程定义名称,流程定义版本)
				ProcessDefinition processDefinition = repositoryService
								.createProcessDefinitionQuery()
								.processDefinitionId(task.getProcessDefinitionId())
								.singleResult();
				
				
				map.put("procDefName", processDefinition.getName());
				map.put("procDefVersion", processDefinition.getVersion());
				
				
				//3.根据任务查询流程实例(根据流程实例的id查询流程单,查询用户信息)
				Member member = ticketService.getMemberByPiid(task.getProcessInstanceId());
				
				map.put("member",member);
				
				data.add(map);
				
			}
			
			page.setDatas(data);
			
			Long count = taskQuery.count();
			page.setTotalSize(count.intValue());
			
			result.setData(page);
			
			result.setInfo("success");
		} catch (Exception e) {
			result.setData(false);
			result.setInfo("任务查询列表失败!");
			e.printStackTrace();
		}
		
		return result;
	}
	
}
