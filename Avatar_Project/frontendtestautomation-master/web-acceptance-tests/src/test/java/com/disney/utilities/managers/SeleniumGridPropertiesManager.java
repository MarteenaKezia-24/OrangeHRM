package com.disney.utilities.managers;

/**
 * Created by Disney on 3/31/17.
 */
public class SeleniumGridPropertiesManager extends AbstractBaseSpringPropertiesManager
{
    public String provideHubURL()
    {
        return env.getProperty("com.disney.selenium.grid.hub.url");
    }
}
