package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class DemoSecurityConfig {

        @Bean
        public InMemoryUserDetailsManager userDetailsManager() {
                UserDetails john = User.builder()
                                .username("john")
                                .password("{noop}test123")
                                .roles("EMPLOYEE")
                                .build();

                UserDetails mary = User.builder()
                                .username("mary")
                                .password("{noop}test123")
                                .roles("EMPLOYEE", "MANAGER")
                                .build();

                UserDetails susan = User.builder()
                                .username("susan")
                                .password("{noop}test123")
                                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                                .build();

                return new InMemoryUserDetailsManager(john, mary, susan);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(
                                configurer -> configurer
                                                .anyRequest()
                                                .authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/authentication")
                                                .permitAll())
                                .logout(logout -> logout.permitAll());

                return httpSecurity.build();
        }
}
