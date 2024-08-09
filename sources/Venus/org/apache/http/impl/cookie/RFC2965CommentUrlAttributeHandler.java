/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.cookie.SetCookie2;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RFC2965CommentUrlAttributeHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        if (setCookie instanceof SetCookie2) {
            SetCookie2 setCookie2 = (SetCookie2)setCookie;
            setCookie2.setCommentURL(string);
        }
    }

    @Override
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
    }

    @Override
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        return false;
    }

    @Override
    public String getAttributeName() {
        return "commenturl";
    }
}

