package com.eranbackend.erandevu.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eranbackend.erandevu.dto.UserDto;
import com.eranbackend.erandevu.entity.User;
import com.eranbackend.erandevu.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDto userDto) {

        Optional<User> findUser = userRepository.findById(userDto.getId());

        if (userDto != null && findUser.isEmpty()) {
            User newUser = new User();
            newUser = modelMapper.map(userDto, User.class);
            newUser.setStatus(true);
            newUser.setUptodate(LocalDateTime.now());
            userRepository.save(newUser);
            return newUser;
        }
        return null;
    }

    public User updateUser(UserDto userDto) {
        Optional<User> findUser = userRepository.findById(userDto.getId());
        if (userDto != null && findUser.isEmpty()) {
            User newUser = new User();
            newUser = modelMapper.map(userDto, User.class);
            newUser.setStatus(true);
            newUser.setUptodate(LocalDateTime.now());
            userRepository.save(newUser);
            return newUser;
        }
        return null;
    }

    public User getUserById(Long Id) {
        Optional<User> userUstId = userRepository.findById(Id);
        return userUstId.get();
    }

    public void deleteUser(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        User newUser = new User();
        if (findUser != null) {
            newUser = findUser.get();
            newUser.setStatus(false);
            newUser.setUptodate(LocalDateTime.now());
            userRepository.save(newUser);
        }
    }

}
