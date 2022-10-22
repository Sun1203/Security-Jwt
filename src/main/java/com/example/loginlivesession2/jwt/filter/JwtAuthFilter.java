package com.example.loginlivesession2.jwt.filter;

import com.example.loginlivesession2.global.dto.GlobalResDto;
import com.example.loginlivesession2.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1 . 요청이 들어오면 이곳에 무조건 한번은 들어오는가 ? yes
        // 2 . 그러면 요청이 들어왔을때 Acess토큰이 만료되고 Refresh토큰값이 아직 살아있을떼
//             Refresh토큰값으로 Acess토큰값을 다시 할당해주면 안될까 ?
        // 3. 2번이 가능하다면 매번 인증이 필요한 요청을 받을때 마다 Acess토큰과 Refresh토큰값을 같이 받아야 하는가 ?
        // 4. 그러면 프론트단에서는 cookie저장소에 Access 토큰값만 저장하고 Refresh토큰값은 어떻게 처리해야할까?
        //    Refresh토큰을 cookie저장소에 저장하기엔 너무 위험하지 않을까?
        // Refresh토큰을 cookie저장소에 저장하지 않는다면 어떻게 프론트 단에서 요청할때 보내줄 것인가..?


        String accessToken = jwtUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtUtil.getHeaderToken(request, "Refresh");

        if(accessToken != null) {
            // 어세스 토큰값이 유효하다면 setAuthentication를 통해
            // security context에 인증 정보저장
            if(jwtUtil.tokenValidation(accessToken)){
                setAuthentication(jwtUtil.getEmailFromToken(accessToken));
            }
            // 어세스 토큰이 만료된 상황 | 리프레시 토큰 또한 존재하는 상황
            else if (!jwtUtil.tokenValidation(accessToken) && refreshToken != null) {
                // 리프레시 토큰 검증 && 리프레시 토큰 DB에서  토큰 존재유무 확인
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
                // 리프레시 토큰이 유효하고 리프레시 토큰이 DB와 비교했을때 똑같다면
                if (isRefreshToken) {
                    // 리프레시 토큰으로 아이디 정보 가져오기
                    String loginId = jwtUtil.getEmailFromToken(refreshToken);
                    // 새로운 어세스 토큰 발급
                    String newAccessToken = jwtUtil.createToken(loginId, "Access");
                    // 헤더에 어세스 토큰 추가
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);
                    // Security context에 인증 정보 넣기
                    setAuthentication(jwtUtil.getEmailFromToken(newAccessToken));
                }
                // 리프레시 토큰이 만료 || 리프레시 토큰이 DB와 비교했을때 똑같지 않다면
                else {
                    jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST);
                    return;
                }
            }
        }

        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResDto(msg, status.value()));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
