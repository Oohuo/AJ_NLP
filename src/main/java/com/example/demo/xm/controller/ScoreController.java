package com.example.demo.xm.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.xm.entity.SentenceSimilarityEntity;
import com.example.demo.xm.flowProcessing.scoreProcessing;
import com.example.demo.xm.sensitivewdfilter.WordFilter;
import com.example.demo.xm.similarity.sentence.morphology.SemanticSimilarity;
import com.example.demo.xm.similarity.text.CosineSimilarity;
import com.example.demo.xm.similarity.text.TextSimilarity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @ClassName Score
 * @Description TODO
 * @Author ZH
 * @Date 2020-03-22 21:06
 * @Version 1.0
 **/


@RestController
@RequestMapping(path = "/api")
public class ScoreController {

    @RequestMapping(value = "/getScore")
    public SentenceSimilarityEntity getSimilarity(@RequestParam(value = "s1", required = false, defaultValue = "1") String s1, @RequestParam(value = "s2", required = false, defaultValue = "1") String s2) {
        SentenceSimilarityEntity user = new SentenceSimilarityEntity();
        user.setCurrentTime(new Date(System.currentTimeMillis()));
        Double grate;
        String negativeProcessed = WordFilter.doFilter(s1);
        System.out.println(negativeProcessed);
        // 获取到key为similarty的值


        //System.out.println(text1);
        //System.out.println(negativeProcessed);
        TextSimilarity similarity = new CosineSimilarity();
        double consineSimilartyValue = similarity.getSimilarity(negativeProcessed, s2);
        //System.out.println(consineSimilartyValue);
        SemanticSimilarity semanticSimilarity = SemanticSimilarity.getInstance();
        double semanticSimilartyValue = semanticSimilarity.getSimilarity(negativeProcessed, s2);

        //


        //
        com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity morphoSimilarity = com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity.getInstance();

        double morphoSimilartyValue = morphoSimilarity.getSimilarity(negativeProcessed, s2);

        scoreProcessing scoreProcessing = new scoreProcessing();
        grate=scoreProcessing.returnScore(consineSimilartyValue, semanticSimilartyValue/*, EditDistanceSimilartyValue*/, morphoSimilartyValue);

        System.out.println("\n"+"本题分数："+grate);

        user.setText1(s1);
        user.setText2(s2);
        user.setScore(grate);
        return user;
    }

}
