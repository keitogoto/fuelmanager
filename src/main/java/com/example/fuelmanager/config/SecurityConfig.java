package com.example.fuelmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/login", "/register").permitAll() // 誰でもアクセス可能
						.requestMatchers("/vehicle/register", "/home").authenticated() // ログイン必須
				)
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/home", true) // ログイン成功後に /home へリダイレクト
						.failureUrl("/login?error") // ログイン失敗時のリダイレクト先
						.permitAll())
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.permitAll())
				.rememberMe(rememberMe -> rememberMe
						.key("uniqueAndSecret") // ここを変更すれば記憶情報がリセットされる
						.tokenValiditySeconds(86400) // 1日間有効
				)
				.sessionManagement(session -> session
						.sessionFixation().migrateSession() // セッションフィクセーション対策
						.maximumSessions(1) // 同時ログイン制限
						.expiredUrl("/login?expired") // セッション切れ時のリダイレクト先
				)
				.csrf(csrf -> csrf.disable()); // CSRF 無効化（※ 本番環境では注意）

		return http.build();
	}
}
