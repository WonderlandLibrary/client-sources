/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CommonCookieAttributeHandler;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;
import org.apache.http.impl.cookie.AbstractCookieAttributeHandler;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class LaxExpiresHandler
extends AbstractCookieAttributeHandler
implements CommonCookieAttributeHandler {
    static final TimeZone UTC;
    private static final BitSet DELIMS;
    private static final Map<String, Integer> MONTHS;
    private static final Pattern TIME_PATTERN;
    private static final Pattern DAY_OF_MONTH_PATTERN;
    private static final Pattern MONTH_PATTERN;
    private static final Pattern YEAR_PATTERN;

    @Override
    public void parse(SetCookie setCookie, String string) throws MalformedCookieException {
        Object object;
        Args.notNull(setCookie, "Cookie");
        if (TextUtils.isBlank(string)) {
            return;
        }
        ParserCursor parserCursor = new ParserCursor(0, string.length());
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        try {
            while (!parserCursor.atEnd()) {
                this.skipDelims(string, parserCursor);
                stringBuilder.setLength(0);
                this.copyContent(string, parserCursor, stringBuilder);
                if (stringBuilder.length() != 0) {
                    if (!bl && ((Matcher)(object = TIME_PATTERN.matcher(stringBuilder))).matches()) {
                        bl = true;
                        n3 = Integer.parseInt(((Matcher)object).group(1));
                        n2 = Integer.parseInt(((Matcher)object).group(2));
                        n = Integer.parseInt(((Matcher)object).group(3));
                        continue;
                    }
                    if (!bl2 && ((Matcher)(object = DAY_OF_MONTH_PATTERN.matcher(stringBuilder))).matches()) {
                        bl2 = true;
                        n4 = Integer.parseInt(((Matcher)object).group(1));
                        continue;
                    }
                    if (!bl3 && ((Matcher)(object = MONTH_PATTERN.matcher(stringBuilder))).matches()) {
                        bl3 = true;
                        n5 = MONTHS.get(((Matcher)object).group(1).toLowerCase(Locale.ROOT));
                        continue;
                    }
                    if (bl4 || !((Matcher)(object = YEAR_PATTERN.matcher(stringBuilder))).matches()) continue;
                    bl4 = true;
                    n6 = Integer.parseInt(((Matcher)object).group(1));
                    continue;
                }
                break;
            }
        } catch (NumberFormatException numberFormatException) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + string);
        }
        if (!(bl && bl2 && bl3 && bl4)) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + string);
        }
        if (n6 >= 70 && n6 <= 99) {
            n6 = 1900 + n6;
        }
        if (n6 >= 0 && n6 <= 69) {
            n6 = 2000 + n6;
        }
        if (n4 < 1 || n4 > 31 || n6 < 1601 || n3 > 23 || n2 > 59 || n > 59) {
            throw new MalformedCookieException("Invalid 'expires' attribute: " + string);
        }
        object = Calendar.getInstance();
        ((Calendar)object).setTimeZone(UTC);
        ((Calendar)object).setTimeInMillis(0L);
        ((Calendar)object).set(13, n);
        ((Calendar)object).set(12, n2);
        ((Calendar)object).set(11, n3);
        ((Calendar)object).set(5, n4);
        ((Calendar)object).set(2, n5);
        ((Calendar)object).set(1, n6);
        setCookie.setExpiryDate(((Calendar)object).getTime());
    }

    private void skipDelims(CharSequence charSequence, ParserCursor parserCursor) {
        char c;
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        for (int i = n2; i < n3 && DELIMS.get(c = charSequence.charAt(i)); ++i) {
            ++n;
        }
        parserCursor.updatePos(n);
    }

    private void copyContent(CharSequence charSequence, ParserCursor parserCursor, StringBuilder stringBuilder) {
        char c;
        int n = parserCursor.getPos();
        int n2 = parserCursor.getPos();
        int n3 = parserCursor.getUpperBound();
        for (int i = n2; i < n3 && !DELIMS.get(c = charSequence.charAt(i)); ++i) {
            ++n;
            stringBuilder.append(c);
        }
        parserCursor.updatePos(n);
    }

    @Override
    public String getAttributeName() {
        return "expires";
    }

    static {
        int n;
        UTC = TimeZone.getTimeZone("UTC");
        Serializable serializable = new BitSet();
        ((BitSet)serializable).set(9);
        for (n = 32; n <= 47; ++n) {
            ((BitSet)serializable).set(n);
        }
        for (n = 59; n <= 64; ++n) {
            ((BitSet)serializable).set(n);
        }
        for (n = 91; n <= 96; ++n) {
            ((BitSet)serializable).set(n);
        }
        for (n = 123; n <= 126; ++n) {
            ((BitSet)serializable).set(n);
        }
        DELIMS = serializable;
        serializable = new ConcurrentHashMap(12);
        ((ConcurrentHashMap)serializable).put("jan", 0);
        ((ConcurrentHashMap)serializable).put("feb", 1);
        ((ConcurrentHashMap)serializable).put("mar", 2);
        ((ConcurrentHashMap)serializable).put("apr", 3);
        ((ConcurrentHashMap)serializable).put("may", 4);
        ((ConcurrentHashMap)serializable).put("jun", 5);
        ((ConcurrentHashMap)serializable).put("jul", 6);
        ((ConcurrentHashMap)serializable).put("aug", 7);
        ((ConcurrentHashMap)serializable).put("sep", 8);
        ((ConcurrentHashMap)serializable).put("oct", 9);
        ((ConcurrentHashMap)serializable).put("nov", 10);
        ((ConcurrentHashMap)serializable).put("dec", 11);
        MONTHS = serializable;
        TIME_PATTERN = Pattern.compile("^([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})([^0-9].*)?$");
        DAY_OF_MONTH_PATTERN = Pattern.compile("^([0-9]{1,2})([^0-9].*)?$");
        MONTH_PATTERN = Pattern.compile("^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)(.*)?$", 2);
        YEAR_PATTERN = Pattern.compile("^([0-9]{2,4})([^0-9].*)?$");
    }
}

