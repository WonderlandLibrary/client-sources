/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.cookie.Cookie;

@Deprecated
public final class ClientCookieEncoder {
    @Deprecated
    public static String encode(String string, String string2) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(string, string2);
    }

    @Deprecated
    public static String encode(io.netty.handler.codec.http.Cookie cookie) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode((Cookie)cookie);
    }

    @Deprecated
    public static String encode(io.netty.handler.codec.http.Cookie ... cookieArray) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(cookieArray);
    }

    @Deprecated
    public static String encode(Iterable<io.netty.handler.codec.http.Cookie> iterable) {
        return io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(iterable);
    }

    private ClientCookieEncoder() {
    }
}

