package com.todomanagement.app.config;

import com.todomanagement.app.security.JwtAuthenticationEntryPoint;
import com.todomanagement.app.security.JwtAuthenticationFilter;
import com.todomanagement.app.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize)->{
//                    authorize.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT,"api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
//                    authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
                    authorize.requestMatchers("api/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
//
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails nitin = User.builder()
//                .username("nitin")
//                .password(passwordEncode().encode("nitin"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncode().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(nitin,admin);
//    }


}
