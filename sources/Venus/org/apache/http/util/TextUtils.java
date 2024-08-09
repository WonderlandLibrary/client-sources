/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

public final class TextUtils {
    public static boolean isEmpty(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        return charSequence.length() == 0;
    }

    public static boolean isBlank(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        for (int i = 0; i < charSequence.length(); ++i) {
            if (Character.isWhitespace(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean containsBlanks(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        for (int i = 0; i < charSequence.length(); ++i) {
            if (!Character.isWhitespace(charSequence.charAt(i))) continue;
            return false;
        }
        return true;
    }
}

