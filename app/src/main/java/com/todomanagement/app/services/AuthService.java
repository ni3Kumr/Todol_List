package com.todomanagement.app.services;

import com.todomanagement.app.dto.JwtAuthResponse;
import com.todomanagement.app.dto.LoginDto;
import com.todomanagement.app.dto.RegisterDto;

public interface AuthService {
    String register (RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
