/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.AbstractCookieSpec;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public abstract class CookieSpecBase
extends AbstractCookieSpec {
    public CookieSpecBase() {
    }

    protected CookieSpecBase(HashMap<String, CookieAttributeHandler> hashMap) {
        super(hashMap);
    }

    protected CookieSpecBase(CommonCookieAttributeHandler ... commonCookieAttributeHandlerArray) {
        super(commonCookieAttributeHandlerArray);
    }

    protected static String getDefaultPath(CookieOrigin cookieOrigin) {
        String string = cookieOrigin.getPath();
        int n = string.lastIndexOf(47);
        if (n >= 0) {
            if (n == 0) {
                n = 1;
            }
            string = string.substring(0, n);
        }
        return string;
    }

    protected static String getDefaultDomain(CookieOrigin cookieOrigin) {
        return cookieOrigin.getHost();
    }

    protected List<Cookie> parse(HeaderElement[] headerElementArray, CookieOrigin cookieOrigin) throws MalformedCookieException {
        ArrayList<Cookie> arrayList = new ArrayList<Cookie>(headerElementArray.length);
        for (HeaderElement headerElement : headerElementArray) {
            String string = headerElement.getName();
            String string2 = headerElement.getValue();
            if (string == null || string.isEmpty()) continue;
            BasicClientCookie basicClientCookie = new BasicClientCookie(string, string2);
            basicClientCookie.setPath(CookieSpecBase.getDefaultPath(cookieOrigin));
            basicClientCookie.setDomain(CookieSpecBase.getDefaultDomain(cookieOrigin));
            NameValuePair[] nameValuePairArray = headerElement.getParameters();
            for (int i = nameValuePairArray.length - 1; i >= 0; --i) {
                NameValuePair nameValuePair = nameValuePairArray[i];
                String string3 = nameValuePair.getName().toLowerCase(Locale.ROOT);
                basicClientCookie.setAttribute(string3, nameValuePair.getValue());
                CookieAttributeHandler cookieAttributeHandler = this.findAttribHandler(string3);
                if (cookieAttributeHandler == null) continue;
                cookieAttributeHandler.parse(basicClientCookie, nameValuePair.getValue());
            }
            arrayList.add(basicClientCookie);
        }
        return arrayList;
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.getAttribHandlers()) {
            cookieAttributeHandler.validate(cookie, cookieOrigin);
        }
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        for (CookieAttributeHandler cookieAttributeHandler : this.getAttribHandlers()) {
            if (cookieAttributeHandler.match(cookie, cookieOrigin)) continue;
            return true;
        }
        return false;
    }
}

