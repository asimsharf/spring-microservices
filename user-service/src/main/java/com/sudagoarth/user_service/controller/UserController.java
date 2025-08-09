package com.sudagoarth.user_service.controller;

import com.sudagoarth.common.response.ApiResponse;
import com.sudagoarth.user_service.model.User;
import com.sudagoarth.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<User>> list() {
        return ApiResponse.ok(service.all());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@RequestBody User body) {
        User saved = service.create(body);
        return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(ApiResponse.ok("Created", saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> get(@PathVariable Long id) {
        User u = service.get(id);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(ApiResponse.ok(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
