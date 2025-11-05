package com.example.splitwise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // Allow H2 console
                .antMatchers("/h2-console/**").permitAll()
                // Allow Swagger UI
                .antMatchers(
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()
                // All other requests need authentication
                .anyRequest().authenticated()
                .and()
                // OAuth2 login with forced redirect
                .oauth2Login()
                .defaultSuccessUrl("/loginSuccess", true)
                .and()
                // H2 console config
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
