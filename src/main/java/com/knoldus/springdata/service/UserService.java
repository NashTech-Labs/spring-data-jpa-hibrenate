package com.knoldus.springdata.service;

import com.knoldus.springdata.entity.UserEntity;

import java.util.Collection;

public interface UserService {

    String createUser(String name, String email);

    Collection<UserEntity> getAll();

    void deleteUser(String id);

}
