/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieDecoder;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public final class ServerCookieDecoder
extends CookieDecoder {
    private static final String RFC2965_VERSION = "$Version";
    private static final String RFC2965_PATH = "$Path";
    private static final String RFC2965_DOMAIN = "$Domain";
    private static final String RFC2965_PORT = "$Port";
    public static final ServerCookieDecoder STRICT = new ServerCookieDecoder(true);
    public static final ServerCookieDecoder LAX = new ServerCookieDecoder(false);

    private ServerCookieDecoder(boolean bl) {
        super(bl);
    }

    public Set<Cookie> decode(String string) {
        int n = ObjectUtil.checkNotNull(string, "header").length();
        if (n == 0) {
            return Collections.emptySet();
        }
        TreeSet<Cookie> treeSet = new TreeSet<Cookie>();
        int n2 = 0;
        boolean bl = false;
        if (string.regionMatches(true, 0, RFC2965_VERSION, 0, 1)) {
            n2 = string.indexOf(59) + 1;
            bl = true;
        }
        while (n2 != n) {
            DefaultCookie defaultCookie;
            int n3;
            int n4;
            int n5;
            int n6;
            block9: {
                n6 = string.charAt(n2);
                if (n6 == 9 || n6 == 10 || n6 == 11 || n6 == 12 || n6 == 13 || n6 == 32 || n6 == 44 || n6 == 59) {
                    ++n2;
                    continue;
                }
                n6 = n2;
                do {
                    char c;
                    if ((c = string.charAt(n2)) == ';') {
                        n5 = n2;
                        n4 = -1;
                        n3 = -1;
                    } else {
                        if (c != '=') continue;
                        n5 = n2++;
                        if (n2 == n) {
                            n4 = 0;
                            n3 = 0;
                        } else {
                            n3 = n2;
                            int n7 = string.indexOf(59, n2);
                            n2 = n7 > 0 ? n7 : n;
                            n4 = n2;
                        }
                    }
                    break block9;
                } while (++n2 != n);
                n5 = n;
                n4 = -1;
                n3 = -1;
            }
            if (bl && (string.regionMatches(n6, RFC2965_PATH, 0, 0) || string.regionMatches(n6, RFC2965_DOMAIN, 0, 0) || string.regionMatches(n6, RFC2965_PORT, 0, 0)) || (defaultCookie = this.initCookie(string, n6, n5, n3, n4)) == null) continue;
            treeSet.add(defaultCookie);
        }
        return treeSet;
    }
}

