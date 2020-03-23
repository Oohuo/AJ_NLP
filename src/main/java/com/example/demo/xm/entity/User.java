package com.example.demo.xm.entity;

import java.util.Date;

public class User {
	// 用户姓名
	private String name;
	// 年龄
	private Integer age;
	// 登录时间
	private Date currentTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}

