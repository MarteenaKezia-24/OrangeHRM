package com.disney.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.disney.utilities.managers.AuthPropertiesManager;
import com.disney.utilities.managers.ExternalPropertiesManager;

import static com.disney.steps.RunnerTest.environment;

import java.util.List;

/**
 * Created by Disney on 3/31/17.
 */
public class AuthConfig extends WebConfig
{
    private static Logger logger = LoggerFactory.getLogger(AuthConfig.class);

    @Bean
    public AuthPropertiesManager provideAuthProperties()
    {
        return new AuthPropertiesManager();
    }
    
    /*@Bean
    public List<String> provideCollection()
    {
    	 return provideExternalProperties().getExternalCollectionProperty();
    }*/
}
