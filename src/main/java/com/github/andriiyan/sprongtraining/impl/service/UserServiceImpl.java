package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.api.service.UserService;
import com.github.andriiyan.sprongtraining.impl.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long userId) {
        return userDao.findById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return StreamUtils.paging(
                userDao.findAll()
                        .stream()
                        .filter(user -> user.getName().contains(name)),
                pageNum,
                pageSize
        ).collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return userDao.delete(userId);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
