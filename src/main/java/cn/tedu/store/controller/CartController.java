package cn.tedu.store.controller;

import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 处理购物车相关请求的控制器
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private ICartService cartService;
	
	@RequestMapping("add_to_cart")
	public JsonResult<Void> addToCart(Integer pid, Integer num, HttpSession session) {
	    // 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
	    // 调用业务层对象的方法执行任务
		cartService.addToCart(uid, pid, num, username);
	    // 响应成功
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("/")
	public JsonResult<List<CartVO>>	getByUid(HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		List<CartVO> data = cartService.getByUid(uid);
		// 响应成功
		return new JsonResult<>(SUCCESS, data);
	}
	
	@RequestMapping("{cid}/add_num")
	public JsonResult<Void>	addNum(
			@PathVariable("cid") Integer cid, 
			HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象的方法执行任务
		cartService.addNum(cid, uid, username);
		// 响应成功
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("{cid}/reduce_num")
	public JsonResult<Void>	reduceNum(
			@PathVariable("cid") Integer cid, 
			HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象的方法执行任务
		cartService.reduceNum(cid, uid, username);
		// 响应成功
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("{cid}/delete")
	public JsonResult<Void>	delete(
			@PathVariable("cid") Integer cid, 
			HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		cartService.delete(cid, uid);
		// 响应成功
		return new JsonResult<>(SUCCESS);

}
	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getByCids(
			Integer[] cids, HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层对象的方法执行任务
		List<CartVO> data = cartService.getByCids(cids, uid);
		// 响应成功
		return new JsonResult<>(SUCCESS, data);
	}
	
}









