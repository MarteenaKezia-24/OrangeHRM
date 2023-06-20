package com.disney.exceptions;

/**
 * Created by Disney on 1/30/18.
 */
public class EventNotFoundException extends Exception
{
    public EventNotFoundException(String message)
    {
        super(message);
    }
}