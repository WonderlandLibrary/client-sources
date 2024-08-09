/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.util.Locale;

public final class EnglishEnums {
    private EnglishEnums() {
    }

    public static <T extends Enum<T>> T valueOf(Class<T> clazz, String string) {
        return EnglishEnums.valueOf(clazz, string, null);
    }

    public static <T extends Enum<T>> T valueOf(Class<T> clazz, String string, T t) {
        return string == null ? t : Enum.valueOf(clazz, string.toUpperCase(Locale.ENGLISH));
    }
}

