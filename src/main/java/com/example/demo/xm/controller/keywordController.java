package com.example.demo.xm.controller;

import com.example.demo.xm.entity.SegmentEntity;
import com.example.demo.xm.textrank.TextRankKeyword;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName keywordController
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-16 18:59
 * @Version 1.0
 **/
//@Controller  注册Controller对象
@RestController // @Controller与 ResponseBody合二为一注解
@RequestMapping(path = "/api") // 地址值
public class keywordController {
    @RequestMapping(value = "/getkeyword")
    public SegmentEntity segmentEntity(@RequestParam(value="s1",required = false,defaultValue = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。") String s1) {


        String result = new TextRankKeyword().getKeyword("", s1);
        String[] list= result.split("#");

        List<String> strsToList1= Arrays.asList(list);
        System.out.println(result);
        SegmentEntity user = new SegmentEntity();
        user.setCurrentTime(new Date(System.currentTimeMillis()));
      /*String s1 = "作业系统和信息系统";
      String s2 = "物流系统由“物流作业系统”和支持物流信息流动的“物流信息系统”两大部分组成。";*/


        user.setList(strsToList1);
        user.setSentence(s1);
        return user;



    }
}
