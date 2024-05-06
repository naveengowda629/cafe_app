package com.inn.cafe.dao;

import com.inn.cafe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {

    User findEmailId(@Param("email") String email);
}
