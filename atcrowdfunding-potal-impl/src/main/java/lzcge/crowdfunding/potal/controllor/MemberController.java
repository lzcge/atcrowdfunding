package lzcge.crowdfunding.potal.controllor;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.potal.service.MemberService;
import lzcge.crowdfunding.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;


	@RequestMapping("/toIndex")
	public String toIndex(){
		return "user/index";
	}


	@RequestMapping("/toAdd")
	public String toAdd(){
		return "user/add";
	}



	/**
	 * 普通会员用户注册
	 * @param
	 * @param
	 * @return
	 */
	@PostMapping(value = "/doRegist",consumes = "application/json")
	@ResponseBody
	public JsonResult doReg(@RequestBody Member member){
		JsonResult jsonResult = memberService.insert(member);
		return jsonResult;
	}



}
