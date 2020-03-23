package com.example.demo.xm.flowProcessing;



import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


/**
 * java发送http的get和post请求
 */
public class HttpRequest {


	/**
	 * 向指定URL发送POST方式的请求
	 * @param url  发送请求的URL
	 * @param param 请求参数
	 * @return URL 代表远程资源的响应
	 */
	public static String sendPost(String url, String param){
		String result = "";
		try{
			URL realUrl = new URL(url);
			//打开和URL之间的连接
			URLConnection conn =  realUrl.openConnection();
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

	//测试发送GET和POST请求
	public static void main(String[] args) throws Exception{
        //发送GET请求

        //发送POST请求
		Double grate;
		String s1="s1=";
		String s2="s2=";

		//String text1="你是对的";
		//String answer = "你是对的";

		String text1="复杂系统、大跨度系统";
		String answer = "物流系统是复杂系统2、物流系统是大跨度系统";

		//String text1="我不得不承认你是对的";
		//String answer = "你是对的";

        String url_negativeWords = HttpRequest.sendPost("http://47.113.115.243:8080/api/getNegativeWordFilteringInformation", s1+text1);
        System.out.println(url_negativeWords);
		JSONObject jsonObject1 = JSONObject.parseObject(url_negativeWords);
		// 获取到key为similarty的值

		String negativeProcessed = jsonObject1.getString("processedSentence");
		//System.out.println(text1);
		//System.out.println(negativeProcessed);
		String url_Cosine = HttpRequest.sendPost("http://47.113.115.243:8080/api/getCosineSimilarity", s1+negativeProcessed+"&"+s2+answer);
		System.out.println(url_Cosine);
		JSONObject jsonObject2 = JSONObject.parseObject(url_Cosine);
		String consineSimilarty = jsonObject2.getString("similarty");
		double consineSimilartyValue = Double.parseDouble(consineSimilarty);
		//System.out.println(consineSimilartyValue);
		String url_Semantic = HttpRequest.sendPost("http://47.113.115.243:8080/api/getSemanticSimilarity", s1+negativeProcessed+"&"+s2+answer);

		System.out.println(url_Semantic);

		JSONObject jsonObject3 = JSONObject.parseObject(url_Semantic);
		String semanticSimilarty = jsonObject3.getString("similarty");
		double semanticSimilartyValue = Double.parseDouble(semanticSimilarty);

		//
		System.out.println(negativeProcessed);
		System.out.println(answer);

		String url_EditDistance = HttpRequest.sendPost("http://47.113.115.243:8080/api/getNewEditDistance", s1+negativeProcessed+"&"+s2+answer);

		System.out.println(url_EditDistance);
		JSONObject jsonObject4 = JSONObject.parseObject(url_EditDistance);
		String EditDistanceSimilarty = jsonObject4.getString("similarty");
		double EditDistanceSimilartyValue = Double.parseDouble(EditDistanceSimilarty);

		//
		String url_morpho = HttpRequest.sendPost("http://47.113.115.243:8080/api/getmorphosimilarity", s1+negativeProcessed+"&"+s2+answer);
		System.out.println(url_morpho);
		JSONObject jsonObject5 = JSONObject.parseObject(url_morpho);
		String morphoSimilarty = jsonObject5.getString("similarty");
		double morphoSimilartyValue = Double.parseDouble(morphoSimilarty);

		scoreProcessing scoreProcessing = new scoreProcessing();
		grate=scoreProcessing.returnScore(consineSimilartyValue, semanticSimilartyValue/*, EditDistanceSimilartyValue*/, morphoSimilartyValue);

		System.out.println("\n"+"本题分数："+grate);

//狗屁thing
    }
}
