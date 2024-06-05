package com.ibm.icu.util;

public class ICUCloneNotSupportedException extends ICUException
{
    private static final long serialVersionUID = -4824446458488194964L;
    
    public ICUCloneNotSupportedException() {
    }
    
    public ICUCloneNotSupportedException(final String message) {
        super(message);
    }
    
    public ICUCloneNotSupportedException(final Throwable cause) {
        super(cause);
    }
    
    public ICUCloneNotSupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
