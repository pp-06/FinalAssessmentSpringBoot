package com.example.springbootecommerce.configuration;

import com.example.springbootecommerce.service.TokenService;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtFilter extends GenericFilterBean {
    private TokenService tokenService;
    public JwtFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        String token = httpServletRequest.getHeader("Authorization");
        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.sendError(HttpServletResponse.SC_OK,"success");
            return ;
        }

        //specific APIs without token
        if(allowRequestWithoutToken(httpServletRequest)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK, "success");
            filterChain.doFilter(req,res);
        }
        else {
            ObjectId userId = new ObjectId(tokenService.getUserIdToken(token));
            httpServletRequest.setAttribute("userId", userId);
            filterChain.doFilter(req,res);
        }
    }

    public boolean allowRequestWithoutToken(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRequestURI());
        if(httpServletRequest.getRequestURI().contains("/users"))
            return true;
        return false;
    }
}

