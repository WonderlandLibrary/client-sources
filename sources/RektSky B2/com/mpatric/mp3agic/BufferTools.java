package com.mpatric.mp3agic;

import java.io.*;

public class BufferTools
{
    protected static final String defaultCharsetName = "ISO-8859-1";
    
    public static String byteBufferToStringIgnoringEncodingIssues(final byte[] array, final int n, final int n2) {
        try {
            return byteBufferToString(array, n, n2, "ISO-8859-1");
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    
    public static String byteBufferToString(final byte[] array, final int n, final int n2) throws UnsupportedEncodingException {
        return byteBufferToString(array, n, n2, "ISO-8859-1");
    }
    
    public static String byteBufferToString(final byte[] array, final int n, final int n2, final String s) throws UnsupportedEncodingException {
        if (n2 < 1) {
            return "";
        }
        return new String(array, n, n2, s);
    }
    
    public static byte[] stringToByteBufferIgnoringEncodingIssues(final String s, final int n, final int n2) {
        try {
            return stringToByteBuffer(s, n, n2);
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    
    public static byte[] stringToByteBuffer(final String s, final int n, final int n2) throws UnsupportedEncodingException {
        return stringToByteBuffer(s, n, n2, "ISO-8859-1");
    }
    
    public static byte[] stringToByteBuffer(final String s, final int n, final int n2, final String s2) throws UnsupportedEncodingException {
        return s.substring(n, n + n2).getBytes(s2);
    }
    
    public static void stringIntoByteBuffer(final String s, final int n, final int n2, final byte[] array, final int n3) throws UnsupportedEncodingException {
        stringIntoByteBuffer(s, n, n2, array, n3, "ISO-8859-1");
    }
    
    public static void stringIntoByteBuffer(final String s, final int n, final int n2, final byte[] array, final int n3, final String s2) throws UnsupportedEncodingException {
        final byte[] bytes = s.substring(n, n + n2).getBytes(s2);
        if (bytes.length > 0) {
            System.arraycopy(bytes, 0, array, n3, bytes.length);
        }
    }
    
    public static String trimStringRight(final String s) {
        int n;
        for (n = s.length() - 1; n >= 0 && s.charAt(n) <= ' '; --n) {}
        if (n == s.length() - 1) {
            return s;
        }
        if (n < 0) {
            return "";
        }
        return s.substring(0, n + 1);
    }
    
    public static String padStringRight(final String s, final int n, final char c) {
        if (s.length() >= n) {
            return s;
        }
        final StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static boolean checkBit(final byte b, final int n) {
        return (b & 1 << n) != 0x0;
    }
    
    public static byte setBit(final byte b, final int n, final boolean b2) {
        byte b3;
        if (b2) {
            b3 = (byte)(b | 1 << n);
        }
        else {
            b3 = (byte)(b & ~(1 << n));
        }
        return b3;
    }
    
    public static int shiftByte(final byte b, final int n) {
        final int n2 = b & 0xFF;
        if (n < 0) {
            return n2 << -n;
        }
        if (n > 0) {
            return n2 >> n;
        }
        return n2;
    }
    
    public static int unpackInteger(final byte b, final byte b2, final byte b3, final byte b4) {
        return (b4 & 0xFF) + shiftByte(b3, -8) + shiftByte(b2, -16) + shiftByte(b, -24);
    }
    
    public static byte[] packInteger(final int n) {
        return new byte[] { (byte)(n >> 24 & 0xFF), (byte)(n >> 16 & 0xFF), (byte)(n >> 8 & 0xFF), (byte)(n & 0xFF) };
    }
    
    public static int unpackSynchsafeInteger(final byte b, final byte b2, final byte b3, final byte b4) {
        return (byte)(b4 & 0x7F) + shiftByte((byte)(b3 & 0x7F), -7) + shiftByte((byte)(b2 & 0x7F), -14) + shiftByte((byte)(b & 0x7F), -21);
    }
    
    public static byte[] packSynchsafeInteger(final int n) {
        final byte[] array = new byte[4];
        packSynchsafeInteger(n, array, 0);
        return array;
    }
    
    public static void packSynchsafeInteger(final int n, final byte[] array, final int n2) {
        array[n2 + 3] = (byte)(n & 0x7F);
        array[n2 + 2] = (byte)(n >> 7 & 0x7F);
        array[n2 + 1] = (byte)(n >> 14 & 0x7F);
        array[n2 + 0] = (byte)(n >> 21 & 0x7F);
    }
    
    public static byte[] copyBuffer(final byte[] array, final int n, final int n2) {
        final byte[] array2 = new byte[n2];
        if (n2 > 0) {
            System.arraycopy(array, n, array2, 0, n2);
        }
        return array2;
    }
    
    public static void copyIntoByteBuffer(final byte[] array, final int n, final int n2, final byte[] array2, final int n3) {
        if (n2 > 0) {
            System.arraycopy(array, n, array2, n3, n2);
        }
    }
    
    public static int sizeUnsynchronisationWouldAdd(final byte[] array) {
        int n = 0;
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i] == -1 && ((array[i + 1] & 0xFFFFFFE0) == 0xFFFFFFE0 || array[i + 1] == 0)) {
                ++n;
            }
        }
        if (array.length > 0 && array[array.length - 1] == -1) {
            ++n;
        }
        return n;
    }
    
    public static byte[] unsynchroniseBuffer(final byte[] array) {
        final int sizeUnsynchronisationWouldAdd = sizeUnsynchronisationWouldAdd(array);
        if (sizeUnsynchronisationWouldAdd == 0) {
            return array;
        }
        final byte[] array2 = new byte[array.length + sizeUnsynchronisationWouldAdd];
        int n = 0;
        for (int i = 0; i < array.length - 1; ++i) {
            array2[n++] = array[i];
            if (array[i] == -1 && ((array[i + 1] & 0xFFFFFFE0) == 0xFFFFFFE0 || array[i + 1] == 0)) {
                array2[n++] = 0;
            }
        }
        array2[n++] = array[array.length - 1];
        if (array[array.length - 1] == -1) {
            array2[n++] = 0;
        }
        return array2;
    }
    
    public static int sizeSynchronisationWouldSubtract(final byte[] array) {
        int n = 0;
        for (int i = 0; i < array.length - 2; ++i) {
            if (array[i] == -1 && array[i + 1] == 0 && ((array[i + 2] & 0xFFFFFFE0) == 0xFFFFFFE0 || array[i + 2] == 0)) {
                ++n;
            }
        }
        if (array.length > 1 && array[array.length - 2] == -1 && array[array.length - 1] == 0) {
            ++n;
        }
        return n;
    }
    
    public static byte[] synchroniseBuffer(final byte[] array) {
        final int sizeSynchronisationWouldSubtract = sizeSynchronisationWouldSubtract(array);
        if (sizeSynchronisationWouldSubtract == 0) {
            return array;
        }
        final byte[] array2 = new byte[array.length - sizeSynchronisationWouldSubtract];
        int n = 0;
        for (int i = 0; i < array2.length - 1; ++i) {
            array2[i] = array[n];
            if (array[n] == -1 && array[n + 1] == 0 && ((array[n + 2] & 0xFFFFFFE0) == 0xFFFFFFE0 || array[n + 2] == 0)) {
                ++n;
            }
            ++n;
        }
        array2[array2.length - 1] = array[n];
        return array2;
    }
    
    public static String substitute(final String s, final String s2, final String s3) {
        if (s2.length() < 1 || !s.contains(s2)) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (int index = 0; (index = s.indexOf(s2, index)) >= 0; ++index) {
            if (index > n) {
                sb.append(s.substring(n, index));
            }
            if (s3 != null) {
                sb.append(s3);
            }
            n = index + s2.length();
        }
        if (n < s.length()) {
            sb.append(s.substring(n));
        }
        return sb.toString();
    }
    
    public static String asciiOnly(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 < ' ' || char1 > '~') {
                sb.append('?');
            }
            else {
                sb.append(char1);
            }
        }
        return sb.toString();
    }
    
    public static int indexOfTerminator(final byte[] array) {
        return indexOfTerminator(array, 0);
    }
    
    public static int indexOfTerminator(final byte[] array, final int n) {
        return indexOfTerminator(array, 0, 1);
    }
    
    public static int indexOfTerminator(final byte[] array, final int n, final int n2) {
        int n3 = -1;
        for (int i = n; i <= array.length - n2; ++i) {
            if ((i - n) % n2 == 0) {
                int n4;
                for (n4 = 0; n4 < n2 && array[i + n4] == 0; ++n4) {}
                if (n4 == n2) {
                    n3 = i;
                    break;
                }
            }
        }
        return n3;
    }
    
    public static int indexOfTerminatorForEncoding(final byte[] array, final int n, final int n2) {
        return indexOfTerminator(array, n, (n2 == 1 || n2 == 2) ? 2 : 1);
    }
}
