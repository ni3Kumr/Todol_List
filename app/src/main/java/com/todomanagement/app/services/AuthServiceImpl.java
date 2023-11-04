package com.todomanagement.app.services;

import com.todomanagement.app.dto.JwtAuthResponse;
import com.todomanagement.app.dto.LoginDto;
import com.todomanagement.app.dto.RegisterDto;
import com.todomanagement.app.entity.Role;
import com.todomanagement.app.entity.User;
import com.todomanagement.app.exception.TodoApiException;
import com.todomanagement.app.repository.RoleRepository;
import com.todomanagement.app.repository.UserRepository;
import com.todomanagement.app.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl  implements AuthService{
    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterDto registerDto) {
        // check user name is already exist in database;
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,"User name is Already Exist");
        }
        // if email is already exist
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoApiException(HttpStatus.BAD_REQUEST,"User Email is already exist");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole =   roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered Successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        // finding role role
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());
        String role = null;
        if(userOptional.isPresent())
        {
            User loggeInUser = userOptional.get();
            Optional< Role> optionalRole =loggeInUser.getRoles().stream().findFirst();
            if(optionalRole.isPresent()){
                Role userRole = optionalRole.get();
                role = userRole.getName();

            }
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }
}
