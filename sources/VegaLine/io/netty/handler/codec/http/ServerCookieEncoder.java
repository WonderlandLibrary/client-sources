/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.Cookie;
import java.util.Collection;
import java.util.List;

@Deprecated
public final class ServerCookieEncoder {
    @Deprecated
    public static String encode(String name, String value) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(name, value);
    }

    @Deprecated
    public static String encode(io.netty.handler.codec.http.Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode((Cookie)cookie);
    }

    @Deprecated
    public static List<String> encode(io.netty.handler.codec.http.Cookie ... cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }

    @Deprecated
    public static List<String> encode(Collection<io.netty.handler.codec.http.Cookie> cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }

    @Deprecated
    public static List<String> encode(Iterable<io.netty.handler.codec.http.Cookie> cookies) {
        return io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookies);
    }

    private ServerCookieEncoder() {
    }
}

