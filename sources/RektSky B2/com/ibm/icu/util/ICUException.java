package com.ibm.icu.util;

public class ICUException extends RuntimeException
{
    private static final long serialVersionUID = -3067399656455755650L;
    
    public ICUException() {
    }
    
    public ICUException(final String message) {
        super(message);
    }
    
    public ICUException(final Throwable cause) {
        super(cause);
    }
    
    public ICUException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
