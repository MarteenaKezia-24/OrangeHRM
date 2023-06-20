package com.disney.commons;

public class InvalidPageException extends RuntimeException
{
    public InvalidPageException(String s)
    {
        super(s);
    }
}
