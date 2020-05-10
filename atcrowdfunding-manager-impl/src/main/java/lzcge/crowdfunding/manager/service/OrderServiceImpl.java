package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Order;
import lzcge.crowdfunding.entity.Return;
import lzcge.crowdfunding.manager.dao.OrderMapper;
import lzcge.crowdfunding.manager.dao.ReturnMapper;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;


	@Override
	@Transactional
	public void  updateOrder(Order Order) {
		orderMapper.updateOrder(Order);
	}


	@Override
	public Page<Order> pageQueryOrder(Map<String, Object> orderMap) {
		Page<Order> OrderPage = new Page<Order>((Integer)orderMap.get("pageno"),(Integer)orderMap.get("pagesize"));
		orderMap.put("startIndex", OrderPage.getStartIndex());
		List<Order> OrderList= orderMapper.pageQueryOrder(orderMap);
		// 获取数据的总条数
		int count = orderMapper.queryCount(orderMap);

		OrderPage.setDatas(OrderList);
		OrderPage.setTotalSize(count);
		return OrderPage;
	}

	@Override
	public int queryCount(Map<String, Object> orderMap) {
		return orderMapper.queryCount(orderMap);
	}

	@Override
	public Order queryById(Integer id) {
		return orderMapper.queryOrderById(id);
	}

	@Override
	@Transactional
	public int deleteOrder(Integer id) {
		return orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public int deleteOrders(Data ds) {
		return orderMapper.deleteOrders(ds);
	}
}
