package com.example.demo.xm.controller;

import com.example.demo.xm.entity.User;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

//@Controller  注册Controller对象
@RestController // @Controller与 ResponseBody合二为一注解
@RequestMapping(path = "/user") // 地址值
public class UserController {

	/**
	 * 获取user信息 接受无参请求
	 *
	 * @return user对象
	 */
	// @ResponseBody 如果不加@RestController注解就加这个注解，同样能让方法返回json格式数据对象
	@RequestMapping("/getUser")
	public User getUser() {
		User user = new User();
		user.setName("黄大胖子");
		user.setAge(18);
		user.setCurrentTime(new Date(System.currentTimeMillis()));
		/**
		 * 我们只需要把对象返回去，
		 * 我们在前面加了注解，
		 * Spring会帮我自动把对象转换成json数据.
		 * 他的底层调用了jackson，为我们转换
		 */
		return user;
	}

	/**
	 *
	 * 接收name参数
	 *
	 * @return user对象
	 */
	@RequestMapping("/setUserName")
	public User setUserName(@RequestParam(value = "name", defaultValue = "黄大胖子") String name) {
		User user = new User();
		user.setName(name);
		user.setAge(18);
		user.setCurrentTime(new Date(System.currentTimeMillis()));
		return user;
	}

	/**
	 * 接收请求地址值的参数name参数
	 *
	 * @return user对象
	 */
	@RequestMapping("/setUserName/{name}")
	public User setUserNamePath(@PathVariable("name") String name) {
		User user = new User();
		user.setName(name);
		user.setAge(18);
		user.setCurrentTime(new Date(System.currentTimeMillis()));
		return user;
	}

	/**
	 * 接收Json的参数name参数
	 *
	 * @return user对象
	 */
	@RequestMapping("/setUser")
	public User setUser(@RequestBody User user) {
		return user;
	}

}

