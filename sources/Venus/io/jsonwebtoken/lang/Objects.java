/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.lang;

import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public final class Objects {
    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;
    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = "{}";
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

    private Objects() {
    }

    public static boolean isCheckedException(Throwable throwable) {
        return !(throwable instanceof RuntimeException) && !(throwable instanceof Error);
    }

    public static boolean isCompatibleWithThrowsClause(Throwable throwable, Class[] classArray) {
        if (!Objects.isCheckedException(throwable)) {
            return false;
        }
        if (classArray != null) {
            for (int i = 0; i < classArray.length; ++i) {
                if (!classArray[i].isAssignableFrom(throwable.getClass())) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    public static boolean isEmpty(Object object) {
        return object == null || object instanceof String && !Strings.hasText((String)object) || object instanceof Collection && Collections.isEmpty((Collection)object) || object instanceof Map && Collections.isEmpty((Map)object) || object.getClass().isArray() && Array.getLength(object) == 0;
    }

    public static boolean isEmpty(Object[] objectArray) {
        return objectArray == null || objectArray.length == 0;
    }

    public static boolean isEmpty(byte[] byArray) {
        return byArray == null || byArray.length == 0;
    }

    public static boolean isEmpty(char[] cArray) {
        return cArray == null || cArray.length == 0;
    }

    public static boolean containsElement(Object[] objectArray, Object object) {
        if (objectArray == null) {
            return true;
        }
        for (Object object2 : objectArray) {
            if (!Objects.nullSafeEquals(object2, object)) continue;
            return false;
        }
        return true;
    }

    public static boolean containsConstant(Enum<?>[] enumArray, String string) {
        return Objects.containsConstant(enumArray, string, false);
    }

    public static boolean containsConstant(Enum<?>[] enumArray, String string, boolean bl) {
        for (Enum<?> enum_ : enumArray) {
            if (!(bl ? enum_.toString().equals(string) : enum_.toString().equalsIgnoreCase(string))) continue;
            return false;
        }
        return true;
    }

    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] EArray, String string) {
        for (E e : EArray) {
            if (!((Enum)e).toString().equalsIgnoreCase(string)) continue;
            return e;
        }
        throw new IllegalArgumentException(String.format("constant [%s] does not exist in enum type %s", string, EArray.getClass().getComponentType().getName()));
    }

    public static <A, O extends A> A[] addObjectToArray(A[] AArray, O o) {
        Class clazz = Object.class;
        if (AArray != null) {
            clazz = AArray.getClass().getComponentType();
        } else if (o != null) {
            clazz = o.getClass();
        }
        int n = AArray != null ? AArray.length + 1 : 1;
        Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        if (AArray != null) {
            System.arraycopy(AArray, 0, objectArray, 0, AArray.length);
        }
        objectArray[objectArray.length - 1] = o;
        return objectArray;
    }

    public static Object[] toObjectArray(Object object) {
        if (object instanceof Object[]) {
            return (Object[])object;
        }
        if (object == null) {
            return new Object[0];
        }
        if (!object.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + object);
        }
        int n = Array.getLength(object);
        if (n == 0) {
            return new Object[0];
        }
        Class<?> clazz = Array.get(object, 0).getClass();
        Object[] objectArray = (Object[])Array.newInstance(clazz, n);
        for (int i = 0; i < n; ++i) {
            objectArray[i] = Array.get(object, i);
        }
        return objectArray;
    }

    public static boolean nullSafeEquals(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        if (object == null || object2 == null) {
            return true;
        }
        if (object.equals(object2)) {
            return false;
        }
        if (object.getClass().isArray() && object2.getClass().isArray()) {
            if (object instanceof Object[] && object2 instanceof Object[]) {
                return Arrays.equals((Object[])object, (Object[])object2);
            }
            if (object instanceof boolean[] && object2 instanceof boolean[]) {
                return Arrays.equals((boolean[])object, (boolean[])object2);
            }
            if (object instanceof byte[] && object2 instanceof byte[]) {
                return Arrays.equals((byte[])object, (byte[])object2);
            }
            if (object instanceof char[] && object2 instanceof char[]) {
                return Arrays.equals((char[])object, (char[])object2);
            }
            if (object instanceof double[] && object2 instanceof double[]) {
                return Arrays.equals((double[])object, (double[])object2);
            }
            if (object instanceof float[] && object2 instanceof float[]) {
                return Arrays.equals((float[])object, (float[])object2);
            }
            if (object instanceof int[] && object2 instanceof int[]) {
                return Arrays.equals((int[])object, (int[])object2);
            }
            if (object instanceof long[] && object2 instanceof long[]) {
                return Arrays.equals((long[])object, (long[])object2);
            }
            if (object instanceof short[] && object2 instanceof short[]) {
                return Arrays.equals((short[])object, (short[])object2);
            }
        }
        return true;
    }

    public static int nullSafeHashCode(Object object) {
        if (object == null) {
            return 1;
        }
        if (object.getClass().isArray()) {
            if (object instanceof Object[]) {
                return Objects.nullSafeHashCode((Object[])object);
            }
            if (object instanceof boolean[]) {
                return Objects.nullSafeHashCode((boolean[])object);
            }
            if (object instanceof byte[]) {
                return Objects.nullSafeHashCode((byte[])object);
            }
            if (object instanceof char[]) {
                return Objects.nullSafeHashCode((char[])object);
            }
            if (object instanceof double[]) {
                return Objects.nullSafeHashCode((double[])object);
            }
            if (object instanceof float[]) {
                return Objects.nullSafeHashCode((float[])object);
            }
            if (object instanceof int[]) {
                return Objects.nullSafeHashCode((int[])object);
            }
            if (object instanceof long[]) {
                return Objects.nullSafeHashCode((long[])object);
            }
            if (object instanceof short[]) {
                return Objects.nullSafeHashCode((short[])object);
            }
        }
        return object.hashCode();
    }

    public static int nullSafeHashCode(Object ... objectArray) {
        if (objectArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = objectArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + Objects.nullSafeHashCode(objectArray[i]);
        }
        return n;
    }

    public static int nullSafeHashCode(boolean[] blArray) {
        if (blArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = blArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + Objects.hashCode(blArray[i]);
        }
        return n;
    }

    public static int nullSafeHashCode(byte[] byArray) {
        if (byArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = byArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + byArray[i];
        }
        return n;
    }

    public static int nullSafeHashCode(char[] cArray) {
        if (cArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = cArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + cArray[i];
        }
        return n;
    }

    public static int nullSafeHashCode(double[] dArray) {
        if (dArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = dArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + Objects.hashCode(dArray[i]);
        }
        return n;
    }

    public static int nullSafeHashCode(float[] fArray) {
        if (fArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = fArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + Objects.hashCode(fArray[i]);
        }
        return n;
    }

    public static int nullSafeHashCode(int[] nArray) {
        if (nArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = nArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + nArray[i];
        }
        return n;
    }

    public static int nullSafeHashCode(long[] lArray) {
        if (lArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = lArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + Objects.hashCode(lArray[i]);
        }
        return n;
    }

    public static int nullSafeHashCode(short[] sArray) {
        if (sArray == null) {
            return 1;
        }
        int n = 7;
        int n2 = sArray.length;
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + sArray[i];
        }
        return n;
    }

    public static int hashCode(boolean bl) {
        return bl ? 1231 : 1237;
    }

    public static int hashCode(double d) {
        long l = Double.doubleToLongBits(d);
        return Objects.hashCode(l);
    }

    public static int hashCode(float f) {
        return Float.floatToIntBits(f);
    }

    public static int hashCode(long l) {
        return (int)(l ^ l >>> 32);
    }

    public static String identityToString(Object object) {
        if (object == null) {
            return EMPTY_STRING;
        }
        return object.getClass().getName() + "@" + Objects.getIdentityHexString(object);
    }

    public static String getIdentityHexString(Object object) {
        return Integer.toHexString(System.identityHashCode(object));
    }

    public static String getDisplayString(Object object) {
        if (object == null) {
            return EMPTY_STRING;
        }
        return Objects.nullSafeToString(object);
    }

    public static String nullSafeClassName(Object object) {
        return object != null ? object.getClass().getName() : NULL_STRING;
    }

    public static String nullSafeToString(Object object) {
        if (object == null) {
            return NULL_STRING;
        }
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof Object[]) {
            return Objects.nullSafeToString((Object[])object);
        }
        if (object instanceof boolean[]) {
            return Objects.nullSafeToString((boolean[])object);
        }
        if (object instanceof byte[]) {
            return Objects.nullSafeToString((byte[])object);
        }
        if (object instanceof char[]) {
            return Objects.nullSafeToString((char[])object);
        }
        if (object instanceof double[]) {
            return Objects.nullSafeToString((double[])object);
        }
        if (object instanceof float[]) {
            return Objects.nullSafeToString((float[])object);
        }
        if (object instanceof int[]) {
            return Objects.nullSafeToString((int[])object);
        }
        if (object instanceof long[]) {
            return Objects.nullSafeToString((long[])object);
        }
        if (object instanceof short[]) {
            return Objects.nullSafeToString((short[])object);
        }
        String string = object.toString();
        return string != null ? string : EMPTY_STRING;
    }

    public static String nullSafeToString(Object[] objectArray) {
        if (objectArray == null) {
            return NULL_STRING;
        }
        int n = objectArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(String.valueOf(objectArray[i]));
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(boolean[] blArray) {
        if (blArray == null) {
            return NULL_STRING;
        }
        int n = blArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(blArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(byte[] byArray) {
        if (byArray == null) {
            return NULL_STRING;
        }
        int n = byArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(byArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(char[] cArray) {
        if (cArray == null) {
            return NULL_STRING;
        }
        int n = cArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append("'").append(cArray[i]).append("'");
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(double[] dArray) {
        if (dArray == null) {
            return NULL_STRING;
        }
        int n = dArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(dArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(float[] fArray) {
        if (fArray == null) {
            return NULL_STRING;
        }
        int n = fArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(fArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(int[] nArray) {
        if (nArray == null) {
            return NULL_STRING;
        }
        int n = nArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(nArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(long[] lArray) {
        if (lArray == null) {
            return NULL_STRING;
        }
        int n = lArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(lArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static String nullSafeToString(short[] sArray) {
        if (sArray == null) {
            return NULL_STRING;
        }
        int n = sArray.length;
        if (n == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuilder.append(ARRAY_START);
            } else {
                stringBuilder.append(ARRAY_ELEMENT_SEPARATOR);
            }
            stringBuilder.append(sArray[i]);
        }
        stringBuilder.append(ARRAY_END);
        return stringBuilder.toString();
    }

    public static void nullSafeClose(Closeable ... closeableArray) {
        if (closeableArray == null) {
            return;
        }
        for (Closeable closeable : closeableArray) {
            if (closeable == null) continue;
            try {
                closeable.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static void nullSafeFlush(Flushable ... flushableArray) {
        if (flushableArray == null) {
            return;
        }
        for (Flushable flushable : flushableArray) {
            if (flushable == null) continue;
            try {
                flushable.flush();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

