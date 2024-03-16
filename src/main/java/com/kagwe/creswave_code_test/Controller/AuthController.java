package com.kagwe.creswave_code_test.Controller;


import com.kagwe.creswave_code_test.DTO.AuthResponse;
import com.kagwe.creswave_code_test.Entities.UserEntity;
import com.kagwe.creswave_code_test.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody UserEntity user){
        return service.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserEntity user){
        return service.login(user);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthResponse> deleteUser(@RequestParam String email){
        return service.deleteUser(email);
    }

    @GetMapping("/user")
    public ResponseEntity<AuthResponse> getUsers(@RequestParam String email){
        return service.getUser(email);
    }

    @PutMapping("edit_user")
    public ResponseEntity<AuthResponse> editUser(@RequestBody UserEntity user){
        return service.editUser(user);
    }
}
