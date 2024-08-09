/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RFC2109DomainHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (string == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        }
        if (string.trim().isEmpty()) {
            throw new MalformedCookieException("Blank value for domain attribute");
        }
        setCookie.setDomain(string);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = cookieOrigin.getHost();
        String string2 = cookie.getDomain();
        if (string2 == null) {
            throw new CookieRestrictionViolationException("Cookie domain may not be null");
        }
        if (!string2.equals(string)) {
            int n = string2.indexOf(46);
            if (n == -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + string2 + "\" does not match the host \"" + string + "\"");
            }
            if (!string2.startsWith(".")) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + string2 + "\" violates RFC 2109: domain must start with a dot");
            }
            n = string2.indexOf(46, 1);
            if (n < 0 || n == string2.length() - 1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + string2 + "\" violates RFC 2109: domain must contain an embedded dot");
            }
            if (!(string = string.toLowerCase(Locale.ROOT)).endsWith(string2)) {
                throw new CookieRestrictionViolationException("Illegal domain attribute \"" + string2 + "\". Domain of origin: \"" + string + "\"");
            }
            String string3 = string.substring(0, string.length() - string2.length());
            if (string3.indexOf(46) != -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + string2 + "\" violates RFC 2109: host minus domain may not contain any dots");
            }
        }
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
        return string.equals(string2) || string2.startsWith(".") && string.endsWith(string2);
    }

    @Override
    public String getAttributeName() {
        return "domain";
    }
}

