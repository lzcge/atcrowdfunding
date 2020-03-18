package lzcge.crowdfunding.potal.controllor;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.Ticket;
import lzcge.crowdfunding.potal.service.MemberService;
import lzcge.crowdfunding.potal.service.TicketService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;



	/**
	 * 实名认证-基本信息填写页跳转
	 * @return
	 */
	@RequestMapping("/basicinfo")
	public String basicinfo(HttpSession session){
		Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
		//查出已有数据,信息回显
		Member member1 = memberService.selectById(member);
		session.setAttribute(Const.LOGIN_MEMBER,member1);
		return "member/basicinfo";
	}

	/**
	 * 实名认证跳转
	 * @return
	 */
	@RequestMapping("/realNameAuth")
	public String realNameAuth(){
		return "member/accttype";
	}


	/**
	 * 实名认证-
	 * @return
	 */
	@RequestMapping("/uploadCert")
	public String uploadCert(){
		return "member/uploadCert";
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


	/**
	 * 更新账户类型
	 */
	@ResponseBody
	@PostMapping("/updateAcctType")
	public JsonResult updateAcctType(HttpSession session, Member member ) {
		JsonResult result = new JsonResult();
		String accttype = member.getAccttype();
		Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		// 获取登录会员信息

		loginMember.setAccttype(accttype);
		memberService.updateAcctType(loginMember);

		result.setData(true);

		return result;

	}


	/**
	 * 实名认证-更新账户信息
	 * @param session
	 * @param member
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateBasicinfo")
	public Object updateBasicinfo( HttpSession session, Member member) {
		JsonResult result = new JsonResult();

		// 获取登录会员信息
		Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		loginMember.setRealname(member.getRealname());
		loginMember.setCardnum(member.getCardnum());

		// 更新账户类型
		memberService.updateBasicinfo(loginMember);
		result.setData(true);


		return result;

	}



}
