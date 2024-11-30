package com.eranbackend.erandevu.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eranbackend.erandevu.dto.UserDto;
import com.eranbackend.erandevu.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
      return userService.deleteUser(id);
    }

}
