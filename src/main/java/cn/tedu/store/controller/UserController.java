package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileStateException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理用户相关请求的控制器
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		userService.reg(user);
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		// 调用业务层对象的“登录”方法，获取返回结果
		User data = userService.login(username, password);
		// 向Session中存入用户id和用户名
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		// 返回
		return new JsonResult<>(SUCCESS, data);
	}

	@RequestMapping("change_password")
	public JsonResult<Void> changePassword(
		@RequestParam("old_password") String oldPassword,
		@RequestParam("new_password") String newPassword, 
		HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 从session中获取username
		String username = getUsernameFromSession(session);
		// 调用service对象执行修改密码
		userService.changePassword(uid, oldPassword, newPassword, username);
		// 响应成功
		return new JsonResult<>(SUCCESS);
	}

	@RequestMapping("change_info")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 执行修改
		userService.changeInfo(uid, username, user);
		// 返回成功
		return new JsonResult<>(SUCCESS);
	}

	@GetMapping("get_by_uid")
	public JsonResult<User> getByUid(HttpSession session) {
		// 执行获取数据
		Integer uid = getUidFromSession(session);
		User data = userService.getByUid(uid);
		// 返回成功与数据
		return new JsonResult<>(SUCCESS, data);
	}
	
	/**
	 * 允许上传的头像文件的最大大小
	 */
	public static final long AVATAR_MAX_SIZE = 2 * 1024 * 1024;
	/**
	 * 允许上传的头像文件的类型列表
	 */
	public static final List<String> AVATAR_CONTENT_TYPES = new ArrayList<String>();
	
	static {
		AVATAR_CONTENT_TYPES.add("image/jpeg");
		AVATAR_CONTENT_TYPES.add("image/png");
	}
	
	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(
		@RequestParam("file") MultipartFile file, 
		HttpServletRequest request) {
		// 检查文件是否为空
		if (file.isEmpty()) {
			throw new FileEmptyException(
				"上传头像失败！请选择有效的图片文件！");
		}
		
		// 检查文件大小是否超出限制
		if (file.getSize() > AVATAR_MAX_SIZE) {
			throw new FileSizeException(
				"上传头像失败！不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的图片文件！");
		}
		
		// 检查文件类型是否超出限制
		if (!AVATAR_CONTENT_TYPES.contains(file.getContentType())) {
			throw new FileSizeException(
				"上传头像失败！选择的文件类型超出了限制！\r\r允许使用的文件类型有：" + AVATAR_CONTENT_TYPES);
		}
		
		// 确定文件夹
		String parentPath = request.getServletContext().getRealPath("upload");
		File parent = new File(parentPath);
		if (!parent.exists()) {
			parent.mkdirs();
		}
		
		// 确定文件名
		String filename = UUID.randomUUID().toString();
		String originalFilename = file.getOriginalFilename();
		int beginIndex = originalFilename.lastIndexOf(".");
		String suffix = originalFilename.substring(beginIndex);
		String child = filename + suffix;
		
		// 保存用户上传的文件
		File dest = new File(parent, child);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileStateException(
				"上传文件失败！文件状态有误，请重新尝试！");
		} catch (IOException e) {
			throw new FileUploadIOException(
				"上传文件失败！发生读写错误，请重新尝试！");
		}
		
		// 将文件的路径记录到数据库
		String avatarPath = "/upload/" + child;
		Integer uid = getUidFromSession(request.getSession());
		String username = getUsernameFromSession(request.getSession());
		userService.changeAvatar(uid, avatarPath, username);
		
		// 响应结果
		return new JsonResult<>(SUCCESS, avatarPath);
	}
	
}









