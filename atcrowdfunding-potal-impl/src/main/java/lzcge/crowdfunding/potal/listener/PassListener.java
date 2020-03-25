package lzcge.crowdfunding.potal.listener;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.potal.service.MemberService;
import lzcge.crowdfunding.potal.service.TicketService;
import lzcge.crowdfunding.util.ApplicationContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-03-18
 **/
public class PassListener implements ExecutionListener{

	@Override
	public void notify(DelegateExecution delegateExecution) throws Exception {
		System.out.println("passlistener");
		Integer memberid = (Integer)delegateExecution.getVariable("memberid");

		//ApplicationContext ioc =  new ClassPathXmlApplicationContext(""); //不能自己创建ioc容器,保证容器是唯一的.
		//ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application); //需要获取application对象,可以使用流程变量,或ThreadLocal


		//获取IOC容器.通过自定义的工具类,实现Spring接口,以接口注入的方式获取IOC容器对象.
		ApplicationContext applicationContext = ApplicationContextUtils.applicationContext;

		//获取TicketService, MemberService对象.
		TicketService ticketService = applicationContext.getBean(TicketService.class);
		MemberService memberService = applicationContext.getBean(MemberService.class);

		//更新t_member表的authstatus字段: 1 -> 2 -  已实名认证
		Member me = new Member();
		me.setId(memberid);
		Member member = memberService.selectById(me);
		member.setAuthstatus("2");
		memberService.updateMemberAuthStatus(member);

		//更新t_ticket表的status字段 0 -> 1 表示流程结束
		ticketService.updateStatus(member);
	}
}
