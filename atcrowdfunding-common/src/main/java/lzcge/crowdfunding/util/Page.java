package lzcge.crowdfunding.util;

import lzcge.crowdfunding.entity.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-08
 **/

public class Page {
	private Integer pageNo;
	private Integer pageSize;
	private List<?> datas;
	private Integer totalSize;
	private Integer totalNo;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getDatas() {
		return datas;
	}

	public void setDatas(List<?> datas) {
		this.datas = datas;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
		this.totalNo =(totalSize % pageSize) == 0 ? totalSize / pageSize :(totalSize / pageSize)+1;
	}

	public Integer getTotalNo() {
		return totalNo;
	}

	private void setTotalNo(Integer totalNo) {
		this.totalNo = totalNo;

	}

	public Page(Integer pageNo,Integer pageSize){
		if(pageNo <= 0){
			this.pageNo = 1;
		}else{
			this.pageNo = pageNo;
		}
		if(pageSize <= 0){
			this.pageSize = 10;
		}else{
			this.pageSize = pageSize;
		}

	}

	//获取开始查询的开始记录坐标
	public Integer getStartIndex(){
		return (this.pageNo-1)*pageSize;
	}






}
