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
public class CookieIdentityComparator
implements Serializable,
Comparator<Cookie> {
    private static final long serialVersionUID = 4466565437490631532L;

    @Override
    public int compare(Cookie cookie, Cookie cookie2) {
        String string;
        String string2;
        int n = cookie.getName().compareTo(cookie2.getName());
        if (n == 0) {
            string2 = cookie.getDomain();
            if (string2 == null) {
                string2 = "";
            } else if (string2.indexOf(46) == -1) {
                string2 = string2 + ".local";
            }
            string = cookie2.getDomain();
            if (string == null) {
                string = "";
            } else if (string.indexOf(46) == -1) {
                string = string + ".local";
            }
            n = string2.compareToIgnoreCase(string);
        }
        if (n == 0) {
            string2 = cookie.getPath();
            if (string2 == null) {
                string2 = "/";
            }
            if ((string = cookie2.getPath()) == null) {
                string = "/";
            }
            n = string2.compareTo(string);
        }
        return n;
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Cookie)object, (Cookie)object2);
    }
}

