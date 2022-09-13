package cn.guanjm.service;

import cn.guanjm.entity.User;

public interface UserService {
    User queryUserByUsernameAndPassword(String username, String password);

    User queryUserById(Long id);
}
