/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

public final class LangUtils {
    public static final int HASH_SEED = 17;
    public static final int HASH_OFFSET = 37;

    private LangUtils() {
    }

    public static int hashCode(int n, int n2) {
        return n * 37 + n2;
    }

    public static int hashCode(int n, boolean bl) {
        return LangUtils.hashCode(n, bl ? 1 : 0);
    }

    public static int hashCode(int n, Object object) {
        return LangUtils.hashCode(n, object != null ? object.hashCode() : 0);
    }

    public static boolean equals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    public static boolean equals(Object[] objectArray, Object[] objectArray2) {
        if (objectArray == null) {
            return objectArray2 == null;
        }
        if (objectArray2 != null && objectArray.length == objectArray2.length) {
            for (int i = 0; i < objectArray.length; ++i) {
                if (LangUtils.equals(objectArray[i], objectArray2[i])) continue;
                return true;
            }
            return false;
        }
        return true;
    }
}

