package com.example.demo.xm.controller;

import com.example.demo.xm.entity.SentenceSimilarityEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

//@Controller  注册Controller对象
// @Controller与 ResponseBody合二为一注解


@RestController
@RequestMapping(path = "/api") // 地址值
public class MorphoSimilarityController {

	/**
	 * 获取user信息 接受无参请求
	 *
	 * @return user对象
	 */
	// @ResponseBody 如果不加@RestController注解就加这个注解，同样能让方法返回json格式数据对象
	@RequestMapping(value = "/getmorphosimilarity")

	public SentenceSimilarityEntity getSimilarity(@RequestParam(value="s1",required = false,defaultValue = "1") String s1, @RequestParam(value="s2",required = false,defaultValue = "1") String s2){

	/*public  MorphoSimilarityEntity getSimilarity(String s1,String s2) {*/
		com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity morphoSimilarity = com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity.getInstance();
		SentenceSimilarityEntity user = new SentenceSimilarityEntity();
		user.setCurrentTime(new Date(System.currentTimeMillis()));
		/*String s1 = "作业系统和信息系统";
		String s2 = "物流系统由“物流作业系统”和支持物流信息流动的“物流信息系统”两大部分组成。";*/

		double sim = morphoSimilarity.getSimilarity(s1, s2);
		user.setSimilarty(sim);
		user.setText1(s1);
		user.setText2(s2);
		System.out.println(sim + ":" + s1 + " , " + s2);
		return user;
	}


}

