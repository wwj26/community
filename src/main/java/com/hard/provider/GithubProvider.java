package com.hard.provider;

import com.alibaba.fastjson.JSON;
import com.hard.dto.AccessTokenDto;
import com.hard.dto.GithubUser;

import org.springframework.stereotype.Component;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.parse ("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create (mediaType,JSON.toJSONString (accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body ().string ();
            String token = string.split ("&")[0].split ("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            GithubUser githubUser = JSON.parseObject (response.body ().string (), GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return null;

    }
}
