package com.college.jobboard.service;

import com.college.jobboard.model.User;
import com.college.jobboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username){
        return userRepository.findById(username).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
