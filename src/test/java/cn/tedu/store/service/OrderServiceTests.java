package cn.tedu.store.service;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

	@Autowired
	IOrderService service;

	@Test
	public void create() {
		Integer aid = 18;
		Integer[] cids = { 6,7,8,9 };
		Integer uid = 8;
		String username = "购物狂";
		Order result = service.create(aid, cids, uid, username);
		System.err.println(result);
	}

	@Test
	public void a(){
		try {
			Integer oid = 3;
			Integer status = 0;
			String username = "王梓";
			service.changeStatus(oid, status, username);
			System.err.println("OK.");
		}catch (ServiceException e){
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

}









