package com.example.demo.xm.flowProcessing;


/**
 * @ClassName scoreProcessing
 * @Description TODO
 * @Author ZH
 * @Date 2020-03-19 21:23
 * @Version 1.0
 **/
public class scoreProcessing {
    /*

     */
    public double returnScore(double cos,double Semantic/*,double EditDistance*/,double morpho){
        double A=1.725;
        double B=1.12;
        double C=0.5;
        double D=0.155;
        double score = 0;
        score=Math.round((cos*A+Semantic*B/*+EditDistance*C*/+morpho*D)*10.0/3.0);
       // System.out.println(score);
        return  score+1;

    }


}
