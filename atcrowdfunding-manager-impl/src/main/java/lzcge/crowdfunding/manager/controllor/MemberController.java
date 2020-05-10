package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.manager.service.MemberService;
import lzcge.crowdfunding.manager.service.ProjectService;
import lzcge.crowdfunding.po.MemberDetail;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-04-28
 **/
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;


	@RequestMapping("/index")
	public String index() {
		return "member/index";
	}

	@RequestMapping("/add")
	public String add() {
		return "member/add";
	}



	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {

		Member membervo = new Member();
		membervo.setId(id);
		// 根据主键查询项目信息
		Member member = memberService.selectById(membervo);
		model.addAttribute("member", member);

		return "member/edit";
	}




	/**
	 * 批量删除
	 * @param ds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete( Data ds ) {
		JsonResult result = new JsonResult();

		try {
			int count = memberService.deleteMembers(ds);
			if ( count == ds.getIds().size() ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		JsonResult result = new JsonResult();

		try {
			int count = memberService.deleteMember(id);
			if ( count == 1 ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}


		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Object update( Member member ) {
		JsonResult result = new JsonResult();

		try {
			memberService.updateMember(member);
			result.setData(200);
			result.setInfo("success");

		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}






	/**
	 * 分页查询用户数据
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
			// 查询项目数据
			Map<String, Object> memberMap = new HashMap<String, Object>();
			memberMap.put("pageno", pageno);
			memberMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			memberMap.put("pagetext", pagetext);
			// 分页查询
			Page<MemberDetail> page = memberService.pageQueryMember(memberMap);
			result.setData(page);
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}
}
