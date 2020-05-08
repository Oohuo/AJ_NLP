package com.example.demo.xm.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.xm.entity.SentenceSimilarityEntity;
import com.example.demo.xm.flowProcessing.HttpRequest;
import com.example.demo.xm.flowProcessing.answerLength;
import com.example.demo.xm.flowProcessing.scoreProcessing;
import com.example.demo.xm.sensitivewdfilter.WordFilter;
import com.example.demo.xm.similarity.sentence.editdistance.GregorEditDistanceSimilarity;
import com.example.demo.xm.similarity.sentence.editdistance.SuperString;
import com.example.demo.xm.similarity.sentence.morphology.SemanticSimilarity;
import com.example.demo.xm.similarity.text.CosineSimilarity;
import com.example.demo.xm.similarity.text.TextSimilarity;
import com.example.demo.xm.similarity.word.hownet.concept.ConceptSimilarity;
import com.example.demo.xm.textrank.TextRankKeyword;
import com.example.demo.xm.textrank.TextRankSummary;
import com.sun.org.apache.bcel.internal.generic.I2F;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

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
    public SentenceSimilarityEntity getSimilarity(@RequestParam(value = "reply", required = false, defaultValue = "1") String reply,
                                                  @RequestParam(value = "answer", required = false, defaultValue = "1") String answer,
                                                  @RequestParam(value = "keyWords", required = false) String keywords
                                                  ) {

        int replylenth = answerLength.length(reply);

        int answerlenth = answerLength.length(answer);

        int keywordlenth = answerLength.length(keywords);

        System.out.println(replylenth + "*" + answerlenth + "*" + keywordlenth);
        //获取答案关键词，按空格分开
        String[] keyword = keywords.split(" ");
        /*for (int i = 0; i < keyword.length; i++) {
            System.out.println(keyword[i]);

        }*/
        String s1 = "s1=";
        String s2 = "s2=";

        double finalScore = 0d;
        System.out.println(Arrays.toString(keyword)+"答案关键词");
        if (replylenth <= 50) {


            String urlSegment = HttpRequest.sendPost("http://127.0.0.1:8080/api/getsegment", s1 + reply);

            JSONObject jsonObject2 = JSONObject.parseObject(urlSegment);
            JSONArray segments = jsonObject2.getJSONArray("list");
            String[] replykeywords = new String[segments.size()];
            for (int i = 0; i < segments.size(); i++) {
                String name = (String) segments.getJSONObject(i).get("name");
                replykeywords[i] = name;
                //System.out.println(name);
            }
            System.out.println(Arrays.toString(replykeywords)+"回答关键词");

            double[][] keywordresult = new double[replykeywords.length][keyword.length];
            for (int r = 0; r < replykeywords.length; r++) {
                for (int a = 0; a < keyword.length; a++) {
                    //System.out.println(replykeywords[r]);
                    //System.out.println(keyword[a]);
                    double similarity = ConceptSimilarity.getInstance().getSimilarity(replykeywords[r], keyword[a]);
                    //System.out.println(similarity);
                    keywordresult[r][a] = similarity;
                }
            }

            System.out.println(Arrays.deepToString(keywordresult));
            double keywordsult = 0d;
            double[] keywordresult1 = new double[keyword.length * replykeywords.length];
            int index = 0;
            for (int i = 0; i < keywordresult.length; ++i) {
                for (int j = 0; j < keywordresult[i].length; j++) {
                    keywordresult1[index++] = keywordresult[i][j];
                }
            }
            Arrays.sort(keywordresult1);
            System.out.println(Arrays.toString(keywordresult1) + "-");

            for(int d = keyword.length * replykeywords.length-1; d >=keyword.length * (replykeywords.length-1);d--) {
                keywordsult += keywordresult1[d];
            }
            String re = WordFilter.doFilter(reply);
            boolean TrueOrNo = WordFilter.isContains(reply);

            System.out.println(keyword.length+"答案关键词数");
            System.out.println(keywordsult+"单句关键词相似总分");
            Double singleSentenceSimilarity = keywordsult / (double) keyword.length;

            System.out.println(singleSentenceSimilarity+"关键词最终分数");

            TextSimilarity cosSimilarity = new CosineSimilarity();
            double cos = cosSimilarity.getSimilarity(reply, answer);

            System.out.println(cos+"余弦相似度");
            SemanticSimilarity semanticSimilarity = SemanticSimilarity.getInstance();
            double semantic = semanticSimilarity.getSimilarity(reply, answer);
            System.out.println(semantic+"semantic相似度");
            /*GregorEditDistanceSimilarity ed = new GregorEditDistanceSimilarity();
            double EditDistance = ed.getEditDistance(SuperString.createCharSuperString(reply),
                    SuperString.createCharSuperString(answer));
            System.out.println(EditDistance+"编辑距离");

            Double eds =0d;
            if(EditDistance<10){
                eds=1.0;
            } else if (10<EditDistance&&EditDistance<20){
                eds=0.9;
            }
            else if (20<EditDistance&&EditDistance<30){
                eds=0.8;
            }
            else if (30<EditDistance&&EditDistance<40){
                eds=0.7;
            }
            else if (40<EditDistance&&EditDistance<50){
                eds=0.6;
            }
            else if (50<EditDistance&&EditDistance<60){
                eds=0.5;
            }
            else if (60<EditDistance&&EditDistance<70){
                eds=0.4;
            }
            else if (70<EditDistance&&EditDistance<80){
                eds=0.3;
            }
            else if (80<EditDistance&&EditDistance<90){
                eds=0.2;
            }
            else if (90<EditDistance&&EditDistance<100){
                eds=0.1;
            }else {eds=0.05;

            }*/

            if(TrueOrNo){
                finalScore = 0;

            }else {
                finalScore = 0.8*singleSentenceSimilarity+0.1*cos+0.1*semantic;
            }

            System.out.println(finalScore);





        }
        else {
            List<String> keySentence = TextRankSummary.getTopSentenceList(reply, 4);
            String keysentences = String.valueOf(keySentence);
            //System.out.println(keysentences);
            List<Double> sentencesResult = new ArrayList<> ();
            List<Double> cosResult = new ArrayList<> ();
            List<Double> semanticResult = new ArrayList<> ();
            List<Double> EDResult = new ArrayList<> ();
            for(int i = 0; i < keySentence.size();i++){

                boolean TrueOrNo = WordFilter.isContains(keySentence.get(i));
                String urlSegment = HttpRequest.sendPost("http://127.0.0.1:8080/api/getsegment", s1 + keySentence.get(i));
                //System.out.println(urlSegment);
                JSONObject jsonObject2 = JSONObject.parseObject(urlSegment);
                JSONArray segments = jsonObject2.getJSONArray("list");
                String[] replykeywords = new String[segments.size()];
                for (int i2 = 0; i2 < segments.size(); i2++) {
                    String name = (String) segments.getJSONObject(i2).get("name");
                    replykeywords[i2] = name;
                    //System.out.println(name);
                }
                System.out.println(Arrays.toString(replykeywords)+"回答的关键词");
                //关键词对比结果数组
                double[][] keywordresult = new double[replykeywords.length][keyword.length];

                for (int r = 0; r < replykeywords.length; r++) {
                    for (int a = 0; a < keyword.length; a++) {
                        //System.out.println(replykeywords[r]);
                        //System.out.println(keyword[a]);
                        double similarity = ConceptSimilarity.getInstance().getSimilarity(replykeywords[r], keyword[a]);

                        //System.out.println(similarity);
                        keywordresult[r][a] = similarity;
                    }
                }

               // System.out.println(Arrays.deepToString(keywordresult));
                double keywordsult = 0d;
                double[] keywordresult1 = new double[keyword.length * replykeywords.length];
                int index = 0;
                for (int i1 = 0; i1 < keywordresult.length; ++i1) {
                    for (int j = 0; j < keywordresult[i1].length; j++) {
                          keywordresult1[index++] = keywordresult[i1][j];
                    }
                }
                Arrays.sort(keywordresult1);
                System.out.println(Arrays.toString(keywordresult1) + "-");

                System.out.println(keyword.length * replykeywords.length);
                System.out.println(keyword.length * (replykeywords.length-1));
                for(int d = keyword.length * replykeywords.length-1; d >=keyword.length * (replykeywords.length-1);d--) {

                    keywordsult += keywordresult1[d];
                }
                System.out.println(keyword.length+"答案关键词数");
                System.out.println(keywordsult+"关键词总分");
                Double singleSentenceSimilarity = keywordsult / (double) keyword.length;
                System.out.println(singleSentenceSimilarity+"关键词最终分数");



                TextSimilarity cosSimilarity = new CosineSimilarity();
                double cos = cosSimilarity.getSimilarity(reply, answer);

                System.out.println(cos+"余弦相似度");
                SemanticSimilarity semanticSimilarity = SemanticSimilarity.getInstance();
                double semantic = semanticSimilarity.getSimilarity(reply, answer);
                System.out.println(semantic+"semantic相似度");
               /* GregorEditDistanceSimilarity ed = new GregorEditDistanceSimilarity();
                double EditDistance = ed.getEditDistance(SuperString.createCharSuperString(reply),
                        SuperString.createCharSuperString(answer));
                System.out.println(EditDistance+"编辑距离");

                Double eds =0d;
                if(EditDistance<10){
                    eds=1.0;
                } else if (10<EditDistance&&EditDistance<20){
                    eds=0.9;
                }
                else if (20<EditDistance&&EditDistance<30){
                    eds=0.8;
                }
                else if (30<EditDistance&&EditDistance<40){
                    eds=0.7;
                }
                else if (40<EditDistance&&EditDistance<50){
                    eds=0.6;
                }
                else if (50<EditDistance&&EditDistance<60){
                    eds=0.5;
                }
                else if (60<EditDistance&&EditDistance<70){
                    eds=0.4;
                }
                else if (70<EditDistance&&EditDistance<80){
                    eds=0.3;
                }
                else if (80<EditDistance&&EditDistance<90){
                    eds=0.2;
                }
                else if (90<EditDistance&&EditDistance<100){
                    eds=0.1;
                }else {eds=0.05;

                }*/

                if(TrueOrNo){
                    finalScore = 0;

                }else {
                   // finalScore = 0.75*singleSentenceSimilarity+0.1*cos+0.1*semantic+0.05*eds;;
                    finalScore = 0.75*singleSentenceSimilarity+0.125*cos+0.125*semantic;;
                }




                cosResult.add(cos);
                semanticResult.add(semantic);
                //EDResult.add(eds);
                sentencesResult.add(singleSentenceSimilarity);
            }

            System.out.println(cosResult+"分句子余弦得分");
            System.out.println(semanticResult+"分句子semantic得分");
            System.out.println(EDResult+"分句子ED得分");
            System.out.println(sentencesResult+"分橘子得分");
            System.out.println(finalScore+"最终");
        }


            SentenceSimilarityEntity user = new SentenceSimilarityEntity();
            user.setCurrentTime(new Date(System.currentTimeMillis()));
           /* Double grate;
            String negativeProcessed = WordFilter.doFilter(reply);
            System.out.println(negativeProcessed);
            // 获取到key为similarty的值


            //System.out.println(text1);
            //System.out.println(negativeProcessed);
            TextSimilarity similarity = new CosineSimilarity();
            double consineSimilartyValue = similarity.getSimilarity(negativeProcessed, answer);
            //System.out.println(consineSimilartyValue);
            SemanticSimilarity semanticSimilarity = SemanticSimilarity.getInstance();
            double semanticSimilartyValue = semanticSimilarity.getSimilarity(negativeProcessed, answer);

            com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity morphoSimilarity = com.example.demo.xm.similarity.sentence.morphology.MorphoSimilarity.getInstance();

            double morphoSimilartyValue = morphoSimilarity.getSimilarity(negativeProcessed, answer);

            scoreProcessing scoreProcessing = new scoreProcessing();
            grate = scoreProcessing.returnScore(consineSimilartyValue, semanticSimilartyValue*//*, EditDistanceSimilartyValue*//*, morphoSimilartyValue);
*/
           // System.out.println("\n" + "本题分数：" + grate);

            user.setText1(reply);
            user.setText2(answer);


            //System.out.println("四舍五入取整："+10*Math.round(finalScore));
            user.setScore(10*Math.round(finalScore));
            return user;
        }


    }



