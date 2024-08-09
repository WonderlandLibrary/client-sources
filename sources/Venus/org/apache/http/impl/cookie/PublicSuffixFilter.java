/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Deprecated
public class PublicSuffixFilter
implements CookieAttributeHandler {
    private final CookieAttributeHandler wrapped;
    private Collection<String> exceptions;
    private Collection<String> suffixes;
    private PublicSuffixMatcher matcher;

    public PublicSuffixFilter(CookieAttributeHandler cookieAttributeHandler) {
        this.wrapped = cookieAttributeHandler;
    }

    public void setPublicSuffixes(Collection<String> collection) {
        this.suffixes = collection;
        this.matcher = null;
    }

    public void setExceptions(Collection<String> collection) {
        this.exceptions = collection;
        this.matcher = null;
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (this.isForPublicSuffix(cookie)) {
            return true;
        }
        return this.wrapped.match(cookie, cookieOrigin);
    }

    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        this.wrapped.parse(setCookie, string);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        this.wrapped.validate(cookie, cookieOrigin);
    }

    private boolean isForPublicSuffix(Cookie cookie) {
        if (this.matcher == null) {
            this.matcher = new PublicSuffixMatcher(this.suffixes, this.exceptions);
        }
        return this.matcher.matches(cookie.getDomain());
    }
}

