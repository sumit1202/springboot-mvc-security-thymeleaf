package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
                return new JdbcUserDetailsManager(dataSource);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(
                                configurer -> configurer
                                                .requestMatchers("/").hasRole("EMPLOYEE")
                                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                                .requestMatchers("/systems/**").hasRole("ADMIN")

                                                .anyRequest()
                                                .authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/authentication")
                                                .permitAll())
                                .logout(logout -> logout
                                                .permitAll())
                                .exceptionHandling(
                                                configurer -> configurer
                                                                .accessDeniedPage("/access-denied"));

                return httpSecurity.build();
        }
}
