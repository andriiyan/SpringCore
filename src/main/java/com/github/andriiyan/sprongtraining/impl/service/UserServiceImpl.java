package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long userId) {
        final User user = userDao.findById(userId);
        logger.info("getUserById was invoked with userId=" + userId + " and returning " + user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        final User mUser = userDao.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        logger.info("getUserByEmail was invoked with email=" + email + " and returning " + mUser);
        return mUser;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        final List<User> users = StreamUtils.paging(
                userDao.findAll()
                        .stream()
                        .filter(user -> user.getName().contains(name)),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
        logger.info("getUsersByName was invoked with name=" + name + ", pageSize=" + pageSize + ", pageNum=" + pageNum +
                " and returning " + users.toString());
        return users;
    }

    @Override
    public User createUser(User user) {
        final User mUser = userDao.save(user);
        logger.info("createUser was invoked with user=" + user + " and returning " + mUser);
        return mUser;
    }

    @Override
    public User updateUser(User user) {
        final User mUser = userDao.update(user);
        logger.info("updateUser was invoked with user=" + user  + " and returning " + mUser);
        return mUser;
    }

    @Override
    public boolean deleteUser(long userId) {
        final boolean result = userDao.delete(userId);
        logger.info("deleteUser was invoked with userId=" + userId + " and returning " + result);
        return result;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
