package cn.tedu.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/**
 * 处理用户相关数据的持久层接口
 */
public interface UserMapper {

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer addnew(User user);
	
	/**
	 * 更新密码
	 * @param uid 用户id
	 * @param password 新密码
	 * @param modifiedUser 最后修改执行人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updatePassword(
		@Param("uid") Integer uid, 
		@Param("password") String password, 
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 更新头像
	 * @param uid 用户id
	 * @param avatar 新头像路径
	 * @param modifiedUser 最后修改执行人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateAvatar(
		@Param("uid") Integer uid, 
		@Param("avatar") String avatar, 
		@Param("modifiedUser") String modifiedUser, 
		@Param("modifiedTime") Date modifiedTime);
	
	/**
	 * 更新个人资料
	 * @param user 用户的个人资料
	 * @return 受影响的行数
	 */
	Integer updateInfo(User user);

	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUsername(String username);

	/**
	 * 根据用户id查询用户数据
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUid(Integer uid);
	
}





