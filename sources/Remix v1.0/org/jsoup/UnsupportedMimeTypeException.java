package org.jsoup;

import java.io.*;

public class UnsupportedMimeTypeException extends IOException
{
    private String mimeType;
    private String url;
    
    public UnsupportedMimeTypeException(final String message, final String mimeType, final String url) {
        super(message);
        this.mimeType = mimeType;
        this.url = url;
    }
    
    public String getMimeType() {
        return this.mimeType;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    @Override
    public String toString() {
        return super.toString() + ". Mimetype=" + this.mimeType + ", URL=" + this.url;
    }
}
