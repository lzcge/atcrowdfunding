package lzcge.crowdfunding.manager.controllor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.manager.service.MemberService;
import lzcge.crowdfunding.po.MemberDetail;
import lzcge.crowdfunding.potal.service.TicketService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/authcert")
public class AuthcertController {
	


	
	@Autowired
	private MemberService memberService ;

	@RequestMapping("/index")
	public String index(){
		return "authcert/index";
	}

	@RequestMapping("/show")
	public String show(@RequestParam("id") Integer id, Model model){
		Member member = new Member();
		member.setId(id);
		MemberDetail memberDetail =  memberService.selectMemberDetailByMemId(member);
		model.addAttribute("memberDetail",memberDetail);
		return "authcert/show";
	}



	@RequestMapping("/pageQueryMember")
	@ResponseBody
	public Object pageQueryMember(QueryIndexVo queryIndexVo){
		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();
		JsonResult result = new JsonResult();
		try {
			Map<String, Object> memberMap = new HashMap<String, Object>();
			memberMap.put("pageno", pageno);
			memberMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			memberMap.put("pagetext", pagetext);
			//查询正在实名认证中的用户
			memberMap.put("authstatus",'1');

			// 分页查询
			Page<MemberDetail> page = memberService.pageQueryMember(memberMap);
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
	@PostMapping("/update")
	public Object authStatusMember(Member member) {

		JsonResult result = new JsonResult();
		try {
			memberService.updateMember(member);
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
