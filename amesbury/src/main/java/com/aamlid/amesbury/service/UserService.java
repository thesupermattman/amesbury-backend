package com.aamlid.amesbury.service;

import com.aamlid.amesbury.entity.UserEntity;
import com.aamlid.amesbury.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteById(email);
    }
}