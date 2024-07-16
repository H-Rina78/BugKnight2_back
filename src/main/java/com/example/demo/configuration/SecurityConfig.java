package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.formLogin(login -> login //  フォーム認証を使う
	            .permitAll()) //  フォーム認証画面は認証不要
	            .authorizeHttpRequests(authz -> authz
	                .requestMatchers("/css/**").permitAll() // CSSファイルは認証不要
	                .requestMatchers("/search/**").permitAll() // CSSファイルは認証不要
	                .requestMatchers("/").permitAll() //  トップページは認証不要
	                .anyRequest().authenticated() //  他のURLはログイン後アクセス可能
	            );
	    return http.build();
	}
}
