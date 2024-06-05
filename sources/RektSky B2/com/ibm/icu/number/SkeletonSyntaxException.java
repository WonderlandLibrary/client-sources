package com.ibm.icu.number;

public class SkeletonSyntaxException extends IllegalArgumentException
{
    private static final long serialVersionUID = 7733971331648360554L;
    
    public SkeletonSyntaxException(final String message, final CharSequence token) {
        super("Syntax error in skeleton string: " + message + ": " + (Object)token);
    }
    
    public SkeletonSyntaxException(final String message, final CharSequence token, final Throwable cause) {
        super("Syntax error in skeleton string: " + message + ": " + (Object)token, cause);
    }
}
