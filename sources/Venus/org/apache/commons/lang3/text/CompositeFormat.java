/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class CompositeFormat
extends Format {
    private static final long serialVersionUID = -4329119827877627683L;
    private final Format parser;
    private final Format formatter;

    public CompositeFormat(Format format2, Format format3) {
        this.parser = format2;
        this.formatter = format3;
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.formatter.format(object, stringBuffer, fieldPosition);
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        return this.parser.parseObject(string, parsePosition);
    }

    public Format getParser() {
        return this.parser;
    }

    public Format getFormatter() {
        return this.formatter;
    }

    public String reformat(String string) throws ParseException {
        return this.format(this.parseObject(string));
    }
}

