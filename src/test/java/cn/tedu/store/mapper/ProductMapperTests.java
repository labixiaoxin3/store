package cn.tedu.store.mapper;

import cn.tedu.store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperTests {

	@Autowired
	ProductMapper mapper;
	
	@Test
	public void findHotList() {
		List<Product> list = mapper.findHotList();
		System.err.println("count=" + list.size());
		for (Product item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findById() {
		Integer id = 10000022;
		Product data = mapper.findById(id);
		System.err.println(data);
	}

	@Test
	public void a() {
		Integer pid = 10000022;
		Integer num = 100;
		Integer rows = mapper.updateNum(pid, num);
		System.err.println(rows);
	}
	
}









