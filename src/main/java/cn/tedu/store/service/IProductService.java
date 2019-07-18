package cn.tedu.store.service;

import cn.tedu.store.entity.Product;

import java.util.List;

/**
 * 处理商品数据的业务层接口
 */
public interface IProductService {

	/**
	 * 查询热销的前4名的商品列表
	 * @return 热销的前4名的商品列表
	 */
	List<Product> getHotList();
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	Product getById(Integer id);

	/**
	 * 减少指定商品的数量
	 * @param pid
	 * @param amount
	 */
	// amount：减少的数量
	void reduceNum(Integer pid, Integer amount);

	/**
	 * 增加商品库存
	 * @param pid
	 * @param amount
	 */
	void addNum(Integer pid, Integer amount);
	
}




