package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTests {

	@Autowired
	OrderMapper mapper;

	@Test
	public void insertOrder() {
		Order order = new Order();
		order.setUid(1);
		order.setRecvName("小李同学");
		order.setTotalPrice(10086L);
		Integer rows = mapper.insertOrder(order);
		System.err.println("rows=" + rows);
	}

	@Test
	public void insertOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setOid(1);
		orderItem.setTitle("某手机");
		orderItem.setPrice(3000L);
		orderItem.setNum(3);
		Integer rows = mapper.insertOrderItem(orderItem);
		System.err.println("rows=" + rows);
	}

	@Test
	public void updateStatus(){
		Integer oid = 3;
		Integer status = 100;
		String username = "系统";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateStatus(oid, status, username, modifiedTime);
		System.err.println("rows=" + rows);
	}

	@Test
	public void findByOid(){
		Integer oid = 3;
		Order result = mapper.findByOid(oid);
		System.err.println(result);
	}
}
