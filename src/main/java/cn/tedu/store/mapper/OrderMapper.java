package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderMapper {
	/**
	 * 插入订单数据
	 * @param order 订单数据
	 * @return 受影响的行数
	 */
	Integer insertOrder(Order order);

	/**
	 * 插入订单商品数据
	 * @param orderItem 订单商品数据
	 * @return 受影响的行数
	 */
	Integer insertOrderItem(OrderItem orderItem);

	/**
	 * 修改订单状态
	 * @param oid 订单id
	 * @param status 状态值，0-未支付，1-已支付，3-关闭
	 * @param username 修改执行人
	 * @param modifiedTime 修改时间
	 * @return 受影响的行数
	 */
	Integer updateStatus(@Param("oid") Integer oid, @Param("status") Integer status,
	                     @Param("username") String username,@Param("modifiedTime") Date modifiedTime);

	/**
	 * 根据订单id查询订单状态
	 * @param oid 订单id
	 * @return
	 */
	Order findByOid(Integer oid);

}
