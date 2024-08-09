/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicPathHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        setCookie.setPath(!TextUtils.isBlank(string) ? string : "/");
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
    }

    static boolean pathMatch(String string, String string2) {
        String string3 = string2;
        if (string3 == null) {
            string3 = "/";
        }
        if (string3.length() > 1 && string3.endsWith("/")) {
            string3 = string3.substring(0, string3.length() - 1);
        }
        if (string.startsWith(string3)) {
            if (string3.equals("/")) {
                return false;
            }
            if (string.length() == string3.length()) {
                return false;
            }
            if (string.charAt(string3.length()) == '/') {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        return BasicPathHandler.pathMatch(cookieOrigin.getPath(), cookie.getPath());
    }

    @Override
    public String getAttributeName() {
        return "path";
    }
}

