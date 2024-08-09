/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.UUID;

public class Conversion {
    private static final boolean[] TTTT;
    private static final boolean[] FTTT;
    private static final boolean[] TFTT;
    private static final boolean[] FFTT;
    private static final boolean[] TTFT;
    private static final boolean[] FTFT;
    private static final boolean[] TFFT;
    private static final boolean[] FFFT;
    private static final boolean[] TTTF;
    private static final boolean[] FTTF;
    private static final boolean[] TFTF;
    private static final boolean[] FFTF;
    private static final boolean[] TTFF;
    private static final boolean[] FTFF;
    private static final boolean[] TFFF;
    private static final boolean[] FFFF;
    static final boolean $assertionsDisabled;

    public static int hexDigitToInt(char c) {
        int n = Character.digit(c, 16);
        if (n < 0) {
            throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
        }
        return n;
    }

    public static int hexDigitMsb0ToInt(char c) {
        switch (c) {
            case '0': {
                return 1;
            }
            case '1': {
                return 1;
            }
            case '2': {
                return 1;
            }
            case '3': {
                return 1;
            }
            case '4': {
                return 1;
            }
            case '5': {
                return 1;
            }
            case '6': {
                return 1;
            }
            case '7': {
                return 1;
            }
            case '8': {
                return 0;
            }
            case '9': {
                return 0;
            }
            case 'A': 
            case 'a': {
                return 0;
            }
            case 'B': 
            case 'b': {
                return 0;
            }
            case 'C': 
            case 'c': {
                return 0;
            }
            case 'D': 
            case 'd': {
                return 0;
            }
            case 'E': 
            case 'e': {
                return 0;
            }
            case 'F': 
            case 'f': {
                return 0;
            }
        }
        throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
    }

    public static boolean[] hexDigitToBinary(char c) {
        switch (c) {
            case '0': {
                return (boolean[])FFFF.clone();
            }
            case '1': {
                return (boolean[])TFFF.clone();
            }
            case '2': {
                return (boolean[])FTFF.clone();
            }
            case '3': {
                return (boolean[])TTFF.clone();
            }
            case '4': {
                return (boolean[])FFTF.clone();
            }
            case '5': {
                return (boolean[])TFTF.clone();
            }
            case '6': {
                return (boolean[])FTTF.clone();
            }
            case '7': {
                return (boolean[])TTTF.clone();
            }
            case '8': {
                return (boolean[])FFFT.clone();
            }
            case '9': {
                return (boolean[])TFFT.clone();
            }
            case 'A': 
            case 'a': {
                return (boolean[])FTFT.clone();
            }
            case 'B': 
            case 'b': {
                return (boolean[])TTFT.clone();
            }
            case 'C': 
            case 'c': {
                return (boolean[])FFTT.clone();
            }
            case 'D': 
            case 'd': {
                return (boolean[])TFTT.clone();
            }
            case 'E': 
            case 'e': {
                return (boolean[])FTTT.clone();
            }
            case 'F': 
            case 'f': {
                return (boolean[])TTTT.clone();
            }
        }
        throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
    }

    public static boolean[] hexDigitMsb0ToBinary(char c) {
        switch (c) {
            case '0': {
                return (boolean[])FFFF.clone();
            }
            case '1': {
                return (boolean[])FFFT.clone();
            }
            case '2': {
                return (boolean[])FFTF.clone();
            }
            case '3': {
                return (boolean[])FFTT.clone();
            }
            case '4': {
                return (boolean[])FTFF.clone();
            }
            case '5': {
                return (boolean[])FTFT.clone();
            }
            case '6': {
                return (boolean[])FTTF.clone();
            }
            case '7': {
                return (boolean[])FTTT.clone();
            }
            case '8': {
                return (boolean[])TFFF.clone();
            }
            case '9': {
                return (boolean[])TFFT.clone();
            }
            case 'A': 
            case 'a': {
                return (boolean[])TFTF.clone();
            }
            case 'B': 
            case 'b': {
                return (boolean[])TFTT.clone();
            }
            case 'C': 
            case 'c': {
                return (boolean[])TTFF.clone();
            }
            case 'D': 
            case 'd': {
                return (boolean[])TTFT.clone();
            }
            case 'E': 
            case 'e': {
                return (boolean[])TTTF.clone();
            }
            case 'F': 
            case 'f': {
                return (boolean[])TTTT.clone();
            }
        }
        throw new IllegalArgumentException("Cannot interpret '" + c + "' as a hexadecimal digit");
    }

    public static char binaryToHexDigit(boolean[] blArray) {
        return Conversion.binaryToHexDigit(blArray, 0);
    }

    public static char binaryToHexDigit(boolean[] blArray, int n) {
        if (blArray.length == 0) {
            throw new IllegalArgumentException("Cannot convert an empty array.");
        }
        if (blArray.length > n + 3 && blArray[n + 3]) {
            if (blArray.length > n + 2 && blArray[n + 2]) {
                if (blArray.length > n + 1 && blArray[n + 1]) {
                    return blArray[n] ? (char)'f' : 'e';
                }
                return blArray[n] ? (char)'d' : 'c';
            }
            if (blArray.length > n + 1 && blArray[n + 1]) {
                return blArray[n] ? (char)'b' : 'a';
            }
            return blArray[n] ? (char)'9' : '8';
        }
        if (blArray.length > n + 2 && blArray[n + 2]) {
            if (blArray.length > n + 1 && blArray[n + 1]) {
                return blArray[n] ? (char)'7' : '6';
            }
            return blArray[n] ? (char)'5' : '4';
        }
        if (blArray.length > n + 1 && blArray[n + 1]) {
            return blArray[n] ? (char)'3' : '2';
        }
        return blArray[n] ? (char)'1' : '0';
    }

    public static char binaryToHexDigitMsb0_4bits(boolean[] blArray) {
        return Conversion.binaryToHexDigitMsb0_4bits(blArray, 0);
    }

    public static char binaryToHexDigitMsb0_4bits(boolean[] blArray, int n) {
        if (blArray.length > 8) {
            throw new IllegalArgumentException("src.length>8: src.length=" + blArray.length);
        }
        if (blArray.length - n < 4) {
            throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + blArray.length + ", srcPos=" + n);
        }
        if (blArray[n + 3]) {
            if (blArray[n + 2]) {
                if (blArray[n + 1]) {
                    return blArray[n] ? (char)'f' : '7';
                }
                return blArray[n] ? (char)'b' : '3';
            }
            if (blArray[n + 1]) {
                return blArray[n] ? (char)'d' : '5';
            }
            return blArray[n] ? (char)'9' : '1';
        }
        if (blArray[n + 2]) {
            if (blArray[n + 1]) {
                return blArray[n] ? (char)'e' : '6';
            }
            return blArray[n] ? (char)'a' : '2';
        }
        if (blArray[n + 1]) {
            return blArray[n] ? (char)'c' : '4';
        }
        return blArray[n] ? (char)'8' : '0';
    }

    public static char binaryBeMsb0ToHexDigit(boolean[] blArray) {
        return Conversion.binaryBeMsb0ToHexDigit(blArray, 0);
    }

    public static char binaryBeMsb0ToHexDigit(boolean[] blArray, int n) {
        if (blArray.length == 0) {
            throw new IllegalArgumentException("Cannot convert an empty array.");
        }
        int n2 = blArray.length - 1 - n;
        int n3 = Math.min(4, n2 + 1);
        boolean[] blArray2 = new boolean[4];
        System.arraycopy(blArray, n2 + 1 - n3, blArray2, 4 - n3, n3);
        blArray = blArray2;
        n = 0;
        if (blArray[n]) {
            if (blArray.length > n + 1 && blArray[n + 1]) {
                if (blArray.length > n + 2 && blArray[n + 2]) {
                    return (char)(blArray.length > n + 3 && blArray[n + 3] ? 102 : 101);
                }
                return (char)(blArray.length > n + 3 && blArray[n + 3] ? 100 : 99);
            }
            if (blArray.length > n + 2 && blArray[n + 2]) {
                return (char)(blArray.length > n + 3 && blArray[n + 3] ? 98 : 97);
            }
            return (char)(blArray.length > n + 3 && blArray[n + 3] ? 57 : 56);
        }
        if (blArray.length > n + 1 && blArray[n + 1]) {
            if (blArray.length > n + 2 && blArray[n + 2]) {
                return (char)(blArray.length > n + 3 && blArray[n + 3] ? 55 : 54);
            }
            return (char)(blArray.length > n + 3 && blArray[n + 3] ? 53 : 52);
        }
        if (blArray.length > n + 2 && blArray[n + 2]) {
            return (char)(blArray.length > n + 3 && blArray[n + 3] ? 51 : 50);
        }
        return (char)(blArray.length > n + 3 && blArray[n + 3] ? 49 : 48);
    }

    public static char intToHexDigit(int n) {
        char c = Character.forDigit(n, 16);
        if (c == '\u0000') {
            throw new IllegalArgumentException("nibble value not between 0 and 15: " + n);
        }
        return c;
    }

    public static char intToHexDigitMsb0(int n) {
        switch (n) {
            case 0: {
                return '\u0001';
            }
            case 1: {
                return '\u0001';
            }
            case 2: {
                return '\u0001';
            }
            case 3: {
                return '\u0000';
            }
            case 4: {
                return '\u0001';
            }
            case 5: {
                return '\u0000';
            }
            case 6: {
                return '\u0001';
            }
            case 7: {
                return '\u0000';
            }
            case 8: {
                return '\u0000';
            }
            case 9: {
                return '\u0000';
            }
            case 10: {
                return '\u0000';
            }
            case 11: {
                return '\u0001';
            }
            case 12: {
                return '\u0000';
            }
            case 13: {
                return '\u0001';
            }
            case 14: {
                return '\u0000';
            }
            case 15: {
                return '\u0001';
            }
        }
        throw new IllegalArgumentException("nibble value not between 0 and 15: " + n);
    }

    public static long intArrayToLong(int[] nArray, int n, long l, int n2, int n3) {
        if (nArray.length == 0 && n == 0 || 0 == n3) {
            return l;
        }
        if ((n3 - 1) * 32 + n2 >= 64) {
            throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
        }
        long l2 = l;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 32 + n2;
            long l3 = (0xFFFFFFFFL & (long)nArray[i + n]) << n4;
            long l4 = 0xFFFFFFFFL << n4;
            l2 = l2 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l3;
        }
        return l2;
    }

    public static long shortArrayToLong(short[] sArray, int n, long l, int n2, int n3) {
        if (sArray.length == 0 && n == 0 || 0 == n3) {
            return l;
        }
        if ((n3 - 1) * 16 + n2 >= 64) {
            throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
        }
        long l2 = l;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 16 + n2;
            long l3 = (0xFFFFL & (long)sArray[i + n]) << n4;
            long l4 = 65535L << n4;
            l2 = l2 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l3;
        }
        return l2;
    }

    public static int shortArrayToInt(short[] sArray, int n, int n2, int n3, int n4) {
        if (sArray.length == 0 && n == 0 || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 16 + n3 >= 32) {
            throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        for (int i = 0; i < n4; ++i) {
            int n6 = i * 16 + n3;
            int n7 = (0xFFFF & sArray[i + n]) << n6;
            int n8 = 65535 << n6;
            n5 = n5 & ~n8 | n7;
        }
        return n5;
    }

    public static long byteArrayToLong(byte[] byArray, int n, long l, int n2, int n3) {
        if (byArray.length == 0 && n == 0 || 0 == n3) {
            return l;
        }
        if ((n3 - 1) * 8 + n2 >= 64) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
        }
        long l2 = l;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 8 + n2;
            long l3 = (0xFFL & (long)byArray[i + n]) << n4;
            long l4 = 255L << n4;
            l2 = l2 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l3;
        }
        return l2;
    }

    public static int byteArrayToInt(byte[] byArray, int n, int n2, int n3, int n4) {
        if (byArray.length == 0 && n == 0 || 0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 8 + n3 >= 32) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        for (int i = 0; i < n4; ++i) {
            int n6 = i * 8 + n3;
            int n7 = (0xFF & byArray[i + n]) << n6;
            int n8 = 255 << n6;
            n5 = n5 & ~n8 | n7;
        }
        return n5;
    }

    public static short byteArrayToShort(byte[] byArray, int n, short s, int n2, int n3) {
        if (byArray.length == 0 && n == 0 || 0 == n3) {
            return s;
        }
        if ((n3 - 1) * 8 + n2 >= 16) {
            throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
        }
        short s2 = s;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 8 + n2;
            int n5 = (0xFF & byArray[i + n]) << n4;
            int n6 = 255 << n4;
            s2 = (short)(s2 & ~n6 | n5);
        }
        return s2;
    }

    public static long hexToLong(String string, int n, long l, int n2, int n3) {
        if (0 == n3) {
            return l;
        }
        if ((n3 - 1) * 4 + n2 >= 64) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
        }
        long l2 = l;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 4 + n2;
            long l3 = (0xFL & (long)Conversion.hexDigitToInt(string.charAt(i + n))) << n4;
            long l4 = 15L << n4;
            l2 = l2 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l3;
        }
        return l2;
    }

    public static int hexToInt(String string, int n, int n2, int n3, int n4) {
        if (0 == n4) {
            return n2;
        }
        if ((n4 - 1) * 4 + n3 >= 32) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        for (int i = 0; i < n4; ++i) {
            int n6 = i * 4 + n3;
            int n7 = (0xF & Conversion.hexDigitToInt(string.charAt(i + n))) << n6;
            int n8 = 15 << n6;
            n5 = n5 & ~n8 | n7;
        }
        return n5;
    }

    public static short hexToShort(String string, int n, short s, int n2, int n3) {
        if (0 == n3) {
            return s;
        }
        if ((n3 - 1) * 4 + n2 >= 16) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
        }
        short s2 = s;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 4 + n2;
            int n5 = (0xF & Conversion.hexDigitToInt(string.charAt(i + n))) << n4;
            int n6 = 15 << n4;
            s2 = (short)(s2 & ~n6 | n5);
        }
        return s2;
    }

    public static byte hexToByte(String string, int n, byte by, int n2, int n3) {
        if (0 == n3) {
            return by;
        }
        if ((n3 - 1) * 4 + n2 >= 8) {
            throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
        }
        byte by2 = by;
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 4 + n2;
            int n5 = (0xF & Conversion.hexDigitToInt(string.charAt(i + n))) << n4;
            int n6 = 15 << n4;
            by2 = (byte)(by2 & ~n6 | n5);
        }
        return by2;
    }

    public static long binaryToLong(boolean[] blArray, int n, long l, int n2, int n3) {
        if (blArray.length == 0 && n == 0 || 0 == n3) {
            return l;
        }
        if (n3 - 1 + n2 >= 64) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 64");
        }
        long l2 = l;
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n2;
            long l3 = (blArray[i + n] ? 1L : 0L) << n4;
            long l4 = 1L << n4;
            l2 = l2 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l3;
        }
        return l2;
    }

    public static int binaryToInt(boolean[] blArray, int n, int n2, int n3, int n4) {
        if (blArray.length == 0 && n == 0 || 0 == n4) {
            return n2;
        }
        if (n4 - 1 + n3 >= 32) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 32");
        }
        int n5 = n2;
        for (int i = 0; i < n4; ++i) {
            int n6 = i + n3;
            int n7 = (blArray[i + n] ? 1 : 0) << n6;
            int n8 = 1 << n6;
            n5 = n5 & ~n8 | n7;
        }
        return n5;
    }

    public static short binaryToShort(boolean[] blArray, int n, short s, int n2, int n3) {
        if (blArray.length == 0 && n == 0 || 0 == n3) {
            return s;
        }
        if (n3 - 1 + n2 >= 16) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 16");
        }
        short s2 = s;
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n2;
            int n5 = (blArray[i + n] ? 1 : 0) << n4;
            int n6 = 1 << n4;
            s2 = (short)(s2 & ~n6 | n5);
        }
        return s2;
    }

    public static byte binaryToByte(boolean[] blArray, int n, byte by, int n2, int n3) {
        if (blArray.length == 0 && n == 0 || 0 == n3) {
            return by;
        }
        if (n3 - 1 + n2 >= 8) {
            throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 8");
        }
        byte by2 = by;
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n2;
            int n5 = (blArray[i + n] ? 1 : 0) << n4;
            int n6 = 1 << n4;
            by2 = (byte)(by2 & ~n6 | n5);
        }
        return by2;
    }

    public static int[] longToIntArray(long l, int n, int[] nArray, int n2, int n3) {
        if (0 == n3) {
            return nArray;
        }
        if ((n3 - 1) * 32 + n >= 64) {
            throw new IllegalArgumentException("(nInts-1)*32+srcPos is greather or equal to than 64");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 32 + n;
            nArray[n2 + i] = (int)(0xFFFFFFFFFFFFFFFFL & l >> n4);
        }
        return nArray;
    }

    public static short[] longToShortArray(long l, int n, short[] sArray, int n2, int n3) {
        if (0 == n3) {
            return sArray;
        }
        if ((n3 - 1) * 16 + n >= 64) {
            throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 64");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 16 + n;
            sArray[n2 + i] = (short)(0xFFFFL & l >> n4);
        }
        return sArray;
    }

    public static short[] intToShortArray(int n, int n2, short[] sArray, int n3, int n4) {
        if (0 == n4) {
            return sArray;
        }
        if ((n4 - 1) * 16 + n2 >= 32) {
            throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 32");
        }
        for (int i = 0; i < n4; ++i) {
            int n5 = i * 16 + n2;
            sArray[n3 + i] = (short)(0xFFFF & n >> n5);
        }
        return sArray;
    }

    public static byte[] longToByteArray(long l, int n, byte[] byArray, int n2, int n3) {
        if (0 == n3) {
            return byArray;
        }
        if ((n3 - 1) * 8 + n >= 64) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 64");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 8 + n;
            byArray[n2 + i] = (byte)(0xFFL & l >> n4);
        }
        return byArray;
    }

    public static byte[] intToByteArray(int n, int n2, byte[] byArray, int n3, int n4) {
        if (0 == n4) {
            return byArray;
        }
        if ((n4 - 1) * 8 + n2 >= 32) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 32");
        }
        for (int i = 0; i < n4; ++i) {
            int n5 = i * 8 + n2;
            byArray[n3 + i] = (byte)(0xFF & n >> n5);
        }
        return byArray;
    }

    public static byte[] shortToByteArray(short s, int n, byte[] byArray, int n2, int n3) {
        if (0 == n3) {
            return byArray;
        }
        if ((n3 - 1) * 8 + n >= 16) {
            throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 16");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i * 8 + n;
            byArray[n2 + i] = (byte)(0xFF & s >> n4);
        }
        return byArray;
    }

    public static String longToHex(long l, int n, String string, int n2, int n3) {
        if (0 == n3) {
            return string;
        }
        if ((n3 - 1) * 4 + n >= 64) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 64");
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        int n4 = stringBuilder.length();
        for (int i = 0; i < n3; ++i) {
            int n5 = i * 4 + n;
            int n6 = (int)(0xFL & l >> n5);
            if (n2 + i == n4) {
                ++n4;
                stringBuilder.append(Conversion.intToHexDigit(n6));
                continue;
            }
            stringBuilder.setCharAt(n2 + i, Conversion.intToHexDigit(n6));
        }
        return stringBuilder.toString();
    }

    public static String intToHex(int n, int n2, String string, int n3, int n4) {
        if (0 == n4) {
            return string;
        }
        if ((n4 - 1) * 4 + n2 >= 32) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 32");
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        int n5 = stringBuilder.length();
        for (int i = 0; i < n4; ++i) {
            int n6 = i * 4 + n2;
            int n7 = 0xF & n >> n6;
            if (n3 + i == n5) {
                ++n5;
                stringBuilder.append(Conversion.intToHexDigit(n7));
                continue;
            }
            stringBuilder.setCharAt(n3 + i, Conversion.intToHexDigit(n7));
        }
        return stringBuilder.toString();
    }

    public static String shortToHex(short s, int n, String string, int n2, int n3) {
        if (0 == n3) {
            return string;
        }
        if ((n3 - 1) * 4 + n >= 16) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 16");
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        int n4 = stringBuilder.length();
        for (int i = 0; i < n3; ++i) {
            int n5 = i * 4 + n;
            int n6 = 0xF & s >> n5;
            if (n2 + i == n4) {
                ++n4;
                stringBuilder.append(Conversion.intToHexDigit(n6));
                continue;
            }
            stringBuilder.setCharAt(n2 + i, Conversion.intToHexDigit(n6));
        }
        return stringBuilder.toString();
    }

    public static String byteToHex(byte by, int n, String string, int n2, int n3) {
        if (0 == n3) {
            return string;
        }
        if ((n3 - 1) * 4 + n >= 8) {
            throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 8");
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        int n4 = stringBuilder.length();
        for (int i = 0; i < n3; ++i) {
            int n5 = i * 4 + n;
            int n6 = 0xF & by >> n5;
            if (n2 + i == n4) {
                ++n4;
                stringBuilder.append(Conversion.intToHexDigit(n6));
                continue;
            }
            stringBuilder.setCharAt(n2 + i, Conversion.intToHexDigit(n6));
        }
        return stringBuilder.toString();
    }

    public static boolean[] longToBinary(long l, int n, boolean[] blArray, int n2, int n3) {
        if (0 == n3) {
            return blArray;
        }
        if (n3 - 1 + n >= 64) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 64");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n;
            blArray[n2 + i] = (1L & l >> n4) != 0L;
        }
        return blArray;
    }

    public static boolean[] intToBinary(int n, int n2, boolean[] blArray, int n3, int n4) {
        if (0 == n4) {
            return blArray;
        }
        if (n4 - 1 + n2 >= 32) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 32");
        }
        for (int i = 0; i < n4; ++i) {
            int n5 = i + n2;
            blArray[n3 + i] = (1 & n >> n5) != 0;
        }
        return blArray;
    }

    public static boolean[] shortToBinary(short s, int n, boolean[] blArray, int n2, int n3) {
        if (0 == n3) {
            return blArray;
        }
        if (n3 - 1 + n >= 16) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 16");
        }
        if (!$assertionsDisabled && n3 - 1 >= 16 - n) {
            throw new AssertionError();
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n;
            blArray[n2 + i] = (1 & s >> n4) != 0;
        }
        return blArray;
    }

    public static boolean[] byteToBinary(byte by, int n, boolean[] blArray, int n2, int n3) {
        if (0 == n3) {
            return blArray;
        }
        if (n3 - 1 + n >= 8) {
            throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 8");
        }
        for (int i = 0; i < n3; ++i) {
            int n4 = i + n;
            blArray[n2 + i] = (1 & by >> n4) != 0;
        }
        return blArray;
    }

    public static byte[] uuidToByteArray(UUID uUID, byte[] byArray, int n, int n2) {
        if (0 == n2) {
            return byArray;
        }
        if (n2 > 16) {
            throw new IllegalArgumentException("nBytes is greather than 16");
        }
        Conversion.longToByteArray(uUID.getMostSignificantBits(), 0, byArray, n, n2 > 8 ? 8 : n2);
        if (n2 >= 8) {
            Conversion.longToByteArray(uUID.getLeastSignificantBits(), 0, byArray, n + 8, n2 - 8);
        }
        return byArray;
    }

    public static UUID byteArrayToUuid(byte[] byArray, int n) {
        if (byArray.length - n < 16) {
            throw new IllegalArgumentException("Need at least 16 bytes for UUID");
        }
        return new UUID(Conversion.byteArrayToLong(byArray, n, 0L, 0, 8), Conversion.byteArrayToLong(byArray, n + 8, 0L, 0, 8));
    }

    static {
        $assertionsDisabled = !Conversion.class.desiredAssertionStatus();
        TTTT = new boolean[]{true, true, true, true};
        FTTT = new boolean[]{false, true, true, true};
        TFTT = new boolean[]{true, false, true, true};
        FFTT = new boolean[]{false, false, true, true};
        TTFT = new boolean[]{true, true, false, true};
        FTFT = new boolean[]{false, true, false, true};
        TFFT = new boolean[]{true, false, false, true};
        FFFT = new boolean[]{false, false, false, true};
        TTTF = new boolean[]{true, true, true, false};
        FTTF = new boolean[]{false, true, true, false};
        TFTF = new boolean[]{true, false, true, false};
        FFTF = new boolean[]{false, false, true, false};
        TTFF = new boolean[]{true, true, false, false};
        FTFF = new boolean[]{false, true, false, false};
        TFFF = new boolean[]{true, false, false, false};
        FFFF = new boolean[]{false, false, false, false};
    }
}

