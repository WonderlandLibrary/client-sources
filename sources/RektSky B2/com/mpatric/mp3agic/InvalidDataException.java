package com.mpatric.mp3agic;

public class InvalidDataException extends BaseException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidDataException() {
    }
    
    public InvalidDataException(final String s) {
        super(s);
    }
    
    public InvalidDataException(final String s, final Throwable t) {
        super(s, t);
    }
}
