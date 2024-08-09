/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.DateFormatter;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieDecoder;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.internal.ObjectUtil;
import java.util.Date;

public final class ClientCookieDecoder
extends CookieDecoder {
    public static final ClientCookieDecoder STRICT = new ClientCookieDecoder(true);
    public static final ClientCookieDecoder LAX = new ClientCookieDecoder(false);

    private ClientCookieDecoder(boolean bl) {
        super(bl);
    }

    public Cookie decode(String string) {
        int n;
        int n2 = ObjectUtil.checkNotNull(string, "header").length();
        if (n2 == 0) {
            return null;
        }
        CookieBuilder cookieBuilder = null;
        int n3 = 0;
        while (n3 != n2 && (n = string.charAt(n3)) != 44) {
            int n4;
            int n5;
            int n6;
            block11: {
                if (n == 9 || n == 10 || n == 11 || n == 12 || n == 13 || n == 32 || n == 59) {
                    ++n3;
                    continue;
                }
                n = n3;
                do {
                    char c;
                    if ((c = string.charAt(n3)) == ';') {
                        n6 = n3;
                        n5 = -1;
                        n4 = -1;
                    } else {
                        if (c != '=') continue;
                        n6 = n3++;
                        if (n3 == n2) {
                            n5 = 0;
                            n4 = 0;
                        } else {
                            n4 = n3;
                            int n7 = string.indexOf(59, n3);
                            n3 = n7 > 0 ? n7 : n2;
                            n5 = n3;
                        }
                    }
                    break block11;
                } while (++n3 != n2);
                n6 = n2;
                n5 = -1;
                n4 = -1;
            }
            if (n5 > 0 && string.charAt(n5 - 1) == ',') {
                --n5;
            }
            if (cookieBuilder == null) {
                DefaultCookie defaultCookie = this.initCookie(string, n, n6, n4, n5);
                if (defaultCookie == null) {
                    return null;
                }
                cookieBuilder = new CookieBuilder(defaultCookie, string);
                continue;
            }
            cookieBuilder.appendAttribute(n, n6, n4, n5);
        }
        return cookieBuilder != null ? cookieBuilder.cookie() : null;
    }

    private static class CookieBuilder {
        private final String header;
        private final DefaultCookie cookie;
        private String domain;
        private String path;
        private long maxAge = Long.MIN_VALUE;
        private int expiresStart;
        private int expiresEnd;
        private boolean secure;
        private boolean httpOnly;

        CookieBuilder(DefaultCookie defaultCookie, String string) {
            this.cookie = defaultCookie;
            this.header = string;
        }

        private long mergeMaxAgeAndExpires() {
            Date date;
            if (this.maxAge != Long.MIN_VALUE) {
                return this.maxAge;
            }
            if (CookieBuilder.isValueDefined(this.expiresStart, this.expiresEnd) && (date = DateFormatter.parseHttpDate(this.header, this.expiresStart, this.expiresEnd)) != null) {
                long l = date.getTime() - System.currentTimeMillis();
                return l / 1000L + (long)(l % 1000L != 0L ? 1 : 0);
            }
            return Long.MIN_VALUE;
        }

        Cookie cookie() {
            this.cookie.setDomain(this.domain);
            this.cookie.setPath(this.path);
            this.cookie.setMaxAge(this.mergeMaxAgeAndExpires());
            this.cookie.setSecure(this.secure);
            this.cookie.setHttpOnly(this.httpOnly);
            return this.cookie;
        }

        void appendAttribute(int n, int n2, int n3, int n4) {
            int n5 = n2 - n;
            if (n5 == 4) {
                this.parse4(n, n3, n4);
            } else if (n5 == 6) {
                this.parse6(n, n3, n4);
            } else if (n5 == 7) {
                this.parse7(n, n3, n4);
            } else if (n5 == 8) {
                this.parse8(n);
            }
        }

        private void parse4(int n, int n2, int n3) {
            if (this.header.regionMatches(true, n, "Path", 0, 1)) {
                this.path = this.computeValue(n2, n3);
            }
        }

        private void parse6(int n, int n2, int n3) {
            if (this.header.regionMatches(true, n, "Domain", 0, 0)) {
                this.domain = this.computeValue(n2, n3);
            } else if (this.header.regionMatches(true, n, "Secure", 0, 0)) {
                this.secure = true;
            }
        }

        private void setMaxAge(String string) {
            try {
                this.maxAge = Math.max(Long.parseLong(string), 0L);
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }

        private void parse7(int n, int n2, int n3) {
            if (this.header.regionMatches(true, n, "Expires", 0, 0)) {
                this.expiresStart = n2;
                this.expiresEnd = n3;
            } else if (this.header.regionMatches(true, n, "Max-Age", 0, 0)) {
                this.setMaxAge(this.computeValue(n2, n3));
            }
        }

        private void parse8(int n) {
            if (this.header.regionMatches(true, n, "HTTPOnly", 0, 1)) {
                this.httpOnly = true;
            }
        }

        private static boolean isValueDefined(int n, int n2) {
            return n != -1 && n != n2;
        }

        private String computeValue(int n, int n2) {
            return CookieBuilder.isValueDefined(n, n2) ? this.header.substring(n, n2) : null;
        }
    }
}

