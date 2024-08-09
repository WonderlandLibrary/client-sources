/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;

@SinceKotlin(version="1.2")
public class MagicApiIntrinsics {
    public static void voidMagicApiCall(Object object) {
    }

    public static <T> T anyMagicApiCall(int n) {
        return null;
    }

    public static void voidMagicApiCall(int n) {
    }

    public static int intMagicApiCall(int n) {
        return 1;
    }

    public static <T> T anyMagicApiCall(Object object) {
        return null;
    }

    public static int intMagicApiCall(Object object) {
        return 1;
    }

    public static int intMagicApiCall(int n, long l, Object object) {
        return 1;
    }

    public static int intMagicApiCall(int n, long l, long l2, Object object) {
        return 1;
    }

    public static int intMagicApiCall(int n, Object object, Object object2) {
        return 1;
    }

    public static int intMagicApiCall(int n, Object object, Object object2, Object object3, Object object4) {
        return 1;
    }

    public static <T> T anyMagicApiCall(int n, long l, Object object) {
        return null;
    }

    public static <T> T anyMagicApiCall(int n, long l, long l2, Object object) {
        return null;
    }

    public static <T> T anyMagicApiCall(int n, Object object, Object object2) {
        return null;
    }

    public static <T> T anyMagicApiCall(int n, Object object, Object object2, Object object3, Object object4) {
        return null;
    }
}

