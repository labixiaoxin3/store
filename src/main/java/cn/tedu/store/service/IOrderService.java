package cn.tedu.store.service;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

import java.util.List;

/**
 * 处理订单数据和订单商品数据的业务层接口
 */
public interface IOrderService {

	public static interface Status {
		int UNPAID = 0;
		int PAID = 1;
		int CANCELED = 2;
		int CLOSED = 3;
	}

	/**
	 * 创键订单
	 * @param aid
	 * @param cids
	 * @param uid
	 * @param username
	 * @return
	 */
	Order create(Integer aid, Integer[] cids, Integer uid, String username);

	/**
	 * 修改订单状态
	 * @param oid 订单id
	 * @param status
	 * @param username
	 */
	void changeStatus(Integer oid, Integer status, String username);

	/**
	 * 关闭订单
	 * @param oid 需要关闭的订单id
	 * @param orderItems 需要关闭的订单中的商品列表
	 * @param username 修改执行人
	 */
	void close(Integer oid, List<OrderItem> orderItems, String username);
}

