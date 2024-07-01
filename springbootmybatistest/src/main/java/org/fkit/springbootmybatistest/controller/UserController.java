package org.fkit.springbootmybatistest.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fkit.springbootmybatistest.entity.User;
import org.fkit.springbootmybatistest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户控制器，管理用户登录注册，查看个人信息等操作
 * @sk
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	//主页
	@RequestMapping("/toIndex")
	public String show(){
		return "index1";
	}

	//用户登录，根据用户身份返回相对应的页面
	@RequestMapping("/loginUser")
	public String login(User user,HttpServletRequest request,Model model){
		String name = user.getName();
		String password = user.getPassword();
		User u1 = userService.login(name,password);
		if (u1==null){
			String msg = "账号或密码错误！";
			model.addAttribute("msg",msg);
			return "index1";
		} else{
			request.getSession().setAttribute("user",u1);
			if (userService.findRole(name).equals("admin")) {
				return "dashboard";
			} else {
				return "dashboard-client";
			}
		}
	}

	//预注册
	@RequestMapping("/toRegister")
	public String toRegister(){
		return "/register";
	}

	//注册
	@RequestMapping("/register")
	public String register(User user,Model model){
		String name = user.getName();
		User u = userService.selByName(name);
		if (u!=null){
			String msg = "用户名已存在，请直接登录！";
			model.addAttribute("msg",msg);
			return "index1";
		}else {
			int su = userService.register(user);
//			if (su == 0) {
//				System.out.println("---------");
//			}
			String msg = "注册成功，请登录！";
			model.addAttribute("msg",msg);
			return "index1";
		}
	}

	//登陆成功，客户界面
	@RequestMapping("/welcome")
	public String welcome(){
		return "dashboard-client";
	}

	//登出
	@RequestMapping("/outUser")
	public void outUser(HttpServletRequest request, HttpServletResponse response)throws IOException {
		request.getSession().removeAttribute("user");
		response.sendRedirect("/user/toIndex");
	}

	@RequestMapping("/index1")
	public String log(){
		return "index1";
	}

	//个人主页
	@RequestMapping("/myPage")
	public String MyPage(String name, Model model){
		User user = userService.selByName(name);
		model.addAttribute("user",user);
		return "myinfo";
	}

	//返回主页
	@RequestMapping("/back")
	public String Back(){
		return "dashboard-client";
	}

	//管理员查看用户信息
	@RequestMapping("/selUser")
	public String selUser(Model model,HttpServletRequest request){
		String name = request.getParameter("name");
		if (name != null){
			List<User> users = userService.selLikeName(name);
			model.addAttribute("users",users);
		}else {
			List<User> users = userService.selAll();
			model.addAttribute("users",users);
		}
		return "/usermanager/userinfo";
	}

	//删除用户
	@RequestMapping("/delUser")
	public String delUser(int id){
		userService.delUser(id);
		return "forward:/user/selUser";
	}

	//预更新
	@RequestMapping("toUpdate")
	public String toUpdate(int id,Model model){
		User user = userService.Sel(id);
		model.addAttribute("user",user);
		return "/usermanager/updateuser";
	}

	//更新用户信息
	@RequestMapping("/update")
	public String update(User user){
		userService.updateUser(user);
		return "forward:/user/selUser";
	}

	//预添加
	@RequestMapping("toAdd")
	public String toAdd(){
		return "/usermanager/adduser";
	}

	//添加用户
	@RequestMapping("/add")
	public String Add(User user){
		userService.addUser(user);
		return "forward:/user/selUser";
	}

}