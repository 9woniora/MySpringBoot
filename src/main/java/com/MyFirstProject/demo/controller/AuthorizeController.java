package com.MyFirstProject.demo.controller;

import com.MyFirstProject.demo.dto.AccessTokenDto;
import com.MyFirstProject.demo.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.MyFirstProject.demo.provider.GithubProvider;

@Controller
public class AuthorizeController {

  @Autowired
  private GithubProvider githubProvider;

  @Value("${github.client.id}")
  private String clientId;

  @Value("${github.clientSecret}")
  private String clientSecret;

  @Value("${github.redirectUri}")
  private String redirectUri;

  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code,
      @RequestParam(name = "state") String state) {

    AccessTokenDto accessTokenDto = new AccessTokenDto();
    accessTokenDto.setCode(code);
    accessTokenDto.setClient_id(clientId);
    accessTokenDto.setClient_secret(clientSecret);
    accessTokenDto.setRedirect_uri(redirectUri);
    accessTokenDto.setState(state);

    String accessToken = githubProvider.getAccessToken(accessTokenDto);
    GithubUser user = githubProvider.getUser(accessToken);
    System.out.println(user.getId());
    return "index";
  }
}
