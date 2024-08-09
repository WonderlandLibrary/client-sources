/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RFC2965DomainAttributeHandler
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
        String string2 = string;
        string2 = string2.toLowerCase(Locale.ROOT);
        if (!string.startsWith(".")) {
            string2 = '.' + string2;
        }
        setCookie.setDomain(string2);
    }

    public boolean domainMatch(String string, String string2) {
        boolean bl = string.equals(string2) || string2.startsWith(".") && string.endsWith(string2);
        return bl;
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = cookieOrigin.getHost().toLowerCase(Locale.ROOT);
        if (cookie.getDomain() == null) {
            throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
        }
        String string2 = cookie.getDomain().toLowerCase(Locale.ROOT);
        if (cookie instanceof ClientCookie && ((ClientCookie)cookie).containsAttribute("domain")) {
            if (!string2.startsWith(".")) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2109: domain must start with a dot");
            }
            int n = string2.indexOf(46, 1);
            if (!(n >= 0 && n != string2.length() - 1 || string2.equals(".local"))) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: the value contains no embedded dots " + "and the value is not .local");
            }
            if (!this.domainMatch(string, string2)) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: effective host name does not " + "domain-match domain attribute.");
            }
            String string3 = string.substring(0, string.length() - string2.length());
            if (string3.indexOf(46) != -1) {
                throw new CookieRestrictionViolationException("Domain attribute \"" + cookie.getDomain() + "\" violates RFC 2965: " + "effective host minus domain may not contain any dots");
            }
        } else if (!cookie.getDomain().equals(string)) {
            throw new CookieRestrictionViolationException("Illegal domain attribute: \"" + cookie.getDomain() + "\"." + "Domain of origin: \"" + string + "\"");
        }
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String string = cookieOrigin.getHost().toLowerCase(Locale.ROOT);
        String string2 = cookie.getDomain();
        if (!this.domainMatch(string, string2)) {
            return true;
        }
        String string3 = string.substring(0, string.length() - string2.length());
        return string3.indexOf(46) == -1;
    }

    @Override
    public String getAttributeName() {
        return "domain";
    }
}

