package com.MyFirstProject.demo.provider;

import com.MyFirstProject.demo.dto.AccessTokenDto;
import com.MyFirstProject.demo.dto.GithubUser;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Component
/**
 *
 */
public class GithubProvider {

  //this is my first line of coding
  public String getAccessToken(AccessTokenDto accessTokenDto) {

    //paste from okhttp: post to server
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
    Request request = new Request.Builder()
        .url("https://github.com/login/oauth/access_token")
        .post(body)
        .build();
    try (Response response = client.newCall(request).execute()) {
      String str = response.body().string();
      String token = str.split("&")[0].split("=")[1];
      return token;

    } catch (Exception e){
      System.out.println("error");
    }
    return null;
  }

  public GithubUser getUser(String accessToken){
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url("https://api.github.com/user?access_token=" + accessToken)
        .build();
    try {
      Response response = client.newCall(request).execute();
      String string = response.body().string();
      GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
      return githubUser;
    } catch (IOException e) {
    }
    return null;
  }


}
