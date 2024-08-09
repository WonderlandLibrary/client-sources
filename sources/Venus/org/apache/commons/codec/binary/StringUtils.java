/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.CharSequenceUtils;

public class StringUtils {
    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return charSequence.equals(charSequence2);
        }
        return charSequence.length() == charSequence2.length() && CharSequenceUtils.regionMatches(charSequence, false, 0, charSequence2, 0, charSequence.length());
    }

    private static byte[] getBytes(String string, Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    private static ByteBuffer getByteBuffer(String string, Charset charset) {
        if (string == null) {
            return null;
        }
        return ByteBuffer.wrap(string.getBytes(charset));
    }

    public static ByteBuffer getByteBufferUtf8(String string) {
        return StringUtils.getByteBuffer(string, Charsets.UTF_8);
    }

    public static byte[] getBytesIso8859_1(String string) {
        return StringUtils.getBytes(string, Charsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(String string, String string2) {
        if (string == null) {
            return null;
        }
        try {
            return string.getBytes(string2);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw StringUtils.newIllegalStateException(string2, unsupportedEncodingException);
        }
    }

    public static byte[] getBytesUsAscii(String string) {
        return StringUtils.getBytes(string, Charsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(String string) {
        return StringUtils.getBytes(string, Charsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String string) {
        return StringUtils.getBytes(string, Charsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String string) {
        return StringUtils.getBytes(string, Charsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String string) {
        return StringUtils.getBytes(string, Charsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(String string, UnsupportedEncodingException unsupportedEncodingException) {
        return new IllegalStateException(string + ": " + unsupportedEncodingException);
    }

    private static String newString(byte[] byArray, Charset charset) {
        return byArray == null ? null : new String(byArray, charset);
    }

    public static String newString(byte[] byArray, String string) {
        if (byArray == null) {
            return null;
        }
        try {
            return new String(byArray, string);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw StringUtils.newIllegalStateException(string, unsupportedEncodingException);
        }
    }

    public static String newStringIso8859_1(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.US_ASCII);
    }

    public static String newStringUtf16(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.UTF_16);
    }

    public static String newStringUtf16Be(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.UTF_16LE);
    }

    public static String newStringUtf8(byte[] byArray) {
        return StringUtils.newString(byArray, Charsets.UTF_8);
    }
}

