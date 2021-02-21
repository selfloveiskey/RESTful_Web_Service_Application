package com.restful.restful_application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.restful_application.SpringApplicationContext;
import com.restful.restful_application.service.UserService;
import com.restful.restful_application.shared.dto.UserDto;
import com.restful.restful_application.ui.model.request.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/*
|------------------------------------------------------------------------
| Used when HTTP request is sent for a user to sign into the application
|------------------------------------------------------------------------
*/
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private String contentType;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /*
    |-----------------------------------------------------------------------------------------------
    | When the webservice receives a request to authenticate the user
    | Spring framework will authenticate the user using the username and password provided at login
    |------------------------------------------------------------------------------------------------
    */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            contentType = req.getHeader("Accept");

            UserLoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequestModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    |------------------------------------------------------------------------
    | Spring framework will call this method if authentication is successful
    |------------------------------------------------------------------------
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((User) auth.getPrincipal()).getUsername();

        String token = Jwts.builder()
                                    .setSubject(userName)
                                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                                    .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                                    .compact();

        UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImplementation");
        UserDto userDto = userService.getUser(userName);

        /*
        |--------------------------------------------------------------------------------------
        | The auto generated access token (unique) will be used along with the UserID
        | in all other requests to authenticate the user before allowing them to modify account
        |--------------------------------------------------------------------------------------
        */
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("UserID", userDto.getUserId());
    }
}