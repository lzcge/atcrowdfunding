package lzcge.crowdfunding.vo;

import lzcge.crowdfunding.entity.MemberCert;
import lzcge.crowdfunding.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-09-04
 **/
public class Data {
	private List<User> userList = new ArrayList<>();
	private List<User> datas = new ArrayList<User>();

	private List<Integer> ids ;

	private List<MemberCert> certimgs = new ArrayList<MemberCert>();

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getDatas() {
		return datas;
	}

	public void setDatas(List<User> datas) {
		this.datas = datas;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<MemberCert> getCertimgs() {
		return certimgs;
	}

	public void setCertimgs(List<MemberCert> certimgs) {
		this.certimgs = certimgs;
	}

}
