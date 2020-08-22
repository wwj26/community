package com.hard.dao;

import com.hard.entity.User;

public interface UserDao {

    void save(User user);

    User findByToken (String value);
}
