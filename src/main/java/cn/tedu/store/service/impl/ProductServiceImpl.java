package cn.tedu.store.service.impl;

import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.service.ex.ProductNotFoundException;
import cn.tedu.store.service.ex.ProductOutOfStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 处理商品数据的业务层实现类
 */
@Service
public class ProductServiceImpl implements IProductService {
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> getHotList() {
		List<Product> list = findHotList();
		for (Product product : list) {
			product.setCategoryId(null);
			product.setItemType(null);
			product.setSellPoint(null);
			product.setNum(null);
			product.setStatus(null);
			product.setPriority(null);
			product.setCreatedUser(null);
			product.setCreatedTime(null);
			product.setModifiedUser(null);
			product.setModifiedTime(null);
		}
		return list;
	}

	@Override
	public Product getById(Integer id) {
		// 调用私有方法执行查询
		Product result = findById(id);
		// 判断查询结果是否为null：ProductNotFoundException
		if (result == null) {
			throw new ProductNotFoundException(
				"查询商品详情失败！尝试访问的数据不存在！");
		}

		// 将查询结果中的部分属性设置为null，例如隐藏属性和日志
		result.setPriority(null);
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifiedUser(null);
		result.setModifiedTime(null);
		// 返回结果
		return result;
	}

	@Override
	public void reduceNum(Integer pid, Integer amount) {
		// 通过参数pid查询商品数据
		Product result = findById(pid);
		// 判断查询结果是否为null：ProductNotFoundException
		 if(result == null){
		 	throw new ProductNotFoundException("更新商品库存失败！尝试访问的商品数量不存在！");
		 }
		// 判断查询结果中的num(当前库存)是否小于参数amount(将要购买或减少的库存量)：ProductOutOfStockException
		if(result.getNum() < amount){
			throw new ProductOutOfStockException("更新商品库存失败！当前商品库存已不足！");
		}
		// 执行减少库存
		updateNum(pid, result.getNum() - amount);
	}

	@Override
	public void addNum(Integer pid, Integer amount) {
		// 通过参数pid查询商品数据
		Product result = findById(pid);
		// 判断查询结果是否为null：ProductNotFoundException
		if (result == null) {
			throw new ProductNotFoundException(
					"更新商品库存失败！尝试访问的商品数量不存在！");
		}

		// 暂不考虑商品下架的问题

		// 执行增加库存
		updateNum(pid, result.getNum() + amount);
	}

	/**
	 * 查询热销的前4名的商品列表
	 * @return 热销的前4名的商品列表
	 */
	private List<Product> findHotList() {
		return productMapper.findHotList();
	}
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	private Product findById(Integer id) {
		return productMapper.findById(id);
	}

	/**
	 * 更新商品的库存
	 * @param pid
	 * @param num
	 * @return
	 */
	private void updateNum(Integer pid, Integer num){
		Integer rows = productMapper.updateNum(pid, num);
		if(rows != 1){
			throw new ProductNotFoundException("更新商品数量失败！更新商品数量出现未知异常!");
		}
	}
	
}
