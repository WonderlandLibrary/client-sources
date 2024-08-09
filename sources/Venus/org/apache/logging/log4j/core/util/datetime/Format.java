/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util.datetime;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;

public abstract class Format {
    public final String format(Object object) {
        return this.format(object, new StringBuilder(), new FieldPosition(0)).toString();
    }

    public abstract StringBuilder format(Object var1, StringBuilder var2, FieldPosition var3);

    public abstract Object parseObject(String var1, ParsePosition var2);

    public Object parseObject(String string) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Object object = this.parseObject(string, parsePosition);
        if (parsePosition.getIndex() == 0) {
            throw new ParseException("Format.parseObject(String) failed", parsePosition.getErrorIndex());
        }
        return object;
    }
}

