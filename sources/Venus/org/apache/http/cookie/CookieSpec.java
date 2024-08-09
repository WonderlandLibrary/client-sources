/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.List;
import org.apache.http.Header;
import org.apache.http.annotation.Obsolete;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;

public interface CookieSpec {
    @Obsolete
    public int getVersion();

    public List<Cookie> parse(Header var1, CookieOrigin var2) throws MalformedCookieException;

    public void validate(Cookie var1, CookieOrigin var2) throws MalformedCookieException;

    public boolean match(Cookie var1, CookieOrigin var2);

    public List<Header> formatCookies(List<Cookie> var1);

    @Obsolete
    public Header getVersionHeader();
}

