package org.project.system.user.service;

import org.project.system.product.ProductException;
import org.project.system.user.domain.User;
import org.project.system.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User find(Long id) throws ProductException{
        return userRepository.findById(id).orElseThrow(()->new ProductException("Unknown user"));
    }
}
