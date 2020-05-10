package lzcge.crowdfunding.manager.service;

import lzcge.crowdfunding.entity.Order;
import lzcge.crowdfunding.util.Page;
import lzcge.crowdfunding.vo.Data;

import java.util.Map;


public interface OrderService {


	public Page<Order> pageQueryOrder(Map<String, Object> orderMap);

	public int queryCount(Map<String, Object> orderMap);

	public Order queryById(Integer id);

	public int deleteOrder(Integer id);

	public int deleteOrders(Data ds);

	public void updateOrder(Order order);


}
