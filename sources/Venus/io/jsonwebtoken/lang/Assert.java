/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;
import java.util.Map;

public final class Assert {
    private Assert() {
    }

    public static void isTrue(boolean bl, String string) {
        if (!bl) {
            throw new IllegalArgumentException(string);
        }
    }

    public static void isTrue(boolean bl) {
        Assert.isTrue(bl, "[Assertion failed] - this expression must be true");
    }

    public static void isNull(Object object, String string) {
        if (object != null) {
            throw new IllegalArgumentException(string);
        }
    }

    public static void isNull(Object object) {
        Assert.isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static <T> T notNull(T t, String string) {
        if (t == null) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static void notNull(Object object) {
        Assert.notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    public static void hasLength(String string, String string2) {
        if (!Strings.hasLength(string)) {
            throw new IllegalArgumentException(string2);
        }
    }

    public static void hasLength(String string) {
        Assert.hasLength(string, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    public static <T extends CharSequence> T hasText(T t, String string) {
        if (!Strings.hasText(t)) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static void hasText(String string) {
        Assert.hasText(string, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    public static void doesNotContain(String string, String string2, String string3) {
        if (Strings.hasLength(string) && Strings.hasLength(string2) && string.indexOf(string2) != -1) {
            throw new IllegalArgumentException(string3);
        }
    }

    public static void doesNotContain(String string, String string2) {
        Assert.doesNotContain(string, string2, "[Assertion failed] - this String argument must not contain the substring [" + string2 + "]");
    }

    public static Object[] notEmpty(Object[] objectArray, String string) {
        if (Objects.isEmpty(objectArray)) {
            throw new IllegalArgumentException(string);
        }
        return objectArray;
    }

    public static void notEmpty(Object[] objectArray) {
        Assert.notEmpty(objectArray, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    public static byte[] notEmpty(byte[] byArray, String string) {
        if (Objects.isEmpty(byArray)) {
            throw new IllegalArgumentException(string);
        }
        return byArray;
    }

    public static char[] notEmpty(char[] cArray, String string) {
        if (Objects.isEmpty(cArray)) {
            throw new IllegalArgumentException(string);
        }
        return cArray;
    }

    public static void noNullElements(Object[] objectArray, String string) {
        if (objectArray != null) {
            for (int i = 0; i < objectArray.length; ++i) {
                if (objectArray[i] != null) continue;
                throw new IllegalArgumentException(string);
            }
        }
    }

    public static void noNullElements(Object[] objectArray) {
        Assert.noNullElements(objectArray, "[Assertion failed] - this array must not contain any null elements");
    }

    public static <T extends Collection<?>> T notEmpty(T t, String string) {
        if (Collections.isEmpty(t)) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static void notEmpty(Collection<?> collection) {
        Assert.notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    public static <T extends Map<?, ?>> T notEmpty(T t, String string) {
        if (Collections.isEmpty(t)) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static void notEmpty(Map map) {
        Assert.notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    public static <T> T isInstanceOf(Class<T> clazz, Object object) {
        return Assert.isInstanceOf(clazz, object, "");
    }

    public static <T> T isInstanceOf(Class<T> clazz, Object object, String string) {
        Assert.notNull(clazz, "Type to check against must not be null");
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(string + "Object of class [" + (object != null ? object.getClass().getName() : "null") + "] must be an instance of " + clazz);
        }
        return clazz.cast(object);
    }

    public static <T> T stateIsInstance(Class<T> clazz, Object object, String string) {
        Assert.notNull(clazz, "Type to check cannot be null.");
        if (!clazz.isInstance(object)) {
            String string2 = string + "Object of class [" + Objects.nullSafeClassName(object) + "] must be an instance of " + clazz;
            throw new IllegalStateException(string2);
        }
        return clazz.cast(object);
    }

    public static void isAssignable(Class clazz, Class clazz2) {
        Assert.isAssignable(clazz, clazz2, "");
    }

    public static void isAssignable(Class clazz, Class clazz2, String string) {
        Assert.notNull(clazz, "Type to check against must not be null");
        if (clazz2 == null || !clazz.isAssignableFrom(clazz2)) {
            throw new IllegalArgumentException(string + clazz2 + " is not assignable to " + clazz);
        }
    }

    public static <T extends Comparable<T>> T eq(T t, T t2, String string) {
        if (Assert.compareTo(t, t2) != 0) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    private static <T extends Comparable<T>> int compareTo(T t, T t2) {
        Assert.notNull(t, "value cannot be null.");
        Assert.notNull(t2, "requirement cannot be null.");
        return t.compareTo(t2);
    }

    public static <T extends Comparable<T>> T gt(T t, T t2, String string) {
        if (Assert.compareTo(t, t2) <= 0) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static <T extends Comparable<T>> T lte(T t, T t2, String string) {
        if (Assert.compareTo(t, t2) > 0) {
            throw new IllegalArgumentException(string);
        }
        return t;
    }

    public static void state(boolean bl, String string) {
        if (!bl) {
            throw new IllegalStateException(string);
        }
    }

    public static void state(boolean bl) {
        Assert.state(bl, "[Assertion failed] - this state invariant must be true");
    }

    public static <T> T stateNotNull(T t, String string) throws IllegalStateException {
        if (t == null) {
            throw new IllegalStateException(string);
        }
        return t;
    }
}

