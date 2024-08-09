/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Messages;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DateConverter
implements ValueConverter<Date> {
    private final DateFormat formatter;

    public DateConverter(DateFormat dateFormat) {
        if (dateFormat == null) {
            throw new NullPointerException("illegal null formatter");
        }
        this.formatter = dateFormat;
    }

    public static DateConverter datePattern(String string) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string);
        simpleDateFormat.setLenient(true);
        return new DateConverter(simpleDateFormat);
    }

    @Override
    public Date convert(String string) {
        ParsePosition parsePosition = new ParsePosition(0);
        Date date = this.formatter.parse(string, parsePosition);
        if (parsePosition.getIndex() != string.length()) {
            throw new ValueConversionException(this.message(string));
        }
        return date;
    }

    @Override
    public Class<Date> valueType() {
        return Date.class;
    }

    @Override
    public String valuePattern() {
        return this.formatter instanceof SimpleDateFormat ? ((SimpleDateFormat)this.formatter).toPattern() : "";
    }

    private String message(String string) {
        Object[] objectArray;
        String string2;
        if (this.formatter instanceof SimpleDateFormat) {
            string2 = "with.pattern.message";
            objectArray = new Object[]{string, ((SimpleDateFormat)this.formatter).toPattern()};
        } else {
            string2 = "without.pattern.message";
            objectArray = new Object[]{string};
        }
        return Messages.message(Locale.getDefault(), "joptsimple.ExceptionMessages", DateConverter.class, string2, objectArray);
    }

    @Override
    public Object convert(String string) {
        return this.convert(string);
    }
}

