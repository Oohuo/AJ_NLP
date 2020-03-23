package com.example.demo.xm.entity;

import java.util.Date;

/**
 * @ClassName filterInformationEntity
 * @Description TODO
 * @Author H Z
 * @Date 2020-03-16 20:15
 * @Version 1.0
 **/
public class filterInformationEntity {
    private String presentence="我不得不告诉你这是真的";
    private String processedSentence="";
    private Boolean TrueOrNo;
    private Date currentTime;

    public String getPresentence() {
        return presentence;
    }

    public void setPresentence(String presentence) {
        this.presentence = presentence;
    }

    public String getProcessedSentence() {
        return processedSentence;
    }

    public void setProcessedSentence(String processedSentence) {
        this.processedSentence = processedSentence;
    }


    public Boolean getTrueOrNo() {
        return TrueOrNo;
    }

    public void setTrueOrNo(Boolean trueOrNo) {
        TrueOrNo = trueOrNo;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
