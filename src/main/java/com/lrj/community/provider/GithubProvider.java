package com.lrj.community.provider;

import com.alibaba.fastjson.JSON;
import com.lrj.community.dto.AccessTokenDTO;
import com.lrj.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

//用于调用github 登陆的api 进行的请求
@Component
public class GithubProvider {
    /**
     * get请求
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //通过json数据解析获得 请求的主体
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        //拼接一个请求
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        //发送一个请求获得一个响应
        try (Response response = client.newCall(request).execute()) {
            //获取响应中的主体
            String string = response.body().string();
            //分割响应体获得 account_token
            String token = string.split("&")[0].split("=")[1];
            System.out.println("这个是account_token:"+string);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过post请求获得 github的账号信息
     * @param accessToken 账号令牌
     * @return
     */
    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        //拼接一个请求
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        //发送一个请求获得一个响应
        try (Response response = client.newCall(request).execute()) {
            //获取响应的主体
            String string = response.body().string();
            System.out.println(string);
            //通过反射 将 响应的内容 注入 GithubUser 对象中
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
