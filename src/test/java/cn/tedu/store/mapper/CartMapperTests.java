package cn.tedu.store.mapper;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

	@Autowired
	CartMapper mapper;
	
	@Test
	public void insert() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setPid(2);
		cart.setNum(3);
		cart.setPrice(4L);
		Integer rows = mapper.insert(cart);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void deleteByCid() {
		Integer cid = 7;
		Integer rows = mapper.deleteByCid(cid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid = 1;
		Integer num = 10;
		String username = "哈喽";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateNum(cid, num, username, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByCid() {
		Integer cid = 5;
		Cart cart = mapper.findByCid(cid);
		System.err.println(cart);
	}
	
	@Test
	public void findByUidAndPid() {
		Integer uid = 1;
		Integer pid = 2;
		Cart cart = mapper.findByUidAndPid(uid, pid);
		System.err.println(cart);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 7;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = { 6, 7, 8 };
		List<CartVO> list = mapper.findByCids(cids);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}

	@Test
	public void deleteByCids() {
		Integer[] cid ={6,7} ;
		Integer rows = mapper.deleteByCids(cid);
		System.err.println("rows=" + rows);
	}
	
}









