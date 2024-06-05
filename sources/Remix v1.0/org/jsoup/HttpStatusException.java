package org.jsoup;

import java.io.*;

public class HttpStatusException extends IOException
{
    private int statusCode;
    private String url;
    
    public HttpStatusException(final String message, final int statusCode, final String url) {
        super(message);
        this.statusCode = statusCode;
        this.url = url;
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public String toString() {
        return super.toString() + ". Status=" + this.statusCode + ", URL=" + this.url;
    }
}
