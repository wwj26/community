package com.hard.controller;

import com.hard.dao.UserDao;
import com.hard.dto.AccessTokenDto;
import com.hard.dto.GithubUser;
import com.hard.entity.User;
import com.hard.provider.GithubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import sun.plugin.util.UIUtil;

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
    @Autowired
    private UserDao userDao;


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
        GithubUser githubUser = githubProvider.getUser (accessToken);
        if(githubUser != null) {
            User user = new User ();
            user.setToken (UUID.randomUUID ().toString ());
            user.setAccountId (String.valueOf (githubUser.getId ()));
            user.setName (githubUser.getName ());
            user.setGmtCreate (System.currentTimeMillis ());
            user.setGmtModified (user.getGmtCreate ());
            userDao.save (user);

            request.getSession ().setAttribute ("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
