/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.util.PublicSuffixList;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class PublicSuffixDomainFilter
implements CommonCookieAttributeHandler {
    private final CommonCookieAttributeHandler handler;
    private final PublicSuffixMatcher publicSuffixMatcher;
    private final Map<String, Boolean> localDomainMap;

    private static Map<String, Boolean> createLocalDomainMap() {
        ConcurrentHashMap<String, Boolean> concurrentHashMap = new ConcurrentHashMap<String, Boolean>();
        concurrentHashMap.put(".localhost.", Boolean.TRUE);
        concurrentHashMap.put(".test.", Boolean.TRUE);
        concurrentHashMap.put(".local.", Boolean.TRUE);
        concurrentHashMap.put(".local", Boolean.TRUE);
        concurrentHashMap.put(".localdomain", Boolean.TRUE);
        return concurrentHashMap;
    }

    public PublicSuffixDomainFilter(CommonCookieAttributeHandler commonCookieAttributeHandler, PublicSuffixMatcher publicSuffixMatcher) {
        this.handler = Args.notNull(commonCookieAttributeHandler, "Cookie handler");
        this.publicSuffixMatcher = Args.notNull(publicSuffixMatcher, "Public suffix matcher");
        this.localDomainMap = PublicSuffixDomainFilter.createLocalDomainMap();
    }

    public PublicSuffixDomainFilter(CommonCookieAttributeHandler commonCookieAttributeHandler, PublicSuffixList publicSuffixList) {
        Args.notNull(commonCookieAttributeHandler, "Cookie handler");
        Args.notNull(publicSuffixList, "Public suffix list");
        this.handler = commonCookieAttributeHandler;
        this.publicSuffixMatcher = new PublicSuffixMatcher(publicSuffixList.getRules(), publicSuffixList.getExceptions());
        this.localDomainMap = PublicSuffixDomainFilter.createLocalDomainMap();
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        String string;
        String string2 = cookie.getDomain();
        if (string2 == null) {
            return true;
        }
        int n = string2.indexOf(46);
        if (n >= 0 ? !this.localDomainMap.containsKey(string = string2.substring(n)) && this.publicSuffixMatcher.matches(string2) : !string2.equalsIgnoreCase(cookieOrigin.getHost()) && this.publicSuffixMatcher.matches(string2)) {
            return true;
        }
        return this.handler.match(cookie, cookieOrigin);
    }

    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        this.handler.parse(setCookie, string);
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        this.handler.validate(cookie, cookieOrigin);
    }

    @Override
    public String getAttributeName() {
        return this.handler.getAttributeName();
    }

    public static CommonCookieAttributeHandler decorate(CommonCookieAttributeHandler commonCookieAttributeHandler, PublicSuffixMatcher publicSuffixMatcher) {
        Args.notNull(commonCookieAttributeHandler, "Cookie attribute handler");
        return publicSuffixMatcher != null ? new PublicSuffixDomainFilter(commonCookieAttributeHandler, publicSuffixMatcher) : commonCookieAttributeHandler;
    }
}

