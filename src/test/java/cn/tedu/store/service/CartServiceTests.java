package cn.tedu.store.service;

import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

	@Autowired
	ICartService service;
	
	@Test
	public void addToCart() {
		try {
			Integer uid = 100;
			Integer pid = 10000017;
			Integer num = 3;
			String username = "土豪";
			service.addToCart(uid, pid, num, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 7;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}

	@Test
	public void addNum() {
		try {
			Integer cid = 6;
			Integer uid = 7;
			String username = "土豪";
			service.addNum(cid, uid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void reduceNum() {
		try {
			Integer cid = 11;
			Integer uid = 7;
			String username = "土豪";
			service.reduceNum(cid, uid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void delete() {
		try {
			Integer cid = 6;
			Integer uid = 7;
			service.delete(cid, uid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = { 6, 7, 8 };
		Integer uid = 8;
		List<CartVO> list = service.getByCids(cids, uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}

	@Test
	public void deleteByCids() {
		try {
			Integer[] cids = {8,9};
			Integer uid = 8;
			service.delete(cids, uid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
}









