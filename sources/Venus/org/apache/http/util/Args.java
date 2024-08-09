/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.util.Collection;
import org.apache.http.util.TextUtils;

public class Args {
    public static void check(boolean bl, String string) {
        if (!bl) {
            throw new IllegalArgumentException(string);
        }
    }

    public static void check(boolean bl, String string, Object ... objectArray) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void check(boolean bl, String string, Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, object));
        }
    }

    public static <T> T notNull(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string + " may not be null");
        }
        return t;
    }

    public static <T extends CharSequence> T notEmpty(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string + " may not be null");
        }
        if (TextUtils.isEmpty(t)) {
            throw new IllegalArgumentException(string + " may not be empty");
        }
        return t;
    }

    public static <T extends CharSequence> T notBlank(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string + " may not be null");
        }
        if (TextUtils.isBlank(t)) {
            throw new IllegalArgumentException(string + " may not be blank");
        }
        return t;
    }

    public static <T extends CharSequence> T containsNoBlanks(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string + " may not be null");
        }
        if (t.length() == 0) {
            throw new IllegalArgumentException(string + " may not be empty");
        }
        if (TextUtils.containsBlanks(t)) {
            throw new IllegalArgumentException(string + " may not contain blanks");
        }
        return t;
    }

    public static <E, T extends Collection<E>> T notEmpty(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string + " may not be null");
        }
        if (t.isEmpty()) {
            throw new IllegalArgumentException(string + " may not be empty");
        }
        return t;
    }

    public static int positive(int n, String string) {
        if (n <= 0) {
            throw new IllegalArgumentException(string + " may not be negative or zero");
        }
        return n;
    }

    public static long positive(long l, String string) {
        if (l <= 0L) {
            throw new IllegalArgumentException(string + " may not be negative or zero");
        }
        return l;
    }

    public static int notNegative(int n, String string) {
        if (n < 0) {
            throw new IllegalArgumentException(string + " may not be negative");
        }
        return n;
    }

    public static long notNegative(long l, String string) {
        if (l < 0L) {
            throw new IllegalArgumentException(string + " may not be negative");
        }
        return l;
    }
}

