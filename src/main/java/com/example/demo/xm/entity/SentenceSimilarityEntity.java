package com.example.demo.xm.entity;

import java.util.Date;

public class SentenceSimilarityEntity {
	private double similarty;
	private String text1;
	private String text2;
	private double score;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	// 登录时间
	private Date currentTime;


	public double getSimilarty() {
		return similarty;
	}

	public void setSimilarty(double similarty) {
		this.similarty = similarty;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}

