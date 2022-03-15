package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;

class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
