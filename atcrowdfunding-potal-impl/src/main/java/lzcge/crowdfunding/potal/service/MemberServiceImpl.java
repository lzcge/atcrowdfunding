package lzcge.crowdfunding.potal.service;

import java.util.List;
import java.util.Map;

import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.User;
import lzcge.crowdfunding.potal.dao.MemberMapper;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.ExceptionUtil;
import lzcge.crowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper ;


	/**
	 * 普通会员登录
	 * @param member
	 * @return
	 */
	@Override
	public Member login(Member member) {
		//密码加密
		member.setUserpswd(MD5Util.digest(member.getUserpswd()));
		Member member1 = memberMapper.login(member);
		if(member1==null){
			ExceptionUtil.isTrue(false,"登录失败，账号或密码错误");
		}else if(!member1.getPhone().equals(member.getPhone())){
			ExceptionUtil.isTrue(false,"登录失败，手机号错误");
		}
		return member1;
	}



	/**
	 * 新增用户
	 * @param member
	 * @return
	 */
	@Transactional
	@Override
	public JsonResult insert(Member member) {
		JsonResult jsonResult = new JsonResult();
		//判断是否注册过（账号、邮箱、电话）
		List<Member> memberByAcctList = memberMapper.selectByAcct(member);
		if(memberByAcctList.size()>0){
			jsonResult.setInfo("500");
			jsonResult.setData("该账号已经被注册过");
		}else{
			List<Member> memberByEmailList = memberMapper.selectByEmail(member);
			if(memberByEmailList.size()>0){
				jsonResult.setInfo("500");
				jsonResult.setData("该邮箱已经被注册过");
			}else{
				List<Member> memberByPhoneList = memberMapper.selectByPhone(member);
				if(memberByPhoneList.size()>0){
					jsonResult.setInfo("500");
					jsonResult.setData("该电话已经被注册过");
				}else{
					//设置用户类型
					if(member.getUsertype().equals("个人") || member.getUsertype().equals("企业")){
						String statu = member.getUsertype().equals("个人")?"0":"1";
						member.setUsertype(statu);
						//设置账号状态
						member.setAuthstatus("0");
						member.setUserpswd(MD5Util.digest(member.getUserpswd()));
						//进行注册
						int memberId = memberMapper.insert(member);
						jsonResult.setInfo("200");
						jsonResult.setData("success");
					}else{
						jsonResult.setInfo("500");
						jsonResult.setData("用户类型错误");
					}
				}
			}
		}

		return jsonResult;

	}
	
}
