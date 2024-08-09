/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Collections;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.CookieSpecBase;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class IgnoreSpec
extends CookieSpecBase {
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
        return Collections.emptyList();
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        return true;
    }

    @Override
    public List<Header> formatCookies(List<Cookie> list) {
        return Collections.emptyList();
    }

    @Override
    public Header getVersionHeader() {
        return null;
    }
}

