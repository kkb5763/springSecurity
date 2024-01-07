package com.kbkang.config.interceptor;

import com.kbkang.config.AuthorizationExtractor;
import com.kbkang.config.jwt.provider.JwtProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private JwtProvider jwtProvider;

    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtProvider jwtTokenProvider) {
        this.authExtractor = authExtractor;
        this.jwtProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        System.out.println(">>> interceptor.preHandle 호출");
        String token = authExtractor.extract(request, "Bearer");
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        if (!jwtProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰");
        }

        String name = jwtProvider.getSubject(token);
        request.setAttribute("name", name);
        return true;
    }
}