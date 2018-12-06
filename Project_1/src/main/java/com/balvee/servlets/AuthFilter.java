package com.balvee.servlets;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {

    // Override

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

        if(authHeader == null) {
            res.setStatus(401);
            return;
        } else {
            // Bearer token
            String token = authHeader.substring("Bearer ".length());
            Claims claims = Jwts.parser()
                    .setSigningKey("mySecret".getBytes("UTF-8"))
                    .parseClaimsJws(token).getBody();

        }


        chain.doFilter(request, response);
    } // doFilter

    @Override
    public void destroy() {

    }

} // AuthFilter