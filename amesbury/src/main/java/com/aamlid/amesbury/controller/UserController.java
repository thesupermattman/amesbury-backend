package com.aamlid.amesbury.controller;

import com.aamlid.amesbury.entity.UserEntity;
import com.aamlid.amesbury.model.JwtRequest;
import com.aamlid.amesbury.model.JwtResponse;
import com.aamlid.amesbury.model.UserResponse;
import com.aamlid.amesbury.service.CustomUserDetailsService;
import com.aamlid.amesbury.service.UserService;
import com.aamlid.amesbury.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/amesbury-user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public UserEntity registerUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/main")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.findUserByUsername(userDetails.getUsername());
        UserResponse userResponse = new UserResponse(user.getUsername(), user.getEmail());
        return ResponseEntity.ok(userResponse);
    }

//    @PostMapping
//    public UserEntity createUser(@RequestBody UserEntity user) {
//        return userService.saveUser(user);
//    }
//
//    @GetMapping("/email/{email}")
//    public UserEntity getUserByEmail(@PathVariable String email) {
//        return userService.findUserByEmail(email);
//    }
//
//    @GetMapping("/username/{username}")
//    public UserEntity getUserByUsername(@PathVariable String username) {
//        return userService.findUserByUsername(username);
//    }
//
//    @GetMapping
//    public List<UserEntity> getAllUsers() {
//        return userService.findAllUsers();
//    }
//
//    @DeleteMapping("/{email}")
//    public void deleteUserByEmail(@PathVariable String email) {
//        userService.deleteUserByEmail(email);
//    }
}
