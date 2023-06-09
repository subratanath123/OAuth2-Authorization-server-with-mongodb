package com.authorization.server.authorization.server.config;

import com.authorization.server.authorization.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Bean
    SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/public/**")
                                .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(
                        loginConfigure ->
                                loginConfigure
                                        .loginPage("/auth-login")
                                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return userService;
    }

}
