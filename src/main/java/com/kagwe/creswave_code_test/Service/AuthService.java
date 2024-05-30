package com.kagwe.creswave_code_test.Service;

import com.kagwe.creswave_code_test.Config.JwtService;
import com.kagwe.creswave_code_test.DTO.AuthResponse;
import com.kagwe.creswave_code_test.Entities.Role;
import com.kagwe.creswave_code_test.Entities.UserEntity;
import com.kagwe.creswave_code_test.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthResponse> registerUser(UserEntity user){
        AuthResponse res = new AuthResponse();
        try {
            //check if user already exists
            AtomicReference<AuthResponse> resp = new AtomicReference<>(new AuthResponse());

            Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
            existingUser.ifPresentOrElse(
                    user1 -> {
                        resp.get().setMessage("User with that email already exists");
                        resp.get().setStatus(409);
                    },
                    () -> {
                        String encodedPassword  = passwordEncoder.encode(user.getPassword());
                        UserEntity newUser = UserEntity.builder()
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .phoneNumber(user.getPhoneNumber())
                                .email(user.getEmail().toLowerCase())
                                .fullName(user.getFirstName()+user.getLastName())
                                .password(encodedPassword)
                                .role(Role.valueOf(String.valueOf(user.getRole()).toUpperCase()))
                                .build();
                        userRepository.save(newUser);
                        var jwtToken = jwtService.generateToken(newUser);
                        resp.get().setMessage( "Account created successfully for "+user.getFirstName());
                        resp.get().setJwt(jwtToken);
                        resp.get().setUser(user);
                    }
            );
            return ResponseEntity.ok().body(resp.get());

        } catch (Exception e){
            log.error("An error occured "+e.getMessage());
            res.setMessage("Could not create user");
            res.setStatus(500);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    public ResponseEntity<AuthResponse> login(UserEntity user){
        AuthResponse response = new AuthResponse();

        try {
            //check if that user exists
            AtomicReference<AuthResponse> res = new AtomicReference<>(new AuthResponse());
            Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
            existingUser.ifPresentOrElse(
                    user1 -> {
                        if (!passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())){
                            res.get().setMessage("Wrong password");
                            res.get().setStatus(401);
                        } else {
                            try {
                                var jwtToken = jwtService.generateToken(existingUser.get());
                                log.info("user "+existingUser.get());
                                res.get().setMessage("Login successful");
                                res.get().setJwt(jwtToken);
                            } catch (Exception e){
                                log.error("error trying to login user "+e.getMessage());
                                res.get().setStatus(500);
                                res.get().setMessage("an error occured while logging user in");
                            }
                        }
                    },
                    () -> {
                        res.get().setStatus(404);
                        res.get().setMessage("No one with that email found in system");
                    }
            );
            return ResponseEntity.ok().body(res.get());

        } catch (Exception e){
            log.error("An error occured "+e.getMessage());
            response.setMessage("Could not login user");
            response.setStatus(500);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<AuthResponse> deleteUser(String userEmail){
        //check if user exists

        Optional<UserEntity> existingUser = userRepository.findByEmail(userEmail);
        AtomicReference<AuthResponse> res = new AtomicReference<>(new AuthResponse());
        existingUser.ifPresentOrElse(
                user -> {
                    existingUser.get().setDeleted(true);
                    userRepository.save(existingUser.get());
                    res.get().setStatus(200);
                    res.get().setMessage("User deleted successfully");
                },
                () -> {
                    res.get().setStatus(404);
                    res.get().setMessage("No one with that email found in system");
                }
        );
        return ResponseEntity.ok().body(res.get());
    }

    public ResponseEntity<AuthResponse> getUser(String email) {
        AuthResponse resp = new AuthResponse();

        try{
            Optional<UserEntity> existingUser = userRepository.findByEmail(email);
            AtomicReference<AuthResponse> res = new AtomicReference<>(new AuthResponse());
            existingUser.ifPresentOrElse(
                    user -> {
                        existingUser.get();
                        res.get().setUser(existingUser.get());
                        res.get().setStatus(200);
                        res.get().setMessage("User found");
                    },
                    () -> {
                        res.get().setStatus(404);
                        res.get().setMessage("No one with that email found in system");
                    }
            );
            return ResponseEntity.ok().body(res.get());
        } catch (Exception e){
            log.error("An error occured "+e.getMessage());
            resp.setMessage("An error occured");
            resp.setStatus(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    public ResponseEntity<AuthResponse> editUser(UserEntity user) {
        AuthResponse resp = new AuthResponse();

        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
            AtomicReference<AuthResponse> res = new AtomicReference<>(new AuthResponse());
            existingUser.ifPresentOrElse(
                    user1 -> {
                        existingUser.get().setEmail(user.getEmail() != null ? user.getEmail() : existingUser.get().getEmail());
                        existingUser.get().setRole(user.getRole() != null ? user.getRole() : existingUser.get().getRole());
                        existingUser.get().setFirstName(user.getFirstName() != null ? user.getFirstName() : existingUser.get().getFirstName());
                        existingUser.get().setLastName(user.getLastName() != null ? user.getLastName() : existingUser.get().getLastName());
                        existingUser.get().setFullName(user.getFullName() != null ? user.getFullName() : existingUser.get().getFullName());
                        userRepository.save(existingUser.get());
                        res.get().setStatus(200);
                        res.get().setMessage("User details updated successfully");
                    },
                    () -> {
                        res.get().setStatus(404);
                        res.get().setMessage("No one with that email found in system");
                    }
            );
            return ResponseEntity.ok().body(res.get());
        } catch (Exception e){
            log.error("An error occured "+e.getMessage());
            resp.setMessage("An error occured");
            resp.setStatus(500);
            return ResponseEntity.internalServerError().body(resp);
        }

    }
}
