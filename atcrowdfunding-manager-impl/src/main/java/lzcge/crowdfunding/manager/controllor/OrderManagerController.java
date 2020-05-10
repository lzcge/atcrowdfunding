package lzcge.crowdfunding.manager.controllor;

import lzcge.crowdfunding.entity.Order;
import lzcge.crowdfunding.manager.service.OrderService;
import lzcge.crowdfunding.result.JsonResult;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.util.StringUtil;
import lzcge.crowdfunding.vo.Data;
import lzcge.crowdfunding.vo.QueryIndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * @description:
 * @author: lzcge
 * @create: 2020-04-26
 **/

@Controller
@RequestMapping("/orderManager")
public class OrderManagerController {


	@Autowired
	private OrderService orderService;


	@RequestMapping("/index")
	public String index() {
		return "order/index";
	}

	@RequestMapping("/add")
	public String add() {
		return "order/add";
	}

	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {

		// 根据主键查询项目信息
		Order order = orderService.queryById(id);
		model.addAttribute("order", order);

		return "order/edit";
	}

	/**
	 * 批量删除
	 * @param ds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/batchDelete")
	public Object batchDelete( Data ds ) {
		JsonResult result = new JsonResult();

		try {
			int count = orderService.deleteOrders(ds);
			if ( count == ds.getIds().size() ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Integer id ) {
		JsonResult result = new JsonResult();

		try {
			int count = orderService.deleteOrder(id);
			if ( count == 1 ) {
				result.setData(200);
				result.setInfo("success");
			} else {
				result.setInfo("false");
			}
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}


		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Object update( Order order ) {
		JsonResult result = new JsonResult();

		try {
			orderService.updateOrder(order);
			result.setData(200);
			result.setInfo("success");

		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}






	/**
	 * 分页查询项目数据
	 * @return
	 */
	@ResponseBody
	@PostMapping("/pageQuery")
	public Object pageQuery(QueryIndexVo queryIndexVo) {
		String pagetext = queryIndexVo.getQueryText();
		Integer pageno = queryIndexVo.getPageNo();
		Integer pagesize = queryIndexVo.getPageSize();
		JsonResult result = new JsonResult();

		try {
			// 查询项目数据
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("pageno", pageno);
			orderMap.put("pagesize", pagesize);
			if ( StringUtil.isNotEmpty(pagetext) ) {
				pagetext = pagetext.replaceAll("%", "\\\\%");
			}
			orderMap.put("pagetext", pagetext);

			// 分页查询
			Page<Order> page = orderService.pageQueryOrder(orderMap);
			//result.setPage(page);
			result.setData(page);
			result.setInfo("success");
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setInfo("false");
		}

		return result;
	}
}
