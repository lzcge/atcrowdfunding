package lzcge.crowdfunding.potal.controllor;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.entity.Ticket;
import lzcge.crowdfunding.potal.listener.PassListener;
import lzcge.crowdfunding.potal.listener.RefuseListener;
import lzcge.crowdfunding.potal.service.MemberService;
import lzcge.crowdfunding.potal.service.TicketService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Const;
import lzcge.crowdfunding.util.DesUtil;
import lzcge.crowdfunding.util.ExceptionUtil;
import lzcge.crowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;


@Controller
@CrossOrigin
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TicketService ticketService;



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
	 * 实名认证-上传更新资质页面跳转
	 * @return
	 */
	@RequestMapping("/uploadCert")
	public String uploadCert(){
		return "member/uploadCert";
	}


	/**
	 * 实名认证-输入邮箱发送验证邮件页面跳转
	 * @return
	 */
	@RequestMapping("/checkemail")
	public String checkemail(){
		return "member/checkemail";
	}


	/**
	 * 实名认证-验证邮箱验证码页面跳转
	 * @return
	 */
	@RequestMapping("/checkauthcode")
	public String checkauthcode(){
		return "member/checkauthcode";
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

	/**
	 * 更新图片资质
	 * @param session
	 * @param ds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doUploadCert")
	public Object doUploadCert( HttpSession session, Data ds) {
		JsonResult result = new JsonResult();

		try {

			// 获取登录会员信息
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			String realPath = session.getServletContext().getRealPath("/pics");
			List<MemberCert> certimgs = ds.getCertimgs();
			for (MemberCert memberCert : certimgs) {
				MultipartFile fileImg = memberCert.getImgfile();
				//文件重命名
				String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
				String tmpName = UUID.randomUUID().toString() +  extName;
				String filename = realPath + "\\cert\\" + tmpName;

				fileImg.transferTo(new File(filename));	//资质文件上传.
				//准备数据
				memberCert.setIconpath(tmpName); //封装数据,保存数据库
				memberCert.setMemberid(loginMember.getId());
			}
			memberService.saveMemberCert(certimgs);

			result.setData(true);
		} catch( Exception e ) {
			e.printStackTrace();
			result.setData(false);
		}

		return result;

	}

	/**
	 * 实名认证-邮箱发送验证码
	 * @param session
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendCode")
	public Object sendCode( HttpSession session, Member member) {
		JsonResult result = new JsonResult();
		// 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
		// PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
		//     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
		String myEmailAccount = "li2961136411@163.com";
//		String myEmailPassword = "li1997525626";
		String myEmailPassword = "FXUEUQFILETPWTUC";//客服端授权码

		// 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
		// 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
		String myEmailSMTPHost = "smtp.163.com";

		// 收件人邮箱（替换为自己知道的有效邮箱）
		String receiveMailAccount = member.getEmail();
		//往指定邮箱发送验证码
		try {

			Properties props = new Properties();                    // 参数配置
			props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
			props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
			props.setProperty("mail.smtp.auth", "true");            // 需要请求认证

			// PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
			//     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
			//     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
//			/*
//			// SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
//			//                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
//			//                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
//			final String smtpPort = "465";
//			props.setProperty("mail.smtp.port", smtpPort);
//			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.setProperty("mail.smtp.socketFactory.fallback", "false");
//			props.setProperty("mail.smtp.socketFactory.port", smtpPort);
//			*/
			// 2. 根据配置创建会话对象, 用于和邮件服务器交互
			Session mailSession = Session.getInstance(props);
			mailSession.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log


			// 1. 创建一封邮件
			MimeMessage message = new MimeMessage(mailSession);

			// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
			message.setFrom(new InternetAddress(myEmailAccount, "lzcge课程设计", "UTF-8"));

			// 3. To: 收件人（可以增加多个收件人、抄送、密送）
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMailAccount, receiveMailAccount+"用户", "UTF-8"));

			// 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
			message.setSubject("邮箱验证", "UTF-8");

			// 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
			String MailVerifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

			message.setContent(receiveMailAccount+":"+MailVerifyCode, "text/html;charset=UTF-8");

			//验证码和创建时间放入session域中
			session.setAttribute("MailVerifyCode",MailVerifyCode);
			session.setAttribute("MailVerifyCodeCreateTime",System.currentTimeMillis());
			// 6. 设置发件时间
			message.setSentDate(new Date());

			// 7. 保存设置
			message.saveChanges();

			// 4. 根据 Session 获取邮件传输对象
			Transport transport = mailSession.getTransport();

			// 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
			//
			//    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
			//           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
			//           类型到对应邮件服务器的帮助网站上查看具体失败原因。
			//
			//    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
			//           (1) 邮箱没有开启 SMTP 服务;
			//           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
			//           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
			//           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
			//           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
			//
			//    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
			transport.connect(myEmailAccount, myEmailPassword);

			// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			transport.sendMessage(message, message.getAllRecipients());
			result.setData(MailVerifyCode);
			// 7. 关闭连接
			transport.close();
		}catch (Exception e){
			e.printStackTrace();
			result.setData(false);
		}

		//设置过期时间
		return result;

	}

	/**
	 * 实名认证-审核验证邮箱验证码
	 * @param session
	 * @param authcode
	 * @return
	 */
	@ResponseBody
	@PostMapping("/finishApply")
	public JsonResult finishApply( HttpSession session, String authcode) {
		JsonResult result = new JsonResult();
		// 获取登录会员信息
		Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

		//获取系统发送验证码
		String MailVerifyCode = (String) session.getAttribute("MailVerifyCode");
		Long MailVerifyCodeCreateTime = (Long) session.getAttribute("MailVerifyCodeCreateTime");
		if(MailVerifyCode==null || MailVerifyCodeCreateTime==null){
			result.setInfo("false");
			result.setData("验证码已失效，请重新发送！");
		}else if(System.currentTimeMillis()-MailVerifyCodeCreateTime>1000*60){//判断验证码是否过期
			result.setInfo("false");
			result.setData("验证码过期，请重新发送！");
			session.removeAttribute("MailVerifyCode");
			session.removeAttribute("MailVerifyCodeCreateTime");
		}else if(!authcode.equals(MailVerifyCode)){
			result.setInfo("false");
			result.setData("验证码错误！");
		}else{

			//更新用户状态,启动后台流程认证
			loginMember.setAuthstatus("1");
			memberService.updateMemberAuthStatus(loginMember);

			//启动后台认证流程
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();

			Map<String,Object> variables= new HashMap<String,Object>();
			variables.put("passListener", new PassListener());
			variables.put("refuseListener", new RefuseListener());

			//记录流程状态
			ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),variables);
			Ticket ticket = new Ticket();
			ticket.setMemberid(loginMember.getId());
			ticket.setPstep("finish");
			ticket.setStatus("0");
			ticket.setPiid(processInstance.getId());
			ticketService.saveTicket(ticket);

			result.setData("success");
			result.setInfo("success");
			session.removeAttribute("MailVerifyCode");
			session.removeAttribute("MailVerifyCodeCreateTime");
		}


		return result;

	}





}
