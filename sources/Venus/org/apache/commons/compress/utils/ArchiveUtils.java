/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.UnsupportedEncodingException;
import org.apache.commons.compress.archivers.ArchiveEntry;

public class ArchiveUtils {
    private ArchiveUtils() {
    }

    public static String toString(ArchiveEntry archiveEntry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(archiveEntry.isDirectory() ? (char)'d' : '-');
        String string = Long.toString(archiveEntry.getSize());
        stringBuilder.append(' ');
        for (int i = 7; i > string.length(); --i) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(string);
        stringBuilder.append(' ').append(archiveEntry.getName());
        return stringBuilder.toString();
    }

    public static boolean matchAsciiBuffer(String string, byte[] byArray, int n, int n2) {
        byte[] byArray2;
        try {
            byArray2 = string.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
        return ArchiveUtils.isEqual(byArray2, 0, byArray2.length, byArray, n, n2, false);
    }

    public static boolean matchAsciiBuffer(String string, byte[] byArray) {
        return ArchiveUtils.matchAsciiBuffer(string, byArray, 0, byArray.length);
    }

    public static byte[] toAsciiBytes(String string) {
        try {
            return string.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String toAsciiString(byte[] byArray) {
        try {
            return new String(byArray, "US-ASCII");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String toAsciiString(byte[] byArray, int n, int n2) {
        try {
            return new String(byArray, n, n2, "US-ASCII");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static boolean isEqual(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4, boolean bl) {
        int n5;
        int n6 = n2 < n4 ? n2 : n4;
        for (n5 = 0; n5 < n6; ++n5) {
            if (byArray[n + n5] == byArray2[n3 + n5]) continue;
            return true;
        }
        if (n2 == n4) {
            return false;
        }
        if (bl) {
            if (n2 > n4) {
                for (n5 = n4; n5 < n2; ++n5) {
                    if (byArray[n + n5] == 0) continue;
                    return true;
                }
            } else {
                for (n5 = n2; n5 < n4; ++n5) {
                    if (byArray2[n3 + n5] == 0) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isEqual(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        return ArchiveUtils.isEqual(byArray, n, n2, byArray2, n3, n4, false);
    }

    public static boolean isEqual(byte[] byArray, byte[] byArray2) {
        return ArchiveUtils.isEqual(byArray, 0, byArray.length, byArray2, 0, byArray2.length, false);
    }

    public static boolean isEqual(byte[] byArray, byte[] byArray2, boolean bl) {
        return ArchiveUtils.isEqual(byArray, 0, byArray.length, byArray2, 0, byArray2.length, bl);
    }

    public static boolean isEqualWithNull(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        return ArchiveUtils.isEqual(byArray, n, n2, byArray2, n3, n4, true);
    }

    public static boolean isArrayZero(byte[] byArray, int n) {
        for (int i = 0; i < n; ++i) {
            if (byArray[i] == 0) continue;
            return true;
        }
        return false;
    }
}

