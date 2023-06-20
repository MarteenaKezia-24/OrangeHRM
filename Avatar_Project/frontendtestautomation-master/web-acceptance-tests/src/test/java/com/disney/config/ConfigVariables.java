package com.disney.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ResourceLoader;

@Configuration
@PropertySources({
        @PropertySource(value = { "classpath:/com/disney/properties/web_elements.properties"}), 
        @PropertySource(value = { "classpath:/com/disney/properties/selenium_grid.properties"})
})
public class ConfigVariables
{
    @Autowired
    public ResourceLoader resourceLoader;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
/*    @Value("${com.disney.matterhorn.fe.disney_india.web.env.qa}")
    public String qaUrl;*/

    @Value("${com.disney.selenium.grid.hub.url}")
    public String hubUrl;
}
