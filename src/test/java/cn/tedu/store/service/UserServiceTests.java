package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	IUserService service;
	
	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("back");
			user.setPassword("1234");
			service.reg(user);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
		try {
			String username = "root";
			String password = "1234";
			User result = service.login(username, password);
			System.err.println(result);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changePassword() {
		try {
			Integer uid = 9;
			String oldPassword = "8888";
			String newPassword = "1234";
			String modifiedUser = "系统管理员";
			service.changePassword(uid, oldPassword, newPassword, modifiedUser);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeInfo() {
		try {
			Integer uid = 700;
			String username = "系统管理员";
			User user = new User();
			user.setGender(1);
			user.setPhone("13700137007");
			user.setEmail("root@163.com");
			service.changeInfo(uid, username, user);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		try {
			Integer uid = 7;
			User data = service.getByUid(uid);
			System.err.println(data);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void changeAvatar() {
		try {
			Integer uid = 7;
			String avatar = "88888888";
			String modifiedUser = "系统管理员";
			service.changeAvatar(uid, avatar, modifiedUser);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}





