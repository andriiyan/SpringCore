package com.github.andriiyan.sprongtraining.api.dao;

import com.github.andriiyan.sprongtraining.api.model.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    User getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

}
