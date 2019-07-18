package cn.tedu.store.service;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {

	@Autowired
	IProductService service;
	
	@Test
	public void getHotList() {
		List<Product> list = service.getHotList();
		System.err.println("count=" + list.size());
		for (Product item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void getById() {
		Integer id = 10000022;
		Product data = service.getById(id);
		System.err.println(data);
	}

	@Test
	public void addNum() {
		try {
			Integer pid = 10000022;
			Integer amount = 80;
			service.addNum(pid, amount);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}









