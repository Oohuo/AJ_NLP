package com.example.demo.xm.flowProcessing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * @ClassName KeyWordSimilarity
 * @Description TODO
 * @Author ZH
 * @Date 2020-03-22 21:40
 * @Version 1.0
 **/
public class KeyWordSimilarity {


    /**
     * 向指定URL发送POST方式的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     * @return URL 代表远程资源的响应
     */
    public static String sendPost(String url, String param) {
        String result = "";
        try {
            URL realUrl = new URL(url);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(param);
            //flush输出流的缓冲
            out.flush();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常" + e);
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        Double grate;
        String s1 = "s1=";
        String s2 = "s2=";

        //String text1="你是对的";
        //String answer = "你是对的";

        String text1 = "复杂系统、大跨度系统";
        String answer = "物流系统是复杂系统2、物流系统是大跨度系统";

        //String text1="我不得不承认你是对的";
        //String answer = "你是对的";

        String urlNegativeWords = HttpRequest.sendPost("http://47.113.115.243:8080/api/getNegativeWordFilteringInformation", s1 + text1);
        System.out.println(urlNegativeWords);
        JSONObject jsonObject1 = JSONObject.parseObject(urlNegativeWords);
        // 获取到key为similarty的值

        String negativeProcessed = jsonObject1.getString("processedSentence");

        String urlSegment = HttpRequest.sendPost("http://47.113.115.243:8080/api/getsegment", s1 + negativeProcessed + "&" + s2 + answer);
        System.out.println(urlSegment);
        JSONObject jsonObject2 = JSONObject.parseObject(urlSegment);
        JSONArray consineSimilarty = jsonObject2.getJSONArray("list");
        System.out.println(consineSimilarty);

        
        double value = 0.0;
        double x = 0.0;

        String[] keywordsarray;
        keywordsarray = new String[]{"物流", "系统"};
        for (int m = 0; m < keywordsarray.length; m++) {
            for (int i = 0; i < consineSimilarty.size(); i++) {
                String pos = (String) consineSimilarty.getJSONObject(i).get("pos");
                if (Objects.equals(pos, "n")) {
                    String name = (String) consineSimilarty.getJSONObject(i).get("name");
                    System.out.println(name);
                    //http://47.113.115.243:8080/api/getCilinSimilarity
                    String urlCilinSimilarity = HttpRequest.sendPost("http://47.113.115.243:8080/api/getCilinSimilarity", s1 + name + "&" + s2 + keywordsarray[m]);
                    JSONObject jsonObject3 = JSONObject.parseObject(urlCilinSimilarity);
                    String cilinSimilarty = jsonObject3.getString("similarty");
                    double consineSimilartyValue = Double.parseDouble(cilinSimilarty);
                    System.out.println(consineSimilartyValue);
                    value += consineSimilartyValue;
                    x++;

                }
            }
        }
        System.out.println(value / x / (double) keywordsarray.length);
    }
}
