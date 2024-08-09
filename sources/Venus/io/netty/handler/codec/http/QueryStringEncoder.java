/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.ObjectUtil;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class QueryStringEncoder {
    private final String charsetName;
    private final StringBuilder uriBuilder;
    private boolean hasParams;

    public QueryStringEncoder(String string) {
        this(string, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringEncoder(String string, Charset charset) {
        this.uriBuilder = new StringBuilder(string);
        this.charsetName = charset.name();
    }

    public void addParam(String string, String string2) {
        ObjectUtil.checkNotNull(string, "name");
        if (this.hasParams) {
            this.uriBuilder.append('&');
        } else {
            this.uriBuilder.append('?');
            this.hasParams = true;
        }
        QueryStringEncoder.appendComponent(string, this.charsetName, this.uriBuilder);
        if (string2 != null) {
            this.uriBuilder.append('=');
            QueryStringEncoder.appendComponent(string2, this.charsetName, this.uriBuilder);
        }
    }

    public URI toUri() throws URISyntaxException {
        return new URI(this.toString());
    }

    public String toString() {
        return this.uriBuilder.toString();
    }

    private static void appendComponent(String string, String string2, StringBuilder stringBuilder) {
        try {
            string = URLEncoder.encode(string, string2);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new UnsupportedCharsetException(string2);
        }
        int n = string.indexOf(43);
        if (n == -1) {
            stringBuilder.append(string);
            return;
        }
        stringBuilder.append(string, 0, n).append("%20");
        int n2 = string.length();
        ++n;
        while (n < n2) {
            char c = string.charAt(n);
            if (c != '+') {
                stringBuilder.append(c);
            } else {
                stringBuilder.append("%20");
            }
            ++n;
        }
    }
}

