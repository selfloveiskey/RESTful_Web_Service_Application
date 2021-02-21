package com.restful.restful_application.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    /*
    |------------------------------------------------------------
    | Access request header
    | Check if header has the Bearer TOKEN_PREFIX
    | ---------
    | Override method because it comes from the spring framework
    |------------------------------------------------------------
    */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        /*
        |--------------------------------------------------------------------------
        | If condition is satisfied, continue to next filter we have in the chain
        |--------------------------------------------------------------------------
        */
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        /*
        |---------------------------------------------------------------
        | If the token is there and it starts with Bearer TOKEN_PREFIX
        | then get password authentication token object
        |---------------------------------------------------------------
        */
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

   /*
   |---------------------------------------------
   | Returns user password authentication token
   |---------------------------------------------
   */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            /*
            |-----------------------------------
            | Removes the Bearer TOKEN_PREFIX
            |-----------------------------------
            */
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

            /*
            |-----------------------------------
            | Decrypt and get user details
            |-----------------------------------
            */
            String user = Jwts.parser()
                    .setSigningKey( SecurityConstants.getTokenSecret() )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }

}