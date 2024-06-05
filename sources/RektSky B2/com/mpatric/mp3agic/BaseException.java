package com.mpatric.mp3agic;

public class BaseException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public BaseException() {
    }
    
    public BaseException(final String s) {
        super(s);
    }
    
    public BaseException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public String getDetailedMessage() {
        Throwable cause = this;
        final StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append('[');
            sb.append(((BaseException)cause).getClass().getName());
            if (cause.getMessage() != null && cause.getMessage().length() > 0) {
                sb.append(": ");
                sb.append(cause.getMessage());
            }
            sb.append(']');
            cause = cause.getCause();
            if (cause == null) {
                break;
            }
            sb.append(" caused by ");
        }
        return sb.toString();
    }
}
