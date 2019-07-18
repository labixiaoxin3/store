package cn.tedu.store.service;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.ex.*;

import java.util.List;

/**
 * 处理收货地址数据的业务层接口
 */
public interface IAddressService {
	
	/**
	 * 每个用户可以创建的收货地址数据的最大数量
	 */
	int MAX_COUNT = 20;

	/**
	 * 创建收货地址
	 * @param uid 用户id
	 * @param username 用户名
	 * @param address 收货地址数据
	 * @throws AddressCountLimitException 收货地址数量达到上限
	 * @throws InsertException 插入数据异常
	 */
	void create(Integer uid, String username, Address address) throws AddressCountLimitException, InsertException;
	
	/**
	 * 获取某用户的收货地址数据的列表
	 * @param uid 用户的id
	 * @return 用户的收货地址数据的列表
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * 设置默认收货地址
	 * @param aid 即将要设置为默认的收货地址的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 * @throws AddressNotFoundException 收货地址数据不存在
	 * @throws AccessDeniedException 数据归属错误
	 * @throws UpdateException 更新数据异常
	 */
	void setDefault(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, UpdateException;
	
	/**
	 * 删除收货地址数据
	 * @param aid 即将要删除的收货地址的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 * @throws AddressNotFoundException 收货地址数据不存在
	 * @throws AccessDeniedException 数据归属错误
	 * @throws DeleteException 删除数据异常
	 * @throws UpdateException 更新数据异常
	 */
	void delete(Integer aid, Integer uid, String username) throws AddressNotFoundException, AccessDeniedException, DeleteException, UpdateException;

	Address getByAid(Integer aid);
}
