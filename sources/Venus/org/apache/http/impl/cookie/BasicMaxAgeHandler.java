/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicMaxAgeHandler
extends AbstractCookieAttributeHandler
implements CommonCookieAttributeHandler {
    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        int n;
        Args.notNull(setCookie, "Cookie");
        if (string == null) {
            throw new MalformedCookieException("Missing value for 'max-age' attribute");
        }
        try {
            n = Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            throw new MalformedCookieException("Invalid 'max-age' attribute: " + string);
        }
        if (n < 0) {
            throw new MalformedCookieException("Negative 'max-age' attribute: " + string);
        }
        setCookie.setExpiryDate(new Date(System.currentTimeMillis() + (long)n * 1000L));
    }

    @Override
    public String getAttributeName() {
        return "max-age";
    }
}

