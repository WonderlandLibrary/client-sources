/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.AttributeConverter
 *  javax.persistence.Converter
 */
package org.apache.logging.log4j.core.appender.db.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class StackTraceElementAttributeConverter
implements AttributeConverter<StackTraceElement, String> {
    private static final int UNKNOWN_SOURCE = -1;
    private static final int NATIVE_METHOD = -2;

    public String convertToDatabaseColumn(StackTraceElement stackTraceElement) {
        if (stackTraceElement == null) {
            return null;
        }
        return stackTraceElement.toString();
    }

    public StackTraceElement convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        return StackTraceElementAttributeConverter.convertString(string);
    }

    static StackTraceElement convertString(String string) {
        int n = string.indexOf("(");
        String string2 = string.substring(0, n);
        String string3 = string2.substring(0, string2.lastIndexOf("."));
        String string4 = string2.substring(string2.lastIndexOf(".") + 1);
        String string5 = string.substring(n + 1, string.indexOf(")"));
        String string6 = null;
        int n2 = -1;
        if ("Native Method".equals(string5)) {
            n2 = -2;
        } else if (!"Unknown Source".equals(string5)) {
            int n3 = string5.indexOf(":");
            if (n3 > -1) {
                string6 = string5.substring(0, n3);
                try {
                    n2 = Integer.parseInt(string5.substring(n3 + 1));
                } catch (NumberFormatException numberFormatException) {}
            } else {
                string6 = string5.substring(0);
            }
        }
        return new StackTraceElement(string3, string4, string6, n2);
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((StackTraceElement)object);
    }
}

