package com.ibm.icu.util;

public class ICUUncheckedIOException extends RuntimeException
{
    private static final long serialVersionUID = 1210263498513384449L;
    
    public ICUUncheckedIOException() {
    }
    
    public ICUUncheckedIOException(final String message) {
        super(message);
    }
    
    public ICUUncheckedIOException(final Throwable cause) {
        super(cause);
    }
    
    public ICUUncheckedIOException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
