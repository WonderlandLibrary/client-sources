/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.BasicDomainHandler;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class NetscapeDomainHandler
extends BasicDomainHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(string)) {
            throw new MalformedCookieException("Blank or null value for domain attribute");
        }
        setCookie.setDomain(string);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        String string;
        String string2 = cookieOrigin.getHost();
        if (!string2.equals(string = cookie.getDomain()) && !BasicDomainHandler.domainMatch(string, string2)) {
            throw new CookieRestrictionViolationException("Illegal domain attribute \"" + string + "\". Domain of origin: \"" + string2 + "\"");
        }
        if (string2.contains(".")) {
            int n = new StringTokenizer(string, ".").countTokens();
            if (NetscapeDomainHandler.isSpecialDomain(string)) {
                if (n < 2) {
                    throw new CookieRestrictionViolationException("Domain attribute \"" + string + "\" violates the Netscape cookie specification for " + "special domains");
                }
            } else if (n < 3) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + string + "\" violates the Netscape cookie specification");
            }
        }
    }

    private static boolean isSpecialDomain(String string) {
        String string2 = string.toUpperCase(Locale.ROOT);
        return string2.endsWith(".COM") || string2.endsWith(".EDU") || string2.endsWith(".NET") || string2.endsWith(".GOV") || string2.endsWith(".MIL") || string2.endsWith(".ORG") || string2.endsWith(".INT");
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
        return string.endsWith(string2);
    }

    @Override
    public String getAttributeName() {
        return "domain";
    }
}

