package com.disney.utilities.managers;

/**
 * Created by Disney on 3/31/17.
 */
public class EnvironmentPropertiesManager extends AbstractBaseSpringPropertiesManager
{
     public String provideQaURL()
    {
        return env.getProperty("com.disney.matterhorn.fe.disney_india.web.env.qa");
    }
}
