package org.project.system.user.service;

import org.project.system.user.domain.User;
import org.project.system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> find(Long id){
        return userRepository.findById(id);
    }
}
