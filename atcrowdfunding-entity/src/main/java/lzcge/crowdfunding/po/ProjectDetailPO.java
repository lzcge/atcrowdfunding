package lzcge.crowdfunding.po;


import lombok.Data;
import lzcge.crowdfunding.entity.Member;
import lzcge.crowdfunding.entity.Project;
import lzcge.crowdfunding.entity.Return;

import java.io.Serializable;

/**
 * @description:
 * @author: lzcge
 * @create: 2020-04-03
 **/
@Data
public class ProjectDetailPO  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Project project;
	private Member member;
	private Return areturn;

	//支持应付总价
	private Integer totalMoney;
	//是否筹资成功
	private boolean flag;


}
