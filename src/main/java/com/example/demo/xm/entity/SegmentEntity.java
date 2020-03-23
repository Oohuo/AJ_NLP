package com.example.demo.xm.entity;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SegmentEntity
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-06 21:41
 * @Version 1.0
 **/
public class SegmentEntity {
    private String sentence="什么是球体和服装？一个伟大的国家，中国，电脑病毒会传染给人吗？";
    private List list;
    private Date currentTime;
    private String strings;

    public String getStrings() {
        return strings;
    }

    public void setStrings(String strings) {
        this.strings = strings;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
