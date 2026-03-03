package com.example.stock.config;

import com.example.stock.jwt.JwtAuthenticationFilter;
import com.example.stock.jwt.JwtConfig;
import com.example.stock.jwt.JwtUtil;
import com.example.stock.jwt.LoginFilter;
import com.example.stock.service.CustomUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    private final JwtConfig jwtConfig;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        // CustomUserDetailService를 UserDetailsService 타입의 인자로 전달합니다.
        return new JwtAuthenticationFilter(jwtUtil, customUserDetailService);
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors(withDefaults());

        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 이 설정은 STATELESS로 유지해야 합니다.
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**", "/static/**","/uploads/**").permitAll()
                        .requestMatchers("/chart/**", "/api/chart/**","/chart/basic_materials").permitAll()
                        .requestMatchers("/login", "/", "/join", "/main","/reset-password","/loginP","/send-verification-code", "/verify-code").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user","/technology1","/auth/logout","/mypage").hasRole("USER")
                        .anyRequest().authenticated()
                );


        http
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), objectMapper, jwtUtil, jwtConfig), UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/main") // 로그아웃 성공 시 이동할 페이지
                .deleteCookies("session_id")
                .permitAll()
        );
        return http.build();
    }
}
