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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class QueryStringEncoder {
    private static final Pattern PATTERN = Pattern.compile("+", 16);
    private final Charset charset;
    private final String uri;
    private final List<Param> params = new ArrayList<Param>();

    public QueryStringEncoder(String uri) {
        this(uri, HttpConstants.DEFAULT_CHARSET);
    }

    public QueryStringEncoder(String uri, Charset charset) {
        this.uri = ObjectUtil.checkNotNull(uri, "uri");
        this.charset = ObjectUtil.checkNotNull(charset, "charset");
    }

    public void addParam(String name, String value) {
        ObjectUtil.checkNotNull(name, "name");
        this.params.add(new Param(name, value));
    }

    public URI toUri() throws URISyntaxException {
        return new URI(this.toString());
    }

    public String toString() {
        if (this.params.isEmpty()) {
            return this.uri;
        }
        StringBuilder sb = new StringBuilder(this.uri).append('?');
        for (int i = 0; i < this.params.size(); ++i) {
            Param param = this.params.get(i);
            sb.append(QueryStringEncoder.encodeComponent(param.name, this.charset));
            if (param.value != null) {
                sb.append('=');
                sb.append(QueryStringEncoder.encodeComponent(param.value, this.charset));
            }
            if (i == this.params.size() - 1) continue;
            sb.append('&');
        }
        return sb.toString();
    }

    private static String encodeComponent(String s, Charset charset) {
        try {
            return URLEncoder.encode(s, PATTERN.matcher(charset.name()).replaceAll("%20"));
        } catch (UnsupportedEncodingException ignored) {
            throw new UnsupportedCharsetException(charset.name());
        }
    }

    private static final class Param {
        final String name;
        final String value;

        Param(String name, String value) {
            this.value = value;
            this.name = name;
        }
    }
}

