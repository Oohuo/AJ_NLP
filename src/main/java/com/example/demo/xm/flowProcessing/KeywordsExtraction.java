package com.example.demo.xm.flowProcessing;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.nlp.v20190408.NlpClient;

import com.tencentcloudapi.nlp.v20190408.models.KeywordsExtractionRequest;
import com.tencentcloudapi.nlp.v20190408.models.KeywordsExtractionResponse;

public class KeywordsExtraction
{
    public static void main(String [] args) {
        try{

            Credential cred = new Credential("AKIDPgS7d0z9wb65vgtiwBJcliXQMHE4pppe", "oV2OZKl9QitAJGev44wS5bvIhv2zwiR0");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("nlp.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            NlpClient client = new NlpClient(cred, "ap-guangzhou", clientProfile);

            String params = "{\"Num\":5,\"Text\":\"物流系统是由物流作业系统和支持物流信息流动的物流信息系统两部分组成\"}";
            KeywordsExtractionRequest req = KeywordsExtractionRequest.fromJsonString(params, KeywordsExtractionRequest.class);

            KeywordsExtractionResponse resp = client.KeywordsExtraction(req);

            System.out.println(KeywordsExtractionRequest.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }

}
