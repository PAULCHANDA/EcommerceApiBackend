package com.paul.ecommerce.controller;

import com.paul.ecommerce.model.login.AuthenticationRequest;
import com.paul.ecommerce.model.login.AuthenticationResponse;
import com.paul.ecommerce.model.user.UserDTO;
import com.paul.ecommerce.service.MyUserDetailsService;
import com.paul.ecommerce.service.UserService;
import com.paul.ecommerce.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    public UserController(AuthenticationManager authenticationManager, UserService userService, MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping(value = "createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody UserDTO request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}