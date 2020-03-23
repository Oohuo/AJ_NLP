package com.example.demo.xm.controller;

import com.example.demo.xm.entity.SegmentEntity;
import com.example.demo.xm.tokenizer.Tokenizer;
import com.example.demo.xm.tokenizer.Word;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SegmentController
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-06 21:45
 * @Version 1.0
 **/
//@Controller  注册Controller对象
@RestController // @Controller与 ResponseBody合二为一注解
@RequestMapping(path = "/api") // 地址值

public class SegmentController {


    /**
     * 获取user信息 接受无参请求
     *
     * @return user对象
     */
    // @ResponseBody 如果不加@RestController注解就加这个注解，同样能让方法返回json格式数据对象
    @RequestMapping(value = "/getsegment" )
    public SegmentEntity getsegment(@RequestParam(value="s1",required = false,defaultValue = "1") String s1) {

        /*public  MorphoSimilarityEntity getSimilarity(String s1,String s2) {*/
        List<Word> lists = Tokenizer.segment(s1);
        SegmentEntity user = new SegmentEntity();
        user.setCurrentTime(new Date(System.currentTimeMillis()));
      /*String s1 = "作业系统和信息系统";
      String s2 = "物流系统由“物流作业系统”和支持物流信息流动的“物流信息系统”两大部分组成。";*/


        user.setList(lists);
        user.setSentence(s1);

        System.out.println(lists + ":" + s1 );
        return user;
    }


}
