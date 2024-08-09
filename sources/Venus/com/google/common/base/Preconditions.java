/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
public final class Preconditions {
    private Preconditions() {
    }

    public static void checkArgument(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean bl, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object ... objectArray) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, objectArray));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, char c) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, int n) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, n));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, long l) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, l));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, char c, char c2) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, Character.valueOf(c), Character.valueOf(c2)));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, char c, int n) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, Character.valueOf(c), n));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, char c, long l) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, Character.valueOf(c), l));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, char c, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, Character.valueOf(c), object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, int n, char c) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, n, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, int n, int n2) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, n, n2));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, int n, long l) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, n, l));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, int n, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, n, object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, long l, char c) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, l, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, long l, int n) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, l, n));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, long l, long l2) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, l, l2));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, long l, @Nullable Object object) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, l, object));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, char c) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, Character.valueOf(c)));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, int n) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, n));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, long l) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, l));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, object2));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, object2, object3));
        }
    }

    public static void checkArgument(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3, @Nullable Object object4) {
        if (!bl) {
            throw new IllegalArgumentException(Preconditions.format(string, object, object2, object3, object4));
        }
    }

    public static void checkState(boolean bl) {
        if (!bl) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean bl, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object ... objectArray) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, objectArray));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, char c) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, int n) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, n));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, long l) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, l));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, char c, char c2) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, Character.valueOf(c), Character.valueOf(c2)));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, char c, int n) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, Character.valueOf(c), n));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, char c, long l) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, Character.valueOf(c), l));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, char c, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, Character.valueOf(c), object));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, int n, char c) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, n, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, int n, int n2) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, n, n2));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, int n, long l) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, n, l));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, int n, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, n, object));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, long l, char c) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, l, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, long l, int n) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, l, n));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, long l, long l2) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, l, l2));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, long l, @Nullable Object object) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, l, object));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, char c) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, Character.valueOf(c)));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, int n) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, n));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, long l) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, l));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, object2));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, object2, object3));
        }
    }

    public static void checkState(boolean bl, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3, @Nullable Object object4) {
        if (!bl) {
            throw new IllegalStateException(Preconditions.format(string, object, object2, object3, object4));
        }
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, objectArray));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, char c) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, Character.valueOf(c)));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, int n) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, n));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, long l) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, l));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, char c, char c2) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, Character.valueOf(c), Character.valueOf(c2)));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, char c, int n) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, Character.valueOf(c), n));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, char c, long l) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, Character.valueOf(c), l));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, char c, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, Character.valueOf(c), object));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, int n, char c) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, n, Character.valueOf(c)));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, int n, int n2) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, n, n2));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, int n, long l) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, n, l));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, int n, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, n, object));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, long l, char c) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, l, Character.valueOf(c)));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, long l, int n) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, l, n));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, long l, long l2) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, l, l2));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, long l, @Nullable Object object) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, l, object));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, char c) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, Character.valueOf(c)));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, int n) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, n));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, long l) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, l));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, @Nullable Object object2) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, object2));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, object2, object3));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static <T> T checkNotNull(T t, @Nullable String string, @Nullable Object object, @Nullable Object object2, @Nullable Object object3, @Nullable Object object4) {
        if (t == null) {
            throw new NullPointerException(Preconditions.format(string, object, object2, object3, object4));
        }
        return t;
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int n, int n2) {
        return Preconditions.checkElementIndex(n, n2, "index");
    }

    @CanIgnoreReturnValue
    public static int checkElementIndex(int n, int n2, @Nullable String string) {
        if (n < 0 || n >= n2) {
            throw new IndexOutOfBoundsException(Preconditions.badElementIndex(n, n2, string));
        }
        return n;
    }

    private static String badElementIndex(int n, int n2, String string) {
        if (n < 0) {
            return Preconditions.format("%s (%s) must not be negative", string, n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative size: " + n2);
        }
        return Preconditions.format("%s (%s) must be less than size (%s)", string, n, n2);
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int n, int n2) {
        return Preconditions.checkPositionIndex(n, n2, "index");
    }

    @CanIgnoreReturnValue
    public static int checkPositionIndex(int n, int n2, @Nullable String string) {
        if (n < 0 || n > n2) {
            throw new IndexOutOfBoundsException(Preconditions.badPositionIndex(n, n2, string));
        }
        return n;
    }

    private static String badPositionIndex(int n, int n2, String string) {
        if (n < 0) {
            return Preconditions.format("%s (%s) must not be negative", string, n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative size: " + n2);
        }
        return Preconditions.format("%s (%s) must not be greater than size (%s)", string, n, n2);
    }

    public static void checkPositionIndexes(int n, int n2, int n3) {
        if (n < 0 || n2 < n || n2 > n3) {
            throw new IndexOutOfBoundsException(Preconditions.badPositionIndexes(n, n2, n3));
        }
    }

    private static String badPositionIndexes(int n, int n2, int n3) {
        if (n < 0 || n > n3) {
            return Preconditions.badPositionIndex(n, n3, "start index");
        }
        if (n2 < 0 || n2 > n3) {
            return Preconditions.badPositionIndex(n2, n3, "end index");
        }
        return Preconditions.format("end index (%s) must not be less than start index (%s)", n2, n);
    }

    static String format(String string, @Nullable Object ... objectArray) {
        int n;
        string = String.valueOf(string);
        StringBuilder stringBuilder = new StringBuilder(string.length() + 16 * objectArray.length);
        int n2 = 0;
        int n3 = 0;
        while (n3 < objectArray.length && (n = string.indexOf("%s", n2)) != -1) {
            stringBuilder.append(string, n2, n);
            stringBuilder.append(objectArray[n3++]);
            n2 = n + 2;
        }
        stringBuilder.append(string, n2, string.length());
        if (n3 < objectArray.length) {
            stringBuilder.append(" [");
            stringBuilder.append(objectArray[n3++]);
            while (n3 < objectArray.length) {
                stringBuilder.append(", ");
                stringBuilder.append(objectArray[n3++]);
            }
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }
}

