package iam.sde.udemyblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    final
    JwtTokenProvider provider;

    final
    CustomUserDetailsService userDetailsService;

    public JwtAuthenticationEntryPoint(JwtTokenProvider provider, CustomUserDetailsService userDetailsService) {
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    }
}
