package com.example.demo.xm.controller;

import com.example.demo.xm.entity.filterInformationEntity;
import com.example.demo.xm.sensitivewdfilter.WordFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName negativeWordFiltering
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-16 20:12
 * @Version 1.0
 **/
//@Controller  注册Controller对象
@RestController // @Controller与 ResponseBody合二为一注解
@RequestMapping(path = "/api") // 地址值
public class negativeWordFiltering {
    @RequestMapping(value = "/getNegativeWordFilteringInformation" )
    public filterInformationEntity filterInformationEntity(@RequestParam(value="s1",required = false,defaultValue = "我不得不告诉你这是真的") String s1) {
        String re = WordFilter.doFilter(s1);
        boolean TrueOrNo = WordFilter.isContains(s1);
        System.out.println(re);
        System.out.println(TrueOrNo);
        filterInformationEntity filterInformationEntity = new filterInformationEntity();
        filterInformationEntity.setCurrentTime(new Date(System.currentTimeMillis()));
        filterInformationEntity.setPresentence(s1);
        filterInformationEntity.setProcessedSentence(re);
        filterInformationEntity.setTrueOrNo(TrueOrNo);
        return filterInformationEntity;

    }

}
