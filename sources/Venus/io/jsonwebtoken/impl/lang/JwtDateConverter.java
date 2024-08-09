/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.DateFormats;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class JwtDateConverter
implements Converter<Date, Object> {
    public static final JwtDateConverter INSTANCE = new JwtDateConverter();

    @Override
    public Object applyTo(Date date) {
        if (date == null) {
            return null;
        }
        return date.getTime() / 1000L;
    }

    @Override
    public Date applyFrom(Object object) {
        return JwtDateConverter.toSpecDate(object);
    }

    public static Date toSpecDate(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            try {
                object = Long.parseLong((String)object);
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        if (object instanceof Number) {
            long l = ((Number)object).longValue();
            object = l * 1000L;
        }
        return JwtDateConverter.toDate(object);
    }

    public static Date toDate(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return (Date)object;
        }
        if (object instanceof Calendar) {
            return ((Calendar)object).getTime();
        }
        if (object instanceof Number) {
            long l = ((Number)object).longValue();
            return new Date(l);
        }
        if (object instanceof String) {
            return JwtDateConverter.parseIso8601Date((String)object);
        }
        String string = "Cannot create Date from object of type " + object.getClass().getName() + ".";
        throw new IllegalArgumentException(string);
    }

    private static Date parseIso8601Date(String string) throws IllegalArgumentException {
        try {
            return DateFormats.parseIso8601Date(string);
        } catch (ParseException parseException) {
            String string2 = "String value is not a JWT NumericDate, nor is it ISO-8601-formatted. All heuristics exhausted. Cause: " + parseException.getMessage();
            throw new IllegalArgumentException(string2, parseException);
        }
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((Date)object);
    }
}

