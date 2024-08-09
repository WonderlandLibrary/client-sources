/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Validate {
    private static final String DEFAULT_NOT_NAN_EX_MESSAGE = "The validated value is not a number";
    private static final String DEFAULT_FINITE_EX_MESSAGE = "The value is invalid: %f";
    private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
    private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
    private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
    private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
    private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
    private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
    private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
    private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
    private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
    private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
    private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
    private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
    private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
    private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
    private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
    private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
    private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";

    public static void isTrue(boolean bl, String string, long l) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, l));
        }
    }

    public static void isTrue(boolean bl, String string, double d) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, d));
        }
    }

    public static void isTrue(boolean bl, String string, Object ... objectArray) {
        if (!bl) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void isTrue(boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
        }
    }

    public static <T> T notNull(T t) {
        return Validate.notNull(t, DEFAULT_IS_NULL_EX_MESSAGE, new Object[0]);
    }

    public static <T> T notNull(T t, String string, Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T> T[] notEmpty(T[] TArray, String string, Object ... objectArray) {
        if (TArray == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        if (TArray.length == 0) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
        return TArray;
    }

    public static <T> T[] notEmpty(T[] TArray) {
        return Validate.notEmpty(TArray, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Collection<?>> T notEmpty(T t, String string, Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        if (t.isEmpty()) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends Collection<?>> T notEmpty(T t) {
        return Validate.notEmpty(t, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Map<?, ?>> T notEmpty(T t, String string, Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        if (t.isEmpty()) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends Map<?, ?>> T notEmpty(T t) {
        return Validate.notEmpty(t, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notEmpty(T t, String string, Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        if (t.length() == 0) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends CharSequence> T notEmpty(T t) {
        return Validate.notEmpty(t, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE, new Object[0]);
    }

    public static <T extends CharSequence> T notBlank(T t, String string, Object ... objectArray) {
        if (t == null) {
            throw new NullPointerException(String.format(string, objectArray));
        }
        if (StringUtils.isBlank(t)) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends CharSequence> T notBlank(T t) {
        return Validate.notBlank(t, DEFAULT_NOT_BLANK_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] noNullElements(T[] TArray, String string, Object ... objectArray) {
        Validate.notNull(TArray);
        for (int i = 0; i < TArray.length; ++i) {
            if (TArray[i] != null) continue;
            Object[] objectArray2 = ArrayUtils.add(objectArray, Integer.valueOf(i));
            throw new IllegalArgumentException(String.format(string, objectArray2));
        }
        return TArray;
    }

    public static <T> T[] noNullElements(T[] TArray) {
        return Validate.noNullElements(TArray, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE, new Object[0]);
    }

    public static <T extends Iterable<?>> T noNullElements(T t, String string, Object ... objectArray) {
        Validate.notNull(t);
        int n = 0;
        Iterator<?> iterator2 = t.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next() == null) {
                Object[] objectArray2 = ArrayUtils.addAll(objectArray, n);
                throw new IllegalArgumentException(String.format(string, objectArray2));
            }
            ++n;
        }
        return t;
    }

    public static <T extends Iterable<?>> T noNullElements(T t) {
        return Validate.noNullElements(t, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE, new Object[0]);
    }

    public static <T> T[] validIndex(T[] TArray, int n, String string, Object ... objectArray) {
        Validate.notNull(TArray);
        if (n < 0 || n >= TArray.length) {
            throw new IndexOutOfBoundsException(String.format(string, objectArray));
        }
        return TArray;
    }

    public static <T> T[] validIndex(T[] TArray, int n) {
        return Validate.validIndex(TArray, n, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, new Object[]{n});
    }

    public static <T extends Collection<?>> T validIndex(T t, int n, String string, Object ... objectArray) {
        Validate.notNull(t);
        if (n < 0 || n >= t.size()) {
            throw new IndexOutOfBoundsException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends Collection<?>> T validIndex(T t, int n) {
        return Validate.validIndex(t, n, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, new Object[]{n});
    }

    public static <T extends CharSequence> T validIndex(T t, int n, String string, Object ... objectArray) {
        Validate.notNull(t);
        if (n < 0 || n >= t.length()) {
            throw new IndexOutOfBoundsException(String.format(string, objectArray));
        }
        return t;
    }

    public static <T extends CharSequence> T validIndex(T t, int n) {
        return Validate.validIndex(t, n, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, n);
    }

    public static void validState(boolean bl) {
        if (!bl) {
            throw new IllegalStateException(DEFAULT_VALID_STATE_EX_MESSAGE);
        }
    }

    public static void validState(boolean bl, String string, Object ... objectArray) {
        if (!bl) {
            throw new IllegalStateException(String.format(string, objectArray));
        }
    }

    public static void matchesPattern(CharSequence charSequence, String string) {
        if (!Pattern.matches(string, charSequence)) {
            throw new IllegalArgumentException(String.format(DEFAULT_MATCHES_PATTERN_EX, charSequence, string));
        }
    }

    public static void matchesPattern(CharSequence charSequence, String string, String string2, Object ... objectArray) {
        if (!Pattern.matches(string, charSequence)) {
            throw new IllegalArgumentException(String.format(string2, objectArray));
        }
    }

    public static void notNaN(double d) {
        Validate.notNaN(d, DEFAULT_NOT_NAN_EX_MESSAGE, new Object[0]);
    }

    public static void notNaN(double d, String string, Object ... objectArray) {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void finite(double d) {
        Validate.finite(d, DEFAULT_FINITE_EX_MESSAGE, d);
    }

    public static void finite(double d, String string, Object ... objectArray) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static <T> void inclusiveBetween(T t, T t2, Comparable<T> comparable) {
        if (comparable.compareTo(t) < 0 || comparable.compareTo(t2) > 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, comparable, t, t2));
        }
    }

    public static <T> void inclusiveBetween(T t, T t2, Comparable<T> comparable, String string, Object ... objectArray) {
        if (comparable.compareTo(t) < 0 || comparable.compareTo(t2) > 0) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void inclusiveBetween(long l, long l2, long l3) {
        if (l3 < l || l3 > l2) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, l3, l, l2));
        }
    }

    public static void inclusiveBetween(long l, long l2, long l3, String string) {
        if (l3 < l || l3 > l2) {
            throw new IllegalArgumentException(String.format(string, new Object[0]));
        }
    }

    public static void inclusiveBetween(double d, double d2, double d3) {
        if (d3 < d || d3 > d2) {
            throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, d3, d, d2));
        }
    }

    public static void inclusiveBetween(double d, double d2, double d3, String string) {
        if (d3 < d || d3 > d2) {
            throw new IllegalArgumentException(String.format(string, new Object[0]));
        }
    }

    public static <T> void exclusiveBetween(T t, T t2, Comparable<T> comparable) {
        if (comparable.compareTo(t) <= 0 || comparable.compareTo(t2) >= 0) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, comparable, t, t2));
        }
    }

    public static <T> void exclusiveBetween(T t, T t2, Comparable<T> comparable, String string, Object ... objectArray) {
        if (comparable.compareTo(t) <= 0 || comparable.compareTo(t2) >= 0) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void exclusiveBetween(long l, long l2, long l3) {
        if (l3 <= l || l3 >= l2) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, l3, l, l2));
        }
    }

    public static void exclusiveBetween(long l, long l2, long l3, String string) {
        if (l3 <= l || l3 >= l2) {
            throw new IllegalArgumentException(String.format(string, new Object[0]));
        }
    }

    public static void exclusiveBetween(double d, double d2, double d3) {
        if (d3 <= d || d3 >= d2) {
            throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, d3, d, d2));
        }
    }

    public static void exclusiveBetween(double d, double d2, double d3, String string) {
        if (d3 <= d || d3 >= d2) {
            throw new IllegalArgumentException(String.format(string, new Object[0]));
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object object) {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, clazz.getName(), object == null ? "null" : object.getClass().getName()));
        }
    }

    public static void isInstanceOf(Class<?> clazz, Object object, String string, Object ... objectArray) {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }

    public static void isAssignableFrom(Class<?> clazz, Class<?> clazz2) {
        if (!clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE, clazz2 == null ? "null" : clazz2.getName(), clazz.getName()));
        }
    }

    public static void isAssignableFrom(Class<?> clazz, Class<?> clazz2, String string, Object ... objectArray) {
        if (!clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException(String.format(string, objectArray));
        }
    }
}

