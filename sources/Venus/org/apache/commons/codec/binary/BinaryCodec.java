/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public class BinaryCodec
implements BinaryDecoder,
BinaryEncoder {
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int BIT_0 = 1;
    private static final int BIT_1 = 2;
    private static final int BIT_2 = 4;
    private static final int BIT_3 = 8;
    private static final int BIT_4 = 16;
    private static final int BIT_5 = 32;
    private static final int BIT_6 = 64;
    private static final int BIT_7 = 128;
    private static final int[] BITS = new int[]{1, 2, 4, 8, 16, 32, 64, 128};

    @Override
    public byte[] encode(byte[] byArray) {
        return BinaryCodec.toAsciiBytes(byArray);
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof byte[])) {
            throw new EncoderException("argument not a byte array");
        }
        return BinaryCodec.toAsciiChars((byte[])object);
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return EMPTY_BYTE_ARRAY;
        }
        if (object instanceof byte[]) {
            return BinaryCodec.fromAscii((byte[])object);
        }
        if (object instanceof char[]) {
            return BinaryCodec.fromAscii((char[])object);
        }
        if (object instanceof String) {
            return BinaryCodec.fromAscii(((String)object).toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }

    @Override
    public byte[] decode(byte[] byArray) {
        return BinaryCodec.fromAscii(byArray);
    }

    public byte[] toByteArray(String string) {
        if (string == null) {
            return EMPTY_BYTE_ARRAY;
        }
        return BinaryCodec.fromAscii(string.toCharArray());
    }

    public static byte[] fromAscii(char[] cArray) {
        if (cArray == null || cArray.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] byArray = new byte[cArray.length >> 3];
        int n = 0;
        int n2 = cArray.length - 1;
        while (n < byArray.length) {
            for (int i = 0; i < BITS.length; ++i) {
                if (cArray[n2 - i] != '1') continue;
                int n3 = n;
                byArray[n3] = (byte)(byArray[n3] | BITS[i]);
            }
            ++n;
            n2 -= 8;
        }
        return byArray;
    }

    public static byte[] fromAscii(byte[] byArray) {
        if (BinaryCodec.isEmpty(byArray)) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] byArray2 = new byte[byArray.length >> 3];
        int n = 0;
        int n2 = byArray.length - 1;
        while (n < byArray2.length) {
            for (int i = 0; i < BITS.length; ++i) {
                if (byArray[n2 - i] != 49) continue;
                int n3 = n;
                byArray2[n3] = (byte)(byArray2[n3] | BITS[i]);
            }
            ++n;
            n2 -= 8;
        }
        return byArray2;
    }

    private static boolean isEmpty(byte[] byArray) {
        return byArray == null || byArray.length == 0;
    }

    public static byte[] toAsciiBytes(byte[] byArray) {
        if (BinaryCodec.isEmpty(byArray)) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] byArray2 = new byte[byArray.length << 3];
        int n = 0;
        int n2 = byArray2.length - 1;
        while (n < byArray.length) {
            for (int i = 0; i < BITS.length; ++i) {
                byArray2[n2 - i] = (byArray[n] & BITS[i]) == 0 ? 48 : 49;
            }
            ++n;
            n2 -= 8;
        }
        return byArray2;
    }

    public static char[] toAsciiChars(byte[] byArray) {
        if (BinaryCodec.isEmpty(byArray)) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] cArray = new char[byArray.length << 3];
        int n = 0;
        int n2 = cArray.length - 1;
        while (n < byArray.length) {
            for (int i = 0; i < BITS.length; ++i) {
                cArray[n2 - i] = (byArray[n] & BITS[i]) == 0 ? 48 : 49;
            }
            ++n;
            n2 -= 8;
        }
        return cArray;
    }

    public static String toAsciiString(byte[] byArray) {
        return new String(BinaryCodec.toAsciiChars(byArray));
    }
}

