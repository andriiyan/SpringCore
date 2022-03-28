package com.github.andriiyan.sprongtraining.impl.dao;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.storage.Storage;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl(Storage<User> storage) {
        super(storage);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public User getUserByEmail(String email) {
        final User mUser = findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        logger.debug("getUserByEmail was invoked with email={} and returning {}", email, mUser);
        return mUser;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> users = findPage(pageNum, pageSize, user -> user.getName().contains(name));
        logger.debug("getUsersByName was invoked with name={}, pageSize={}, pageNum={} and returning {}", name, pageSize, pageNum, users);
        return users;
    }
}
