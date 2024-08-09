/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.ChineseDateFormatSymbols;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.DisplayContext;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ChineseCalendar;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.text.FieldPosition;
import java.util.Locale;

@Deprecated
public class ChineseDateFormat
extends SimpleDateFormat {
    static final long serialVersionUID = -4610300753104099899L;

    @Deprecated
    public ChineseDateFormat(String string, Locale locale) {
        this(string, ULocale.forLocale(locale));
    }

    @Deprecated
    public ChineseDateFormat(String string, ULocale uLocale) {
        this(string, (String)null, uLocale);
    }

    @Deprecated
    public ChineseDateFormat(String string, String string2, ULocale uLocale) {
        super(string, new ChineseDateFormatSymbols(uLocale), new ChineseCalendar(TimeZone.getDefault(), uLocale), uLocale, true, string2);
    }

    @Override
    @Deprecated
    protected void subFormat(StringBuffer stringBuffer, char c, int n, int n2, int n3, DisplayContext displayContext, FieldPosition fieldPosition, Calendar calendar) {
        super.subFormat(stringBuffer, c, n, n2, n3, displayContext, fieldPosition, calendar);
    }

    @Override
    @Deprecated
    protected int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] blArray, Calendar calendar) {
        return super.subParse(string, n, c, n2, bl, bl2, blArray, calendar);
    }

    @Override
    @Deprecated
    protected DateFormat.Field patternCharToDateFormatField(char c) {
        return super.patternCharToDateFormatField(c);
    }

    @Deprecated
    public static class Field
    extends DateFormat.Field {
        private static final long serialVersionUID = -5102130532751400330L;
        @Deprecated
        public static final Field IS_LEAP_MONTH = new Field("is leap month", 22);

        @Deprecated
        protected Field(String string, int n) {
            super(string, n);
        }

        @Deprecated
        public static DateFormat.Field ofCalendarField(int n) {
            if (n == 22) {
                return IS_LEAP_MONTH;
            }
            return DateFormat.Field.ofCalendarField(n);
        }

        @Override
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
            }
            if (this.getName().equals(IS_LEAP_MONTH.getName())) {
                return IS_LEAP_MONTH;
            }
            throw new InvalidObjectException("Unknown attribute name.");
        }
    }
}

