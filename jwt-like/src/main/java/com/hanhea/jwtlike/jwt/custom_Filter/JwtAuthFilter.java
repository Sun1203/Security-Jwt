package com.hanhea.jwtlike.jwt.custom_Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanhea.jwtlike.account.dto.Response.CommonResponseDto;
import com.hanhea.jwtlike.jwt.jwt_utils.JWTUtil;
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
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader("Access_Token");
        if(accessToken != null) {
            if (jwtUtil.tokenValidation(accessToken)) {//완전 정상, 만료기간지남
                String nickname = jwtUtil.getNicknameFromToken(accessToken);//이름빼내기
                if (jwtUtil.rftokenvalid(nickname)) {//refresh기간이 아직 남아 있다면
                    response.setHeader("Access_Token", jwtUtil.createAllToken(nickname));
                }
                setAuthentication(nickname);
            } else jwtExceptionHandler(response, "재로그인 하세요", HttpStatus.BAD_REQUEST);
        }
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String nickname) {
        Authentication authentication = jwtUtil.createAuthentication(nickname);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status){
        response.setStatus(status.value());
        response.setContentType("application/json");
        try{
            String json = new ObjectMapper().writeValueAsString(new CommonResponseDto("success", "", status.value()));
            response.getWriter().write(json);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
