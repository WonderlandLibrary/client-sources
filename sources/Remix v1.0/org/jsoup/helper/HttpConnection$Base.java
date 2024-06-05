package org.jsoup.helper;

import org.jsoup.*;
import java.net.*;
import java.io.*;
import org.jsoup.internal.*;
import java.util.*;

private abstract static class Base<T extends Connection.Base> implements Connection.Base<T>
{
    URL url;
    Method method;
    Map<String, String> headers;
    Map<String, String> cookies;
    
    private Base() {
        this.headers = new LinkedHashMap<String, String>();
        this.cookies = new LinkedHashMap<String, String>();
    }
    
    public URL url() {
        return this.url;
    }
    
    public T url(final URL url) {
        Validate.notNull(url, "URL must not be null");
        this.url = url;
        return (T)this;
    }
    
    public Method method() {
        return this.method;
    }
    
    public T method(final Method method) {
        Validate.notNull(method, "Method must not be null");
        this.method = method;
        return (T)this;
    }
    
    public String header(final String name) {
        Validate.notNull(name, "Header name must not be null");
        String val = this.getHeaderCaseInsensitive(name);
        if (val != null) {
            val = fixHeaderEncoding(val);
        }
        return val;
    }
    
    private static String fixHeaderEncoding(final String val) {
        try {
            final byte[] bytes = val.getBytes("ISO-8859-1");
            if (!looksLikeUtf8(bytes)) {
                return val;
            }
            return new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return val;
        }
    }
    
    private static boolean looksLikeUtf8(final byte[] input) {
        int i = 0;
        if (input.length >= 3 && (input[0] & 0xFF) == 0xEF && ((input[1] & 0xFF) == 0xBB & (input[2] & 0xFF) == 0xBF)) {
            i = 3;
        }
        for (int j = input.length; i < j; ++i) {
            int o = input[i];
            if ((o & 0x80) != 0x0) {
                int end;
                if ((o & 0xE0) == 0xC0) {
                    end = i + 1;
                }
                else if ((o & 0xF0) == 0xE0) {
                    end = i + 2;
                }
                else {
                    if ((o & 0xF8) != 0xF0) {
                        return false;
                    }
                    end = i + 3;
                }
                while (i < end) {
                    ++i;
                    o = input[i];
                    if ((o & 0xC0) != 0x80) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public T header(final String name, final String value) {
        Validate.notEmpty(name, "Header name must not be empty");
        Validate.notNull(value, "Header value must not be null");
        this.removeHeader(name);
        this.headers.put(name, value);
        return (T)this;
    }
    
    public boolean hasHeader(final String name) {
        Validate.notEmpty(name, "Header name must not be empty");
        return this.getHeaderCaseInsensitive(name) != null;
    }
    
    public boolean hasHeaderWithValue(final String name, final String value) {
        return this.hasHeader(name) && this.header(name).equalsIgnoreCase(value);
    }
    
    public T removeHeader(final String name) {
        Validate.notEmpty(name, "Header name must not be empty");
        final Map.Entry<String, String> entry = this.scanHeaders(name);
        if (entry != null) {
            this.headers.remove(entry.getKey());
        }
        return (T)this;
    }
    
    public Map<String, String> headers() {
        return this.headers;
    }
    
    private String getHeaderCaseInsensitive(final String name) {
        Validate.notNull(name, "Header name must not be null");
        String value = this.headers.get(name);
        if (value == null) {
            value = this.headers.get(Normalizer.lowerCase(name));
        }
        if (value == null) {
            final Map.Entry<String, String> entry = this.scanHeaders(name);
            if (entry != null) {
                value = entry.getValue();
            }
        }
        return value;
    }
    
    private Map.Entry<String, String> scanHeaders(final String name) {
        final String lc = Normalizer.lowerCase(name);
        for (final Map.Entry<String, String> entry : this.headers.entrySet()) {
            if (Normalizer.lowerCase(entry.getKey()).equals(lc)) {
                return entry;
            }
        }
        return null;
    }
    
    public String cookie(final String name) {
        Validate.notEmpty(name, "Cookie name must not be empty");
        return this.cookies.get(name);
    }
    
    public T cookie(final String name, final String value) {
        Validate.notEmpty(name, "Cookie name must not be empty");
        Validate.notNull(value, "Cookie value must not be null");
        this.cookies.put(name, value);
        return (T)this;
    }
    
    public boolean hasCookie(final String name) {
        Validate.notEmpty(name, "Cookie name must not be empty");
        return this.cookies.containsKey(name);
    }
    
    public T removeCookie(final String name) {
        Validate.notEmpty(name, "Cookie name must not be empty");
        this.cookies.remove(name);
        return (T)this;
    }
    
    public Map<String, String> cookies() {
        return this.cookies;
    }
}
