/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieEncoder;
import io.netty.handler.codec.http.cookie.CookieUtil;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public final class ClientCookieEncoder
extends CookieEncoder {
    public static final ClientCookieEncoder STRICT = new ClientCookieEncoder(true);
    public static final ClientCookieEncoder LAX = new ClientCookieEncoder(false);
    private static final Comparator<Cookie> COOKIE_COMPARATOR = new Comparator<Cookie>(){

        @Override
        public int compare(Cookie cookie, Cookie cookie2) {
            int n;
            String string = cookie.path();
            String string2 = cookie2.path();
            int n2 = string2 == null ? Integer.MAX_VALUE : string2.length();
            int n3 = n2 - (n = string == null ? Integer.MAX_VALUE : string.length());
            if (n3 != 0) {
                return n3;
            }
            return 1;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((Cookie)object, (Cookie)object2);
        }
    };

    private ClientCookieEncoder(boolean bl) {
        super(bl);
    }

    public String encode(String string, String string2) {
        return this.encode((Cookie)new DefaultCookie(string, string2));
    }

    public String encode(Cookie cookie) {
        StringBuilder stringBuilder = CookieUtil.stringBuilder();
        this.encode(stringBuilder, ObjectUtil.checkNotNull(cookie, "cookie"));
        return CookieUtil.stripTrailingSeparator(stringBuilder);
    }

    public String encode(Cookie ... cookieArray) {
        if (ObjectUtil.checkNotNull(cookieArray, "cookies").length == 0) {
            return null;
        }
        StringBuilder stringBuilder = CookieUtil.stringBuilder();
        if (this.strict) {
            if (cookieArray.length == 1) {
                this.encode(stringBuilder, cookieArray[0]);
            } else {
                Cookie[] cookieArray2 = Arrays.copyOf(cookieArray, cookieArray.length);
                Arrays.sort(cookieArray2, COOKIE_COMPARATOR);
                for (Cookie cookie : cookieArray2) {
                    this.encode(stringBuilder, cookie);
                }
            }
        } else {
            for (Cookie cookie : cookieArray) {
                this.encode(stringBuilder, cookie);
            }
        }
        return CookieUtil.stripTrailingSeparatorOrNull(stringBuilder);
    }

    public String encode(Collection<? extends Cookie> collection) {
        if (ObjectUtil.checkNotNull(collection, "cookies").isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = CookieUtil.stringBuilder();
        if (this.strict) {
            if (collection.size() == 1) {
                this.encode(stringBuilder, collection.iterator().next());
            } else {
                Cookie[] cookieArray = collection.toArray(new Cookie[collection.size()]);
                Arrays.sort(cookieArray, COOKIE_COMPARATOR);
                for (Cookie cookie : cookieArray) {
                    this.encode(stringBuilder, cookie);
                }
            }
        } else {
            for (Cookie cookie : collection) {
                this.encode(stringBuilder, cookie);
            }
        }
        return CookieUtil.stripTrailingSeparatorOrNull(stringBuilder);
    }

    public String encode(Iterable<? extends Cookie> iterable) {
        Iterator<? extends Cookie> iterator2 = ObjectUtil.checkNotNull(iterable, "cookies").iterator();
        if (!iterator2.hasNext()) {
            return null;
        }
        StringBuilder stringBuilder = CookieUtil.stringBuilder();
        if (this.strict) {
            Cookie cookie = iterator2.next();
            if (!iterator2.hasNext()) {
                this.encode(stringBuilder, cookie);
            } else {
                ArrayList<Cookie> arrayList = InternalThreadLocalMap.get().arrayList();
                arrayList.add(cookie);
                while (iterator2.hasNext()) {
                    arrayList.add(iterator2.next());
                }
                Cookie[] cookieArray = arrayList.toArray(new Cookie[arrayList.size()]);
                Arrays.sort(cookieArray, COOKIE_COMPARATOR);
                for (Cookie cookie2 : cookieArray) {
                    this.encode(stringBuilder, cookie2);
                }
            }
        } else {
            while (iterator2.hasNext()) {
                this.encode(stringBuilder, iterator2.next());
            }
        }
        return CookieUtil.stripTrailingSeparatorOrNull(stringBuilder);
    }

    private void encode(StringBuilder stringBuilder, Cookie cookie) {
        String string = cookie.name();
        String string2 = cookie.value() != null ? cookie.value() : "";
        this.validateCookie(string, string2);
        if (cookie.wrap()) {
            CookieUtil.addQuoted(stringBuilder, string, string2);
        } else {
            CookieUtil.add(stringBuilder, string, string2);
        }
    }
}

