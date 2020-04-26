package lzcge.crowdfunding.entity;


import lombok.Data;

@Data
public class Return {
	private Integer id;

	private Integer projectid;

	private Byte type;

	private Integer supportmoney;

	private String content;

	private Integer count;

	private Integer signalpurchase;

	private Integer purchase;

	private Integer freight;

	private Byte invoice;

	private Integer returndate;

	private String describPicPath;

	public Return(Integer id, Integer projectid, Byte type, Integer supportmoney, String content, Integer count, Integer signalpurchase, Integer purchase, Integer freight, Byte invoice, Integer returndate, String describPicPath) {
		this.id = id;
		this.projectid = projectid;
		this.type = type;
		this.supportmoney = supportmoney;
		this.content = content;
		this.count = count;
		this.signalpurchase = signalpurchase;
		this.purchase = purchase;
		this.freight = freight;
		this.invoice = invoice;
		this.returndate = returndate;
		this.describPicPath = describPicPath;
	}

	public Return(){}


}