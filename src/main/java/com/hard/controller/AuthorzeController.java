package com.hard.controller;

import com.hard.dto.AccessTokenDto;
import com.hard.dto.GithubUser;
import com.hard.provider.GithubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorzeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value ("${github.clientId}")
    private String clientId;
    @Value ("${github.clientSecret}")
    private String clientSecret;
    @Value ("${github.url}")
    private String url;


    @RequestMapping("callback")
    public String callback(@RequestParam(name = "code")  String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDto accessTokenDto = new AccessTokenDto ();
        accessTokenDto.setClient_id (clientId);
        accessTokenDto.setClient_secret (clientSecret);
        accessTokenDto.setCode (code);
        accessTokenDto.setRedirect_uri (url);
        accessTokenDto.setState (state);
        String accessToken = githubProvider.getAccessToken (accessTokenDto);
        GithubUser user = githubProvider.getUser (accessToken);
        if(user != null) {
            request.getSession ().setAttribute ("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
