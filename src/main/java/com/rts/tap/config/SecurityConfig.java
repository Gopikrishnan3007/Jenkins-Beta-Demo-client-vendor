//package com.rts.tap.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.rts.tap.filter.JwtFilter;
//import com.rts.tap.service.MyUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private MyUserDetailsService userDetailsService;
//
//	@Autowired
//	private JwtFilter jwtFilter;
//
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		return http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults()) // Enable CORS
//				.authorizeHttpRequests(requests -> requests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow
//						// requests
//						.requestMatchers("tap/login", "tap/verify-otp", "tap/resend-otp", "tap/updatepassword").permitAll()
//						.requestMatchers("tap/mrfjd/getall", "tap/get-mrf-mrfJdId", "tap/candidates/applyjob",
//								"tap/candidates/send-acknowledgement-mail", "tap/user/password/recovery/send-otp",
//								"tap/user/password/recovery/forgot-password-verify-otp",
//								"tap/user/password/recovery/forgot-password-update").permitAll()
//						 .requestMatchers("/tap/zego/createMeeting", "/tap/zego/internal/createMeeting").permitAll() // Public endpoints
//
//						.anyRequest().authenticated() // All other endpoints require authentication
//				).httpBasic(Customizer.withDefaults())
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
//	}
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//		provider.setUserDetailsService(userDetailsService);
//		return provider;
//	}
//
//	// Bean for Bcrypt
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//
//}
