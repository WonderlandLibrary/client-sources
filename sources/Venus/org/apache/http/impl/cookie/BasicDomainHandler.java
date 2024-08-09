/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicDomainHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(string)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        }
        if (string.endsWith(".")) {
            return;
        }
        String string2 = string;
        if (string2.startsWith(".")) {
            string2 = string2.substring(1);
        }
        string2 = string2.toLowerCase(Locale.ROOT);
        setCookie.setDomain(string2);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = cookieOrigin.getHost();
        String string2 = cookie.getDomain();
        if (string2 == null) {
            throw new CookieRestrictionViolationException("Cookie 'domain' may not be null");
        }
        if (!string.equals(string2) && !BasicDomainHandler.domainMatch(string2, string)) {
            throw new CookieRestrictionViolationException("Illegal 'domain' attribute \"" + string2 + "\". Domain of origin: \"" + string + "\"");
        }
    }

    static boolean domainMatch(String string, String string2) {
        String string3;
        if (InetAddressUtils.isIPv4Address(string2) || InetAddressUtils.isIPv6Address(string2)) {
            return true;
        }
        String string4 = string3 = string.startsWith(".") ? string.substring(1) : string;
        if (string2.endsWith(string3)) {
            int n = string2.length() - string3.length();
            if (n == 0) {
                return false;
            }
            if (n > 1 && string2.charAt(n - 1) == '.') {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = cookieOrigin.getHost();
        String string2 = cookie.getDomain();
        if (string2 == null) {
            return true;
        }
        if (string2.startsWith(".")) {
            string2 = string2.substring(1);
        }
        if (string.equals(string2 = string2.toLowerCase(Locale.ROOT))) {
            return false;
        }
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("domain")) {
            return BasicDomainHandler.domainMatch(string2, string);
        }
        return true;
    }

    @Override
    public String getAttributeName() {
        return "domain";
    }
}

