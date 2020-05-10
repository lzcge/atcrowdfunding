package lzcge.crowdfunding.manager.dao;

import lzcge.crowdfunding.entity.Order;
import lzcge.crowdfunding.entity.Order;
import lzcge.crowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

	int queryCount(Map<String, Object> OrderMap);

	List<Order> pageQueryOrder(Map<String, Object> OrderMap);

	public int deleteOrders(Data ds);

	void updateOrder(Order order);
	

	Order queryOrderById(@Param("id") Integer orderid);

	List<Order> queryOrderBymemberid(Integer memberid);

	List<Order> querySupportOrderOrPay(Order order);

	List<Order> queryByStatus(Map<String,Object> statuMap);


}