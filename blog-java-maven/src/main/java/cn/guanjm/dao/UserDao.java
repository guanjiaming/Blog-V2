package cn.guanjm.dao;

import cn.guanjm.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    User queryUserById(@Param("id") Long id);

    User queryUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}