package org.jsoup.helper;

import org.jsoup.*;
import java.io.*;

public static class KeyVal implements Connection.KeyVal
{
    private String key;
    private String value;
    private InputStream stream;
    
    public static KeyVal create(final String key, final String value) {
        return new KeyVal().key(key).value(value);
    }
    
    public static KeyVal create(final String key, final String filename, final InputStream stream) {
        return new KeyVal().key(key).value(filename).inputStream(stream);
    }
    
    private KeyVal() {
    }
    
    public KeyVal key(final String key) {
        Validate.notEmpty(key, "Data key must not be empty");
        this.key = key;
        return this;
    }
    
    public String key() {
        return this.key;
    }
    
    public KeyVal value(final String value) {
        Validate.notNull(value, "Data value must not be null");
        this.value = value;
        return this;
    }
    
    public String value() {
        return this.value;
    }
    
    public KeyVal inputStream(final InputStream inputStream) {
        Validate.notNull(this.value, "Data input stream must not be null");
        this.stream = inputStream;
        return this;
    }
    
    public InputStream inputStream() {
        return this.stream;
    }
    
    public boolean hasInputStream() {
        return this.stream != null;
    }
    
    @Override
    public String toString() {
        return this.key + "=" + this.value;
    }
}
