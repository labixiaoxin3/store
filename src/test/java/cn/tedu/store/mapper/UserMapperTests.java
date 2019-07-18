package cn.tedu.store.mapper;

import cn.tedu.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

	@Autowired
	UserMapper mapper;
	
	@Test
	public void addnew() {
		User user = new User();
		user.setUsername("rootx");
		user.setPassword("1234");
		System.err.println(user);
		Integer rows = mapper.addnew(user);
		System.err.println("rows=" + rows);
		System.err.println(user);
	}
	
	@Test
	public void updatePassword() {
		Integer uid = 8;
		String password ="1234";
		String modifiedUser = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateAvatar() {
		Integer uid = 7;
		String avatar ="1234";
		String modifiedUser = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateInfo() {
		User user = new User();
		user.setUid(7);
		user.setPhone("13700137001");
		user.setEmail("root@qq.com");
		user.setGender(1);
		Integer rows = mapper.updateInfo(user);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByUsername() {
		String username = "root";
		User result = mapper.findByUsername(username);
		System.err.println(result);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 7;
		User result = mapper.findByUid(uid);
		System.err.println(result);
	}
	
}






