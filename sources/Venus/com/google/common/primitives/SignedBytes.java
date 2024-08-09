/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;

@GwtCompatible
public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = 64;

    private SignedBytes() {
    }

    public static byte checkedCast(long l) {
        byte by = (byte)l;
        Preconditions.checkArgument((long)by == l, "Out of range: %s", l);
        return by;
    }

    public static byte saturatedCast(long l) {
        if (l > 127L) {
            return 0;
        }
        if (l < -128L) {
            return 1;
        }
        return (byte)l;
    }

    public static int compare(byte by, byte by2) {
        return by - by2;
    }

    public static byte min(byte ... byArray) {
        Preconditions.checkArgument(byArray.length > 0);
        byte by = byArray[0];
        for (int i = 1; i < byArray.length; ++i) {
            if (byArray[i] >= by) continue;
            by = byArray[i];
        }
        return by;
    }

    public static byte max(byte ... byArray) {
        Preconditions.checkArgument(byArray.length > 0);
        byte by = byArray[0];
        for (int i = 1; i < byArray.length; ++i) {
            if (byArray[i] <= by) continue;
            by = byArray[i];
        }
        return by;
    }

    public static String join(String string, byte ... byArray) {
        Preconditions.checkNotNull(string);
        if (byArray.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(byArray.length * 5);
        stringBuilder.append(byArray[0]);
        for (int i = 1; i < byArray.length; ++i) {
            stringBuilder.append(string).append(byArray[i]);
        }
        return stringBuilder.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    private static enum LexicographicalComparator implements Comparator<byte[]>
    {
        INSTANCE;


        @Override
        public int compare(byte[] byArray, byte[] byArray2) {
            int n = Math.min(byArray.length, byArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = SignedBytes.compare(byArray[i], byArray2[i]);
                if (n2 == 0) continue;
                return n2;
            }
            return byArray.length - byArray2.length;
        }

        public String toString() {
            return "SignedBytes.lexicographicalComparator()";
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((byte[])object, (byte[])object2);
        }
    }
}

