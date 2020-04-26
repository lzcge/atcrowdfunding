package lzcge.crowdfunding.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;


	private Byte status;

	private String deploydate;

	private Long supportmoney;

	private Integer supporter;

	private Integer completion;

	private Integer memberid;

	private Integer follower;

	// 分类id集合
	private List<Integer> typeIdList;

	// 标签id集合
	private List<Integer> tagIdList;

	// 项目名称
	private String projectName;

	// 项目描述
	private String projectDescription;

	// 计划筹集的金额
	private Long money;

	// 筹集资金的天数
	private Integer day;

	// 创建项目的日期
	private String createdate;

	// 头图的路径
	private String headerPicturePath;

	// 详情图片的路径
	private List<String> detailPicturePathList;


	public Project(Integer id, Byte status, String deploydate, Long supportmoney, Integer supporter, Integer completion, Integer memberid, Integer follower, List<Integer> typeIdList, List<Integer> tagIdList, String projectName, String projectDescription, Long money, Integer day, String createdate, String headerPicturePath, List<String> detailPicturePathList) {
		this.id = id;
		this.status = status;
		this.deploydate = deploydate;
		this.supportmoney = supportmoney;
		this.supporter = supporter;
		this.completion = completion;
		this.memberid = memberid;
		this.follower = follower;
		this.typeIdList = typeIdList;
		this.tagIdList = tagIdList;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.money = money;
		this.day = day;
		this.createdate = createdate;
		this.headerPicturePath = headerPicturePath;
		this.detailPicturePathList = detailPicturePathList;
	}

	public Project(){}
}