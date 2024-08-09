/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import org.apache.http.util.TextUtils;

public class Asserts {
    public static void check(boolean bl, String string) {
        if (!bl) {
            throw new IllegalStateException(string);
        }
    }

    public static void check(boolean bl, String string, Object ... objectArray) {
        if (!bl) {
            throw new IllegalStateException(String.format(string, objectArray));
        }
    }

    public static void check(boolean bl, String string, Object object) {
        if (!bl) {
            throw new IllegalStateException(String.format(string, object));
        }
    }

    public static void notNull(Object object, String string) {
        if (object == null) {
            throw new IllegalStateException(string + " is null");
        }
    }

    public static void notEmpty(CharSequence charSequence, String string) {
        if (TextUtils.isEmpty(charSequence)) {
            throw new IllegalStateException(string + " is empty");
        }
    }

    public static void notBlank(CharSequence charSequence, String string) {
        if (TextUtils.isBlank(charSequence)) {
            throw new IllegalStateException(string + " is blank");
        }
    }
}

