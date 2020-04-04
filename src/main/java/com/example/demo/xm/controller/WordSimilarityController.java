package com.example.demo.xm.controller;

import com.example.demo.xm.entity.SentenceSimilarityEntity;
import com.example.demo.xm.similarity.word.clin.CilinSimilarity;
import com.example.demo.xm.similarity.word.hownet.concept.ConceptSimilarity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName CilinSimilarityController
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-08 1:43
 * @Version 1.0
 **/
@RestController
@RequestMapping(path = "/api")
public class WordSimilarityController {
    @RequestMapping(value = "/getwordsimilarity")
    public SentenceSimilarityEntity getSimilarity(@RequestParam(value="s1",required = false,defaultValue = "1") String s1, @RequestParam(value="s2",required = false,defaultValue = "1") String s2){


        SentenceSimilarityEntity user = new SentenceSimilarityEntity();
        user.setCurrentTime(new Date(System.currentTimeMillis()));
		/*String s1 = "作业系统和信息系统";
		String s2 = "物流系统由“物流作业系统”和支持物流信息流动的“物流信息系统”两大部分组成。";*/

        double sim = ConceptSimilarity.getInstance().getSimilarity(s1, s2);
        user.setSimilarty(sim);
        user.setText1(s1);
        user.setText2(s2);
        System.out.println(sim + ":" + s1 + " , " + s2);
        return user;


    }


}
