package com.hard.controller;

import com.hard.dto.AccessTokenDto;
import com.hard.dto.GithubUser;
import com.hard.provider.GithubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorzeController {

    @Autowired
    private GithubProvider githubProvider;

    @RequestMapping("callback")
    public String callback(@RequestParam(name = "code")  String code,
                           @RequestParam(name = "state") String state){

        AccessTokenDto accessTokenDto = new AccessTokenDto ();
        accessTokenDto.setClient_id ("dc951789fcf0508a498c");
        accessTokenDto.setClient_secret ("11f0b1a8533c202a5755c721f0782de4ed4b1210");
        accessTokenDto.setCode (code);
        accessTokenDto.setRedirect_uri ("http://localhost:8887/callback");
        accessTokenDto.setState (state);
        String accessToken = githubProvider.getAccessToken (accessTokenDto);
        GithubUser user = githubProvider.getUser (accessToken);
        System.out.println (user.getName ());
        return "index";

    }
}
