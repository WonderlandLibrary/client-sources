/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.cookie.CookieUtil;

public abstract class CookieEncoder {
    protected final boolean strict;

    protected CookieEncoder(boolean bl) {
        this.strict = bl;
    }

    protected void validateCookie(String string, String string2) {
        if (this.strict) {
            int n = CookieUtil.firstInvalidCookieNameOctet(string);
            if (n >= 0) {
                throw new IllegalArgumentException("Cookie name contains an invalid char: " + string.charAt(n));
            }
            CharSequence charSequence = CookieUtil.unwrapValue(string2);
            if (charSequence == null) {
                throw new IllegalArgumentException("Cookie value wrapping quotes are not balanced: " + string2);
            }
            n = CookieUtil.firstInvalidCookieValueOctet(charSequence);
            if (n >= 0) {
                throw new IllegalArgumentException("Cookie value contains an invalid char: " + string2.charAt(n));
            }
        }
    }
}

