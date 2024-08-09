/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class LaxMaxAgeHandler
extends AbstractCookieAttributeHandler
implements CommonCookieAttributeHandler {
    private static final Pattern MAX_AGE_PATTERN = Pattern.compile("^\\-?[0-9]+$");

    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(string)) {
            return;
        }
        Matcher matcher = MAX_AGE_PATTERN.matcher(string);
        if (matcher.matches()) {
            int n;
            try {
                n = Integer.parseInt(string);
            } catch (NumberFormatException numberFormatException) {
                return;
            }
            Date date = n >= 0 ? new Date(System.currentTimeMillis() + (long)n * 1000L) : new Date(Long.MIN_VALUE);
            setCookie.setExpiryDate(date);
        }
    }

    @Override
    public String getAttributeName() {
        return "max-age";
    }
}

