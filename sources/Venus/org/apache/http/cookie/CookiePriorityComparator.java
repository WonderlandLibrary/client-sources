/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.Comparator;
import java.util.Date;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class CookiePriorityComparator
implements Comparator<Cookie> {
    public static final CookiePriorityComparator INSTANCE = new CookiePriorityComparator();

    private int getPathLength(Cookie cookie) {
        String string = cookie.getPath();
        return string != null ? string.length() : 1;
    }

    @Override
    public int compare(Cookie cookie, Cookie cookie2) {
        int n = this.getPathLength(cookie);
        int n2 = this.getPathLength(cookie2);
        int n3 = n2 - n;
        if (n3 == 0 && cookie instanceof BasicClientCookie && cookie2 instanceof BasicClientCookie) {
            Date date = ((BasicClientCookie)cookie).getCreationDate();
            Date date2 = ((BasicClientCookie)cookie2).getCreationDate();
            if (date != null && date2 != null) {
                return (int)(date.getTime() - date2.getTime());
            }
        }
        return n3;
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Cookie)object, (Cookie)object2);
    }
}

