package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.OrderNotFoundException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理订单数据和订单商品数据的业务层实现类
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICartService cartService;
	@Autowired
	private IProductService productService;

	@Override
	@Transactional
	public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
		// 创建当前时间对象
		Date now = new Date();
		// 根据参数cids，通过cartService的getByCids()查询购物车数据，得到List<CartVO>类型的对象
		List<CartVO> carts = cartService.getByCids(cids, uid);
		// 遍历以上购物车数据集合对象以计算总价
		Long totalPrice = 0L;
		for(CartVO cart : carts){
			totalPrice += cart.getRealPrice()*cart.getPrice();
		}

		// 创建Order对象
		Order order = new Order();
		// 补Order对象属性：uid > 参数uid
		order.setUid(uid);
		// 根据参数aid，通过addressService的getByAid()方法查询收货地址详情
		Address address = addressService.getByAid(aid);
		// 补Order对象属性：recv_*
		order.setRecvName(address.getName());
		order.setRecvPhone(address.getPhone());
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAddress());

		// 补Order对象属性：total_price > 以上遍历时的计算结果
		order.setTotalPrice(totalPrice);
		// 补Order对象属性：status > 0
		order.setStatus(0);
		// 补Order对象属性：order_time > 当前时间
		order.setOrderTime(now);
		// 补Order对象属性：pay_time > null
		order.setPayTime(null);
		// 补Order对象属性：日志 > 参数username，当前时间
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		// 插入订单数据：insertOrder(order)
		insertOrder(order);

		// 准备订单商品数据的集合，后续可用于归还商品库存
		List<OrderItem> orderItems = new ArrayList<>();
		// 遍历购物车数据集合对象
		for(CartVO cart : carts){
			// -- 创建OrderItem对象
			OrderItem item = new OrderItem();
			// -- 补OrderItem对象属性：oid > order.getOid();
			item.setOid(order.getOid());
			// -- 补OrderItem对象属性：pid, title, image, price, num > 遍历对象中的pid, title, iamge ,realPrice, num
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getRealPrice());
			item.setNum(cart.getNum());
			// -- 补OrderItem对象属性：日志 > 参数username，当前时间
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifiedUser(username);
			item.setModifiedTime(now);
			// -- 插入订单商品数据：insertOrderItem(orderItem)
			insertOrderItem(item);
			//销库存
			productService.reduceNum(cart.getPid(), cart.getNum());
		}

		//删除购物车中对应的数据
		cartService.delete(cids, uid);

		// 开启倒计时任务(Timer/Thread)，如果用户在规定时间内未支付，则关闭订单，并归还库存
		new Thread() {
			public void run() {
				System.err.println("OrderService：计划15分钟后检查订单状态，准备关闭订单");
				try {
					Thread.sleep(15 * 60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("OrderService：准备关闭订单……");
				close(order.getOid(), orderItems, username);
			}
		}.start();

		// 返回成功创建的订单对象
		return order;
	}

	@Override
	public void changeStatus(Integer oid, Integer status, String username) {
		// 根据参数oid查询订单状态
		Order result = findByOid(oid);
		// 判断查询结果是否不存在：OrderNotFoundException
		if(result == null){
			throw new OrderNotFoundException("修改订单状态失败！尝试访问的数据不存在！");
		}

		// 执行修改订单状态
		updateStatus(oid, status, username, new Date());
	}

	@Override
	@Transactional
	public void close(Integer oid, List<OrderItem> orderItems, String username) {
		// 检查订单状态是否是“未支付”
		Order result = findByOid(oid);
		// 检查查询结果是否不存在
		if (result == null) {
			throw new OrderNotFoundException(
					"关闭订单失败！尝试访问的数据不存在！");
		}

		// 检查订单当前状态
		if (result.getStatus() != Status.UNPAID) {
			return;
		}

		// 将订单状态修改为已关闭
		changeStatus(oid, Status.CLOSED, username);

		// 归还订单中所有商品的库存
		for (OrderItem orderItem : orderItems) {
			productService.addNum(orderItem.getPid(), orderItem.getNum());
		}
		System.err.println("OrderService：订单已关闭！");
	}

	/**
	 * 插入订单数据
	 * @param order 订单数据
	 * @return 受影响的行数
	 */
	private void insertOrder(Order order){
		Integer rows = orderMapper.insertOrder(order);
		if(rows !=1){
			throw new InsertException("创键订单失败!插入订单数据时查询未知异常!");
		}
	}

	/**
	 * 插入订单商品数据
	 * @param orderItem 订单商品数据
	 * @return 受影响的行数
	 */
	private void insertOrderItem(OrderItem orderItem){
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if(rows !=1){
			throw new InsertException("创键订单失败!插入订单商品数据时查询未知异常!");
		}
	}

	/**
	 * 修改订单状态
	 * @param oid 订单id
	 * @param status 状态值，0-未支付，1-已支付，2-已取消，3-关闭
	 * @param username 修改执行人
	 * @param modifiedTime 修改时间
	 * @throws UpdateException 更新数据异常
	 */
	private void updateStatus(Integer oid, Integer status,
	                          String username, Date modifiedTime) throws UpdateException {
		Integer rows = orderMapper.updateStatus(oid, status, username, modifiedTime);
		if (rows != 1) {
			throw new UpdateException(
					"修改订单状态失败！更新数据时出现未知错误！");
		}
	}

	/**
	 * 根据订单id查询订单详情
	 * @param oid 订单id
	 * @return 匹配的订单详情，如果没有匹配的数据，则返回null
	 */
	private Order findByOid(Integer oid){
		return orderMapper.findByOid(oid);
	}
}
