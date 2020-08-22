package com.hard.controller;

import com.hard.dao.UserDao;
import com.hard.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/")
    public String test(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies ();
        for (Cookie cookie : cookies) {
            if(cookie.getName ().equals ("token")){
                User user = userDao.findByToken(cookie.getValue ());
                if(user!=null){
                    httpServletRequest.getSession ().setAttribute ("user",user);
                }
            }
        }
        return "index";
    }
}
