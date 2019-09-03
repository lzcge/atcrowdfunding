package lzcge.crowdfunding.vo;

import lombok.Data;

/**
 * @description:
 * @author: lzcge
 * @create: 2019-08-15
 **/

@Data
public class QueryIndexVo {
	private Integer pageNo;//页号
	private Integer pageSize;//每页的条数
	private String queryText;//模糊查询内容

}
