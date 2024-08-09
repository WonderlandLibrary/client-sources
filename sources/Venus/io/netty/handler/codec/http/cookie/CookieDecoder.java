/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.cookie.CookieUtil;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.CharBuffer;

public abstract class CookieDecoder {
    private final InternalLogger logger = InternalLoggerFactory.getInstance(this.getClass());
    private final boolean strict;

    protected CookieDecoder(boolean bl) {
        this.strict = bl;
    }

    protected DefaultCookie initCookie(String string, int n, int n2, int n3, int n4) {
        boolean bl;
        int n5;
        if (n == -1 || n == n2) {
            this.logger.debug("Skipping cookie with null name");
            return null;
        }
        if (n3 == -1) {
            this.logger.debug("Skipping cookie with null value");
            return null;
        }
        CharBuffer charBuffer = CharBuffer.wrap(string, n3, n4);
        CharSequence charSequence = CookieUtil.unwrapValue(charBuffer);
        if (charSequence == null) {
            this.logger.debug("Skipping cookie because starting quotes are not properly balanced in '{}'", (Object)charBuffer);
            return null;
        }
        String string2 = string.substring(n, n2);
        if (this.strict && (n5 = CookieUtil.firstInvalidCookieNameOctet(string2)) >= 0) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Skipping cookie because name '{}' contains invalid char '{}'", (Object)string2, (Object)Character.valueOf(string2.charAt(n5)));
            }
            return null;
        }
        boolean bl2 = bl = charSequence.length() != n4 - n3;
        if (this.strict && (n5 = CookieUtil.firstInvalidCookieValueOctet(charSequence)) >= 0) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Skipping cookie because value '{}' contains invalid char '{}'", (Object)charSequence, (Object)Character.valueOf(charSequence.charAt(n5)));
            }
            return null;
        }
        DefaultCookie defaultCookie = new DefaultCookie(string2, charSequence.toString());
        defaultCookie.setWrap(bl);
        return defaultCookie;
    }
}

