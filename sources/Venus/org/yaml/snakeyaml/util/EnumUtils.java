/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.util;

public class EnumUtils {
    public static <T extends Enum<T>> T findEnumInsensitiveCase(Class<T> clazz, String string) {
        for (Enum enum_ : (Enum[])clazz.getEnumConstants()) {
            if (enum_.name().compareToIgnoreCase(string) != 0) continue;
            return (T)enum_;
        }
        throw new IllegalArgumentException("No enum constant " + clazz.getCanonicalName() + "." + string);
    }
}

