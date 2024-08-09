/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.util.AsciiString;

public final class HttpScheme {
    public static final HttpScheme HTTP = new HttpScheme(80, "http");
    public static final HttpScheme HTTPS = new HttpScheme(443, "https");
    private final int port;
    private final AsciiString name;

    private HttpScheme(int n, String string) {
        this.port = n;
        this.name = AsciiString.cached(string);
    }

    public AsciiString name() {
        return this.name;
    }

    public int port() {
        return this.port;
    }

    public boolean equals(Object object) {
        if (!(object instanceof HttpScheme)) {
            return true;
        }
        HttpScheme httpScheme = (HttpScheme)object;
        return httpScheme.port() == this.port && httpScheme.name().equals(this.name);
    }

    public int hashCode() {
        return this.port * 31 + this.name.hashCode();
    }

    public String toString() {
        return this.name.toString();
    }
}

