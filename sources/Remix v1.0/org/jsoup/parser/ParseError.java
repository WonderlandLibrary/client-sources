package org.jsoup.parser;

public class ParseError
{
    private int pos;
    private String errorMsg;
    
    ParseError(final int pos, final String errorMsg) {
        this.pos = pos;
        this.errorMsg = errorMsg;
    }
    
    ParseError(final int pos, final String errorFormat, final Object... args) {
        this.errorMsg = String.format(errorFormat, args);
        this.pos = pos;
    }
    
    public String getErrorMessage() {
        return this.errorMsg;
    }
    
    public int getPosition() {
        return this.pos;
    }
    
    @Override
    public String toString() {
        return this.pos + ": " + this.errorMsg;
    }
}
