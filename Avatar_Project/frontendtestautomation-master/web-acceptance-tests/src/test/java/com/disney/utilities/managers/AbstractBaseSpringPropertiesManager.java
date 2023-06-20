package com.disney.utilities.managers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Created by Disney on 3/31/17.
 */
public abstract class AbstractBaseSpringPropertiesManager
{
    @Autowired
    protected Environment env;        
}
