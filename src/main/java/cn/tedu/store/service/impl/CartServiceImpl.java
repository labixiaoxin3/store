package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.*;
import cn.tedu.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements ICartService {
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private IProductService productService;

	@Override
	public void addToCart(Integer uid, Integer pid, Integer num, String username)
			throws InsertException, UpdateException {
		// 基于参数uid和pid查询数据
		Cart result = findByUidAndPid(uid, pid);
		//	创建当前时间对象
		Date now = new Date();
		// 判断查询结果是否为null
		if (result == null) {
			// 是：需要新增购物车数据
			//	自行创建Cart对象
			Cart cart = new Cart();
			//	调用productService的getById()方法获取单价并封装到Cart对象
			Long price = productService.getById(pid).getPrice();
			cart.setPrice(price);
			//	将uid、pid、num参数封装到Cart对象
			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(num);
			// 将时间和username封装到Cart对象的日志属性
			cart.setCreatedUser(username);
			cart.setCreatedTime(now);
			cart.setModifiedUser(username);
			cart.setModifiedTime(now);
			//	执行插入
			insert(cart);
		} else {
			// 否：需要修改欲购买的商品的数量
			//	从查询结果中获取当前数量num和数据的cid
			Integer oldNum = result.getNum();
			Integer cid = result.getCid();
			//	将以上查询结果中的当前数量num和参数增量num相加，得到新的数量
			Integer newNum = oldNum + num;
			//	执行更新数量
			updateNum(cid, newNum, username, now);
		}
	}

	@Override
	public List<CartVO> getByUid(Integer uid) {
		return findByUid(uid);
	}
	
	@Override
	public void addNum(Integer cid, Integer uid, String username)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if (result == null) {
			throw new CartNotFoundException(
				"增加失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if (result.getUid() != uid) {
			throw new AccessDeniedException(
				"增加失败！不允许操作他人的数据！");
		}

		// 从查询结果中获取尝试购买的原数量
		Integer oldNum = result.getNum();
		// 将数量更新为原数量+1的结果
		updateNum(cid, oldNum + 1, username, new Date());
	}

	@Override
	public void reduceNum(Integer cid, Integer uid, String username)
			throws CartNotFoundException, AccessDeniedException, UpdateException {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if (result == null) {
			throw new CartNotFoundException(
				"减少商品数量失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if (result.getUid() != uid) {
			throw new AccessDeniedException(
				"减少商品数量失败！不允许操作他人的数据！");
		}
		
		// 获取当前商品数量
		Integer num = result.getNum();
		// 判断当前商品数量是否已经只有1个
		if (num <= 1) {
			// 规则1：商口数量只有1个时，再减少，不执行任何操作
			return;
			// 规则2：商口数量只有1个时，再减少，则删除该数据
			// deleteByCid(cid);
			// return;
		}
		
		// 当前商品的数量大于1，则减少数量
		updateNum(cid, num - 1, username, new Date());
	}

	@Override
	public void delete(Integer cid, Integer uid) throws CartNotFoundException, AccessDeniedException, DeleteException {
		// 根据参数cid查询数据
		Cart result = findByCid(cid);
		// 判断查询结果是否为null：CartNotFoundException
		if (result == null) {
			throw new CartNotFoundException(
				"删除失败！尝试访问的购物车数据不存在！");
		}

		// 判断查询结果中的uid与参数uid是否不同：AccessDeniedException
		if (result.getUid() != uid) {
			throw new AccessDeniedException(
				"删除失败！不允许操作他人的数据！");
		}
		
		// 执行删除
		deleteByCid(cid);
	}
	
	@Override
	public List<CartVO> getByCids(Integer[] cids, Integer uid) {
		// 检查参数cids
		if (cids == null) {
			return new ArrayList<>();
		}
		
		// 执行查询
		List<CartVO> result = findByCids(cids);
		
		// 遍历查询结果，去除归属错误的数据
		Iterator<CartVO> it = result.iterator();
		while (it.hasNext()) {
			CartVO item = it.next();
			if (item.getUid() != uid) {
				it.remove();
			}
		}
		
		// 返回
		return result;
	}

	@Override
	public void delete(Integer[] cids,Integer uid) {
		// 判断即将删除的数据是否存在，及数据归属是否正确
		// 可以调用自身的：List<CartVO> getByCids(Integer[] cids, Integer uid)，得到cid有效，且归属正确的购物车数据
		List<CartVO> carts = getByCids(cids, uid);

		//判断以上查询结果的长度是否有效
		if(carts.size() == 0){
			throw new CartNotFoundException("删除购物车数据失败！尝试访问的数据不存在！");
		}

		// 基于以上得到的List<CartVO>得到允许执行删除的cid的数组
		Integer[] ids = new Integer[carts.size()];
		for (int i = 0; i < carts.size(); i++) {
			ids[i] = carts.get(i).getCid();
		}

		// 执行删除
		deleteByCids(ids);
	}

	/**
	 * 插入购物车数据
	 * @param cart 购物车数据
	 * @throws InsertException 插入数据异常
	 */
	private void insert(Cart cart) throws InsertException {
		Integer rows = cartMapper.insert(cart);
		if (rows != 1) {
			throw new InsertException(
				"将商品添加到购物车失败！插入数据时发生未知错误！");
		}
	}

	/**
	 * 根据若干个购物车数据id删除数据
	 * @param cids 若干个购物车数据id
	 * @throws DeleteException 删除数据异常
	 */
	private void deleteByCids(Integer[] cids) {
		Integer rows = cartMapper.deleteByCids(cids);
		if (rows < 1) {
			throw new DeleteException(
					"清除购物车数据失败！删除数据时发生未知错误！");
		}
	}
	
	/**
	 * 根据购物车数据id删除数据
	 * @param cid 购物车数据id
	 * @throws DeleteException 删除数据异常
	 */
	private void deleteByCid(Integer cid) throws DeleteException {
		Integer rows = cartMapper.deleteByCid(cid);
		if (rows != 1) {
			throw new DeleteException(
				"删除购物车数据失败！删除数据时发生未知错误！");
		}
	}

	/**
	 * 修改商品在购物车中的数量
	 * @param cid 购物车数据的id
	 * @param num 新的数量
	 * @param username 操作执行人
	 * @param modifiedTime 操作执行时间
	 * @throws UpdateException 更新数据异常
	 */
	private void updateNum(Integer cid, Integer num,
		String username, Date modifiedTime) {
		Integer rows = cartMapper.updateNum(cid, num, username, modifiedTime);
		if (rows != 1) {
			throw new UpdateException(
				"调整商品数量失败！修改数据时发生未知错误！");
		}
	}
	
	/**
	 * 根据购物车数据id查询购物车数据详情
	 * @param cid 购物车数据id
	 * @return 匹配的购物车数据详情，如果没有匹配的数据，则返回null
	 */
	private Cart findByCid(Integer cid) {
		return cartMapper.findByCid(cid);
	}

	/**
	 * 根据用户id和商品id查询购物车数据
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	private Cart findByUidAndPid(Integer uid, Integer pid) {
		return cartMapper.findByUidAndPid(uid, pid);
	}
	
	/**
	 * 根据用户id查询该用户的购物车列表
	 * @param uid 用户id
	 * @return 该用户的购物车列表，如果该用户购物车为空，则返回空集合
	 */
	private List<CartVO> findByUid(Integer uid) {
		return cartMapper.findByUid(uid);
	}

	/**
	 * 根据购物车数据的若干个id值获取匹配的购物车数据的详情
	 * @param cids 若干个购物车数据的id
	 * @return 匹配的购物车数据的详情
	 */
	private List<CartVO> findByCids(Integer[] cids) {
		return cartMapper.findByCids(cids);
	}

	
}
