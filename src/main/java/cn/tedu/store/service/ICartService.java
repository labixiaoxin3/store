package cn.tedu.store.service;

import cn.tedu.store.service.ex.*;
import cn.tedu.store.vo.CartVO;

import java.util.List;

/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {

	/**
	 * 将商品添加到购物车
	 * @param uid 用户id
	 * @param pid 商品id
	 * @param num 商品的数量
	 * @param username 操作执行人
	 * @throws InsertException 插入数据异常
	 * @throws UpdateException 更新数据异常
	 */
	void addToCart(Integer uid, Integer pid, Integer num, String username)
		throws InsertException, UpdateException;
	
	/**
	 * 根据用户id查询该用户的购物车列表
	 * @param uid 用户id
	 * @return 该用户的购物车列表，如果该用户购物车为空，则返回空集合
	 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * 将购物车中的商品的数量增加1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	void addNum(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;
	
	/**
	 * 将购物车中的商品的数量减少1
	 * @param cid 购物车数据id
	 * @param uid 用户id
	 * @param username 用户名
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws UpdateException 更新数据异常
	 */
	void reduceNum(Integer cid, Integer uid, String username) throws CartNotFoundException, AccessDeniedException, UpdateException;
	
	/**
	 * 删除收货地址数据
	 * @param cid 收货地址数据的id
	 * @param uid 当前登录的用户id
	 * @throws CartNotFoundException 购物车数据不存在
	 * @throws AccessDeniedException 访问的是他人的数据
	 * @throws DeleteException 删除数据异常
	 */
	void delete(Integer cid, Integer uid) throws CartNotFoundException, AccessDeniedException, DeleteException;
	
	/**
	 * 根据购物车数据的若干个id值获取匹配的购物车数据的详情
	 * @param cids 若干个购物车数据的id
	 * @return 匹配的购物车数据的详情
	 */
	List<CartVO> getByCids(Integer[] cids, Integer uid);

	void delete(Integer[] cids,Integer uid);
	
}





