package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

	@Autowired
	AddressMapper mapper;
	
	@Test
	public void addnew() {
		Address address = new Address();
		address.setUid(1);
		address.setName("小刘同学");
		Integer rows = mapper.addnew(address);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void deleteByAid() {
		Integer aid = 20;
		Integer rows = mapper.deleteByAid(aid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateDefault() {
		Integer aid = 30;
		String username = "哈哈";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateDefault(aid, username, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNonDefault() {
		Integer uid = 7;
		Integer rows = mapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void countByUid() {
		Integer uid = 100;
		Integer count = mapper.countByUid(uid);
		System.err.println("count=" + count);
	}
	
	@Test
	public void findByAid() {
		Integer aid = 30;
		Address result = mapper.findByAid(aid);
		System.err.println(result);
	}
	
	@Test
	public void findLastModified() {
		Integer uid = 7;
		Address result = mapper.findLastModified(uid);
		System.err.println(result);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 7;
		List<Address> list = mapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (Address item : list) {
			System.err.println(item);
		}
	}
	
}









