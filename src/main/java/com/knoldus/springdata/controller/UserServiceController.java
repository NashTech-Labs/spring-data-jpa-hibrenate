package com.knoldus.springdata.controller;

import com.knoldus.springdata.dto.CreateUserRequest;
import com.knoldus.springdata.dto.UserData;
import com.knoldus.springdata.dto.UserId;
import com.knoldus.springdata.entity.UserEntity;
import com.knoldus.springdata.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/services")
public class UserServiceController {

    private final UserService userService;

    public UserServiceController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserId> createUser(@RequestBody CreateUserRequest request) {
        String id = userService.createUser(request.getName(), request.getEmail());
        return ResponseEntity.ok(new UserId(id));
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<UserData>> getAll() {
        Collection<UserEntity> entities = userService.getAll();
        Collection<UserData> users = entities.stream().map(UserServiceController::transform).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    private static UserData transform(UserEntity entity) {
        return new UserData(entity.getId(), entity.getName(), entity.getEmail(), entity.getEnabled());
    }

}
