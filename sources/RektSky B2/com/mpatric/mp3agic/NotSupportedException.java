package com.mpatric.mp3agic;

public class NotSupportedException extends BaseException
{
    private static final long serialVersionUID = 1L;
    
    public NotSupportedException() {
    }
    
    public NotSupportedException(final String s) {
        super(s);
    }
    
    public NotSupportedException(final String s, final Throwable t) {
        super(s, t);
    }
}
