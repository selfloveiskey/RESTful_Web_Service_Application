package com.restful.restful_application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Autowired
    private Environment env;

    /*
    |------------------------------------------------------------
    | Returns value we got from the application properties file
    |------------------------------------------------------------
    */
    public String getTokenSecret() {
        return env.getProperty("tokenSecret");
    }
}