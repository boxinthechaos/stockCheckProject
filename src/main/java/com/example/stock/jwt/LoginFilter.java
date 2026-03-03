    package com.example.stock.jwt;

    import com.example.stock.dto.JoinDTO;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
    import jakarta.servlet.http.Cookie;
    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;

    public class LoginFilter extends UsernamePasswordAuthenticationFilter {
        private final AuthenticationManager authenticationManager;
        private final ObjectMapper objectMapper;
        private final JwtUtil jwtUtil;
        private final JwtConfig jwtConfig;


        public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, JwtUtil jwtUtil, JwtConfig jwtConfig) {
            this.authenticationManager = authenticationManager;
            this.objectMapper = objectMapper;
            this.jwtUtil = jwtUtil;
            this.jwtConfig = jwtConfig; // jwtConfig 초기화
            setFilterProcessesUrl("/login");
        }


        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            try {
                if ("application/json".equals(request.getContentType())) {
                    JoinDTO joinDTO = objectMapper.readValue(request.getInputStream(), JoinDTO.class);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(joinDTO.getUsername(), joinDTO.getPassword());
                    return authenticationManager.authenticate(authToken);
                } else {
                    // form-data 혹은 쿼리 파라미터 처리
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, password);
                    return authenticationManager.authenticate(authToken);
                }
            } catch (IOException e) {
                throw new RuntimeException("로그인 요청 파싱 실패", e);
            }
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
            String token = jwtUtil.generateToken(authentication);

            Cookie jwtCookie = new Cookie("session_id", token);
            jwtCookie.setPath("/");
            jwtCookie.setHttpOnly(true);
            jwtCookie.setMaxAge((int) (jwtConfig.getExpirationTime() / 1000));
            response.addCookie(jwtCookie);

            // ✅ 로그인 성공 후 메인 페이지로 리다이렉트
            response.sendRedirect("/main");

            // ✅ 아래 JSON 응답은 사용하지 않음 (브라우저는 JSON을 처리 못함)
        /*
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        Map<String,String> responseMap  = new HashMap<>();
        responseMap.put("message","로그인 성공");
        String result = objectMapper.writeValueAsString(responseMap);
        response.getWriter().write(result);
        */
        }
        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException fail) throws IOException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendRedirect("/loginP?error=true");
        }
    }
