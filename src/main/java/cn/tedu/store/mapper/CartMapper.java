package cn.tedu.store.mapper;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {

	/**
	 * 插入购物车数据
	 * @param cart 购物车数据
	 * @return 受影响的行数
	 */
	Integer insert(Cart cart);

	/**
	 * 根据购物车数据id删除数据
	 * @param cid 购物车数据id
	 * @return 受影响的行数
	 */
	Integer deleteByCid(Integer cid);

	/**
	 * 根据若干个购物车数据id删除数据
	 * @param cids
	 * @return
	 */
	 Integer deleteByCids(Integer[] cids);
	
	/**
	 * 修改商品在购物车中的数量
	 * @param cid 购物车数据的id
	 * @param num 新的数量
	 * @param username 操作执行人
	 * @param modifiedTime 操作执行时间
	 * @return 受影响的行数
	 */
	Integer updateNum(
		@Param("cid") Integer cid, 
		@Param("num") Integer num,
		@Param("username") String username, 
		@Param("modifiedTime") Date modifiedTime);

	/**
	 * 根据购物车数据id查询购物车数据详情
	 * @param cid 购物车数据id
	 * @return 匹配的购物车数据详情，如果没有匹配的数据，则返回null
	 */
	Cart findByCid(Integer cid);
	
	/**
	 * 根据用户id和商品id查询购物车数据
	 * @param uid 用户id
	 * @param pid 商品id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	Cart findByUidAndPid(
		@Param("uid") Integer uid, 
		@Param("pid") Integer pid);
	
	/**
	 * 根据用户id查询该用户的购物车列表
	 * @param uid 用户id
	 * @return 该用户的购物车列表，如果该用户购物车为空，则返回空集合
	 */
	List<CartVO> findByUid(Integer uid);
	
	/**
	 * 根据购物车数据的若干个id值获取匹配的购物车数据的详情
	 * @param cids 若干个购物车数据的id
	 * @return 匹配的购物车数据的详情
	 */
	List<CartVO> findByCids(Integer[] cids);


	
}
