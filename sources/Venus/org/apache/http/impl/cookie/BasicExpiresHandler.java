/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BasicExpiresHandler
extends AbstractCookieAttributeHandler
implements CommonCookieAttributeHandler {
    private final String[] datePatterns;

    public BasicExpiresHandler(String[] stringArray) {
        Args.notNull(stringArray, "Array of date patterns");
        this.datePatterns = (String[])stringArray.clone();
    }

    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (string == null) {
            throw new MalformedCookieException("Missing value for 'expires' attribute");
        }
        Date date = DateUtils.parseDate(string, this.datePatterns);
        if (date == null) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + string);
        }
        setCookie.setExpiryDate(date);
    }

    @Override
    public String getAttributeName() {
        return "expires";
    }
}

