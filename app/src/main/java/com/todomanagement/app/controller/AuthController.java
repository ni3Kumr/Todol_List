package com.todomanagement.app.controller;

import com.todomanagement.app.dto.JwtAuthResponse;
import com.todomanagement.app.dto.LoginDto;
import com.todomanagement.app.dto.RegisterDto;
import com.todomanagement.app.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    // build register REST API

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody  RegisterDto registerDto){
       String response =  authService.register(registerDto);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // build login rest api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
       JwtAuthResponse jwtAuthResponse =  authService.login(loginDto);
//       JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
//       jwtAuthResponse.setAccessToken(token);
       return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }


}
