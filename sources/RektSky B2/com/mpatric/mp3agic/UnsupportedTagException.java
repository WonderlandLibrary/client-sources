package com.mpatric.mp3agic;

public class UnsupportedTagException extends BaseException
{
    private static final long serialVersionUID = 1L;
    
    public UnsupportedTagException() {
    }
    
    public UnsupportedTagException(final String s) {
        super(s);
    }
    
    public UnsupportedTagException(final String s, final Throwable t) {
        super(s, t);
    }
}
