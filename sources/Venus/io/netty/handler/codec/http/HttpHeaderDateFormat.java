/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.util.concurrent.FastThreadLocal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@Deprecated
public final class HttpHeaderDateFormat
extends SimpleDateFormat {
    private static final long serialVersionUID = -925286159755905325L;
    private final SimpleDateFormat format1 = new HttpHeaderDateFormatObsolete1();
    private final SimpleDateFormat format2 = new HttpHeaderDateFormatObsolete2();
    private static final FastThreadLocal<HttpHeaderDateFormat> dateFormatThreadLocal = new FastThreadLocal<HttpHeaderDateFormat>(){

        @Override
        protected HttpHeaderDateFormat initialValue() {
            return new HttpHeaderDateFormat(null);
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };

    public static HttpHeaderDateFormat get() {
        return dateFormatThreadLocal.get();
    }

    private HttpHeaderDateFormat() {
        super("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        this.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public Date parse(String string, ParsePosition parsePosition) {
        Date date = super.parse(string, parsePosition);
        if (date == null) {
            date = this.format1.parse(string, parsePosition);
        }
        if (date == null) {
            date = this.format2.parse(string, parsePosition);
        }
        return date;
    }

    HttpHeaderDateFormat(1 var1_1) {
        this();
    }

    private static final class HttpHeaderDateFormatObsolete2
    extends SimpleDateFormat {
        private static final long serialVersionUID = 3010674519968303714L;

        HttpHeaderDateFormatObsolete2() {
            super("E MMM d HH:mm:ss yyyy", Locale.ENGLISH);
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }

    private static final class HttpHeaderDateFormatObsolete1
    extends SimpleDateFormat {
        private static final long serialVersionUID = -3178072504225114298L;

        HttpHeaderDateFormatObsolete1() {
            super("E, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH);
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }
}

