package com.mpatric.mp3agic;

public class NoSuchTagException extends BaseException
{
    private static final long serialVersionUID = 1L;
    
    public NoSuchTagException() {
    }
    
    public NoSuchTagException(final String s) {
        super(s);
    }
    
    public NoSuchTagException(final String s, final Throwable t) {
        super(s, t);
    }
}
