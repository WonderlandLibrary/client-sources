/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.Cookie;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class CookiePathComparator
implements Serializable,
Comparator<Cookie> {
    public static final CookiePathComparator INSTANCE = new CookiePathComparator();
    private static final long serialVersionUID = 7523645369616405818L;

    private String normalizePath(Cookie cookie) {
        String string = cookie.getPath();
        if (string == null) {
            string = "/";
        }
        if (!string.endsWith("/")) {
            string = string + '/';
        }
        return string;
    }

    @Override
    public int compare(Cookie cookie, Cookie cookie2) {
        String string;
        String string2 = this.normalizePath(cookie);
        if (string2.equals(string = this.normalizePath(cookie2))) {
            return 1;
        }
        if (string2.startsWith(string)) {
            return 1;
        }
        if (string.startsWith(string2)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Cookie)object, (Cookie)object2);
    }
}

