/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class Strings {
    public static final String EMPTY = "";
    public static final String LINE_SEPARATOR = PropertiesUtil.getProperties().getStringProperty("line.separator", "\n");

    private Strings() {
    }

    public static String dquote(String string) {
        return '\"' + string + '\"';
    }

    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotBlank(String string) {
        return !Strings.isBlank(string);
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !Strings.isEmpty(charSequence);
    }

    public static String quote(String string) {
        return '\'' + string + '\'';
    }

    public String toRootUpperCase(String string) {
        return string.toUpperCase(Locale.ROOT);
    }

    public static String trimToNull(String string) {
        String string2 = string == null ? null : string.trim();
        return Strings.isEmpty(string2) ? null : string2;
    }

    public static String join(Iterable<?> iterable, char c) {
        if (iterable == null) {
            return null;
        }
        return Strings.join(iterable.iterator(), c);
    }

    public static String join(Iterator<?> iterator2, char c) {
        if (iterator2 == null) {
            return null;
        }
        if (!iterator2.hasNext()) {
            return EMPTY;
        }
        Object obj = iterator2.next();
        if (!iterator2.hasNext()) {
            return Objects.toString(obj);
        }
        StringBuilder stringBuilder = new StringBuilder(256);
        if (obj != null) {
            stringBuilder.append(obj);
        }
        while (iterator2.hasNext()) {
            stringBuilder.append(c);
            Object obj2 = iterator2.next();
            if (obj2 == null) continue;
            stringBuilder.append(obj2);
        }
        return stringBuilder.toString();
    }
}

