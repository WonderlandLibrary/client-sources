/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.handler.codec.DateFormatter;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.CookieEncoder;
import io.netty.handler.codec.http.cookie.CookieUtil;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ServerCookieEncoder
extends CookieEncoder {
    public static final ServerCookieEncoder STRICT = new ServerCookieEncoder(true);
    public static final ServerCookieEncoder LAX = new ServerCookieEncoder(false);

    private ServerCookieEncoder(boolean bl) {
        super(bl);
    }

    public String encode(String string, String string2) {
        return this.encode((Cookie)new DefaultCookie(string, string2));
    }

    public String encode(Cookie cookie) {
        String string = ObjectUtil.checkNotNull(cookie, "cookie").name();
        String string2 = cookie.value() != null ? cookie.value() : "";
        this.validateCookie(string, string2);
        StringBuilder stringBuilder = CookieUtil.stringBuilder();
        if (cookie.wrap()) {
            CookieUtil.addQuoted(stringBuilder, string, string2);
        } else {
            CookieUtil.add(stringBuilder, string, string2);
        }
        if (cookie.maxAge() != Long.MIN_VALUE) {
            CookieUtil.add(stringBuilder, "Max-Age", cookie.maxAge());
            Date date = new Date(cookie.maxAge() * 1000L + System.currentTimeMillis());
            stringBuilder.append("Expires");
            stringBuilder.append('=');
            DateFormatter.append(date, stringBuilder);
            stringBuilder.append(';');
            stringBuilder.append(' ');
        }
        if (cookie.path() != null) {
            CookieUtil.add(stringBuilder, "Path", cookie.path());
        }
        if (cookie.domain() != null) {
            CookieUtil.add(stringBuilder, "Domain", cookie.domain());
        }
        if (cookie.isSecure()) {
            CookieUtil.add(stringBuilder, "Secure");
        }
        if (cookie.isHttpOnly()) {
            CookieUtil.add(stringBuilder, "HTTPOnly");
        }
        return CookieUtil.stripTrailingSeparator(stringBuilder);
    }

    private static List<String> dedup(List<String> list, Map<String, Integer> map) {
        int n;
        boolean[] blArray = new boolean[list.size()];
        Object object = map.values().iterator();
        while (object.hasNext()) {
            n = object.next();
            blArray[n] = true;
        }
        object = new ArrayList(map.size());
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            if (!blArray[n]) continue;
            object.add(list.get(n));
        }
        return object;
    }

    public List<String> encode(Cookie ... cookieArray) {
        if (ObjectUtil.checkNotNull(cookieArray, "cookies").length == 0) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>(cookieArray.length);
        HashMap<String, Integer> hashMap = this.strict && cookieArray.length > 1 ? new HashMap<String, Integer>() : null;
        boolean bl = false;
        for (int i = 0; i < cookieArray.length; ++i) {
            Cookie cookie = cookieArray[i];
            arrayList.add(this.encode(cookie));
            if (hashMap == null) continue;
            bl |= hashMap.put(cookie.name(), i) != null;
        }
        return bl ? ServerCookieEncoder.dedup(arrayList, hashMap) : arrayList;
    }

    public List<String> encode(Collection<? extends Cookie> collection) {
        if (ObjectUtil.checkNotNull(collection, "cookies").isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>(collection.size());
        HashMap<String, Integer> hashMap = this.strict && collection.size() > 1 ? new HashMap<String, Integer>() : null;
        int n = 0;
        boolean bl = false;
        for (Cookie cookie : collection) {
            arrayList.add(this.encode(cookie));
            if (hashMap == null) continue;
            bl |= hashMap.put(cookie.name(), n++) != null;
        }
        return bl ? ServerCookieEncoder.dedup(arrayList, hashMap) : arrayList;
    }

    public List<String> encode(Iterable<? extends Cookie> iterable) {
        boolean bl;
        Iterator<? extends Cookie> iterator2 = ObjectUtil.checkNotNull(iterable, "cookies").iterator();
        if (!iterator2.hasNext()) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        Cookie cookie = iterator2.next();
        HashMap<String, Integer> hashMap = this.strict && iterator2.hasNext() ? new HashMap<String, Integer>() : null;
        int n = 0;
        arrayList.add(this.encode(cookie));
        boolean bl2 = bl = hashMap != null && hashMap.put(cookie.name(), n++) != null;
        while (iterator2.hasNext()) {
            Cookie cookie2 = iterator2.next();
            arrayList.add(this.encode(cookie2));
            if (hashMap == null) continue;
            bl |= hashMap.put(cookie2.name(), n++) != null;
        }
        return bl ? ServerCookieEncoder.dedup(arrayList, hashMap) : arrayList;
    }
}

