package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }
}
