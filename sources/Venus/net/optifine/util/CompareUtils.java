/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class CompareUtils {
    public static int hash(int n, float f) {
        return 31 * CompareUtils.hash(n) + CompareUtils.hash(f);
    }

    public static int hash(float f, float f2) {
        return 31 * CompareUtils.hash(f) + CompareUtils.hash(f2);
    }

    public static int hash(boolean bl, boolean bl2) {
        return 31 * CompareUtils.hash(bl) + CompareUtils.hash(bl2);
    }

    public static int hash(int n, Object object) {
        return 31 * CompareUtils.hash(n) + CompareUtils.hash(object);
    }

    public static int hash(Object object, boolean bl) {
        return 31 * CompareUtils.hash(object) + CompareUtils.hash(bl);
    }

    public static int hash(Object object, Object object2) {
        return 31 * CompareUtils.hash(object) + CompareUtils.hash(object2);
    }

    public static int hash(int n) {
        return n;
    }

    public static int hash(float f) {
        return Float.hashCode(f);
    }

    public static int hash(boolean bl) {
        return Boolean.hashCode(bl);
    }

    public static int hash(Object object) {
        return object == null ? 0 : object.hashCode();
    }
}

