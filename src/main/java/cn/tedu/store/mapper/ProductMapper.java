package cn.tedu.store.mapper;

import cn.tedu.store.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {

	/**
	 * 查询热销的前4名的商品列表
	 * @return 热销的前4名的商品列表
	 */
	List<Product> findHotList();
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	Product findById(Integer id);

	/**
	 * 更新商品的库存
	 * @param pid
	 * @param num
	 * @return
	 */
	Integer updateNum(
			@Param("pid") Integer pid,
			@Param("num") Integer num);
	
}





