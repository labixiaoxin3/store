package cn.tedu.store.service;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {

	/**
	 * 用户注册
	 * @param user 用户数据
	 * @throws UsernameDuplicateException 用户名冲突异常，例如尝试注册已经被占用的用户名
	 * @throws InsertException 插入数据异常
	 */
	void reg(User user) 
		throws UsernameDuplicateException, InsertException;
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户信息
	 * @throws UserNotFoundException 用户名错误，或用户数据被标记为“已删除”
	 * @throws PasswordNotMatchException 密码验证失败
	 */
	User login(String username, String password) 
		throws UserNotFoundException, PasswordNotMatchException;
	
	/**
	 * 修改密码
	 * @param uid 当前登录的用户id
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param modifiedUser 当前登录的用户名
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 密码错误
	 * @throws UpdateException 更新数据异常
	 */
	void changePassword(Integer uid, String oldPassword, String newPassword, String modifiedUser) throws UserNotFoundException, PasswordNotMatchException, UpdateException;
	
	/**
	 * 修改个人资料
	 * @param uid 用户id
	 * @param username 用户名
	 * @param user 用户的新资料
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeInfo(Integer uid, String username, 
		User user) throws UserNotFoundException, UpdateException;

	/**
	 * 根据用户id获取用户信息详情
	 * @param uid 用户的id
	 * @return 匹配的用户信息详情
	 * @throws UserNotFoundException 用户数据不存在
	 */
	User getByUid(Integer uid) throws UserNotFoundException;
	
	/**
	 * 修改头像
	 * @param uid 用户id
	 * @param avatar 新头像路径
	 * @param modifiedUser 用户名
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeAvatar(Integer uid, String avatar, String modifiedUser) throws UserNotFoundException, UpdateException;
	
}




