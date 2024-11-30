package com.eranbackend.erandevu.service;

import com.eranbackend.erandevu.dto.UserDto;
import com.eranbackend.erandevu.entity.User;
import com.eranbackend.erandevu.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<Map<String, Object>> saveUser(UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userDto == null) {
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "UserDto cannot be null.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<User> findUser = userRepository.findById(userDto.getId());
            if (findUser.isPresent()) {
                response.put("status", HttpStatus.CONFLICT.value());
                response.put("message", "User with this ID already exists.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            User newUser = modelMapper.map(userDto, User.class);
            newUser.setStatus(true);
            newUser.setUptodate(LocalDateTime.now());
            User savedUser = userRepository.save(newUser);

            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "User saved successfully.");
            response.put("data", savedUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Failed to save user.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateUser(UserDto userDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (userDto == null) {
                response.put("status", HttpStatus.BAD_REQUEST.value());
                response.put("message", "UserDto cannot be null.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Optional<User> findUser = userRepository.findById(userDto.getId());
            if (findUser.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "User not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            User existingUser = findUser.get();
            modelMapper.map(userDto, existingUser);
            existingUser.setStatus(true);
            existingUser.setUptodate(LocalDateTime.now());
            User updatedUser = userRepository.save(existingUser);

            response.put("status", HttpStatus.OK.value());
            response.put("message", "User updated successfully.");
            response.put("data", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Failed to update user.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, Object>> getUserById(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "User not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            response.put("status", HttpStatus.OK.value());
            response.put("message", "User retrieved successfully.");
            response.put("data", user.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Failed to retrieve user.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteUser(UUID id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<User> findUser = userRepository.findById(id);
            if (findUser.isEmpty()) {
                response.put("status", HttpStatus.NOT_FOUND.value());
                response.put("message", "User not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            User existingUser = findUser.get();
            existingUser.setStatus(false);
            existingUser.setUptodate(LocalDateTime.now());
            userRepository.save(existingUser);

            response.put("status", HttpStatus.OK.value());
            response.put("message", "User deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Failed to delete user.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
