package cn.guanjm.service.impl;

import cn.guanjm.common.enums.ExceptionEnum;
import cn.guanjm.common.exception.UmException;
import cn.guanjm.dao.UserDao;
import cn.guanjm.entity.User;
import cn.guanjm.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        User user = userDao.queryUserByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UmException(ExceptionEnum.LOGIN_FAILED);
        }
        return user;
    }

    @Override
    public User queryUserById(Long id) {
        User user = userDao.queryUserById(id);
        if (user == null) {
            throw new UmException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }
}