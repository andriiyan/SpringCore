package com.github.andriiyan.sprongtraining.impl.service;

import com.github.andriiyan.sprongtraining.api.dao.UserDao;
import com.github.andriiyan.sprongtraining.api.model.User;
import com.github.andriiyan.sprongtraining.impl.TestModelsFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserServiceImpl userService;

    @Before
    public void init() {
        userService = new UserServiceImpl();
        userService.setUserDao(userDao);
    }

    @Test
    public void getUserById() {
        final long userId = 123;
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.findById(userId)).thenReturn(returningUser);

        final User returnedUser = userService.getUserById(userId);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).findById(userId);
    }

    @Test
    public void getUserByEmail() {
        final String email = "email@test.com";
        User returningUser = TestModelsFactory.generateSingleUser();
        returningUser.setEmail(email);
        Mockito.when(userDao.findAll()).thenReturn(Collections.singletonList(returningUser));

        final User returnedUser1 = userService.getUserByEmail(email);

        Assert.assertEquals(returningUser, returnedUser1);
        Mockito.verify(userDao).findAll();

        // case when there is no user with such email
        returningUser.setEmail("");

        final User returnedUser2 = userService.getUserByEmail(email);

        Assert.assertNull(returnedUser2);
        Mockito.verify(userDao, Mockito.times(2)).findAll();

        // case when it's more than a 1 user with such email
        final List<User> userList = TestModelsFactory.generateUsers(3);
        userList.forEach(user -> user.setEmail(email));
        Mockito.when(userDao.findAll()).thenReturn(userList);

        final User returnedUser3 = userService.getUserByEmail(email);

        Assert.assertEquals(userList.get(0), returnedUser3);
        Mockito.verify(userDao, Mockito.times(3)).findAll();
    }

    @Test
    public void getUsersByName() {
        final String searchingName = "name";
        int pageSize = 3;
        int pageNum = 2;
        List<User> returningUsers = TestModelsFactory.generateUsers((pageNum + 1) * pageSize,
                new TestModelsFactory.DefaultUserCountInstanceFactory() {
                    @Override
                    protected String name(int count) {
                        return searchingName + count;
                    }
                });
        Mockito.when(userDao.findAll()).thenReturn(returningUsers);

        List<User> returnedUsers = userService.getUsersByName(searchingName, pageSize, pageNum);

        Assert.assertEquals(returningUsers.subList(pageNum * pageSize, (pageNum + 1) * pageSize), returnedUsers);
        Mockito.verify(userDao).findAll();

        // case when there are no users that contains that name
        returningUsers = Collections.emptyList();
        Mockito.when(userDao.findAll()).thenReturn(returningUsers);

        returnedUsers = userService.getUsersByName(searchingName, pageSize, pageNum);
        Assert.assertEquals(returningUsers, returnedUsers);
        Mockito.verify(userDao, Mockito.times(2)).findAll();
    }

    @Test
    public void createUser() {
        final User user = TestModelsFactory.generateSingleUser();
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.save(user)).thenReturn(returningUser);

        final User returnedUser = userService.createUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).save(user);
    }

    @Test
    public void updateUser() {
        final User user = TestModelsFactory.generateSingleUser();
        final User returningUser = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.update(user)).thenReturn(returningUser);

        final User returnedUser = userService.updateUser(user);

        Assert.assertEquals(returningUser, returnedUser);
        Mockito.verify(userDao).update(user);
    }

    @Test
    public void deleteUser() {
        final User user = TestModelsFactory.generateSingleUser();
        Mockito.when(userDao.delete(user.getId())).thenReturn(true);

        final boolean result = userService.deleteUser(user.getId());

        Assert.assertTrue(result);
        Mockito.verify(userDao).delete(user.getId());
    }
}