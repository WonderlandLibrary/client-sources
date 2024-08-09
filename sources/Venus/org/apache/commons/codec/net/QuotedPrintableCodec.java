/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.net.Utils;

public class QuotedPrintableCodec
implements BinaryEncoder,
BinaryDecoder,
StringEncoder,
StringDecoder {
    private final Charset charset;
    private final boolean strict;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte ESCAPE_CHAR = 61;
    private static final byte TAB = 9;
    private static final byte SPACE = 32;
    private static final byte CR = 13;
    private static final byte LF = 10;
    private static final int SAFE_LENGTH = 73;

    public QuotedPrintableCodec() {
        this(Charsets.UTF_8, false);
    }

    public QuotedPrintableCodec(boolean bl) {
        this(Charsets.UTF_8, bl);
    }

    public QuotedPrintableCodec(Charset charset) {
        this(charset, false);
    }

    public QuotedPrintableCodec(Charset charset, boolean bl) {
        this.charset = charset;
        this.strict = bl;
    }

    public QuotedPrintableCodec(String string) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(string), false);
    }

    private static final int encodeQuotedPrintable(int n, ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        char c = Utils.hexDigit(n >> 4);
        char c2 = Utils.hexDigit(n);
        byteArrayOutputStream.write(c);
        byteArrayOutputStream.write(c2);
        return 0;
    }

    private static int getUnsignedOctet(int n, byte[] byArray) {
        int n2 = byArray[n];
        if (n2 < 0) {
            n2 = 256 + n2;
        }
        return n2;
    }

    private static int encodeByte(int n, boolean bl, ByteArrayOutputStream byteArrayOutputStream) {
        if (bl) {
            return QuotedPrintableCodec.encodeQuotedPrintable(n, byteArrayOutputStream);
        }
        byteArrayOutputStream.write(n);
        return 0;
    }

    private static boolean isWhitespace(int n) {
        return n == 32 || n == 9;
    }

    public static final byte[] encodeQuotedPrintable(BitSet bitSet, byte[] byArray) {
        return QuotedPrintableCodec.encodeQuotedPrintable(bitSet, byArray, false);
    }

    /*
     * WARNING - void declaration
     */
    public static final byte[] encodeQuotedPrintable(BitSet bitSet, byte[] byArray, boolean bl) {
        if (byArray == null) {
            return null;
        }
        if (bitSet == null) {
            bitSet = PRINTABLE_CHARS;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bl) {
            void var7_14;
            boolean bl2;
            int n;
            int n2 = 1;
            for (n = 0; n < byArray.length - 3; ++n) {
                int bl22 = QuotedPrintableCodec.getUnsignedOctet(n, byArray);
                if (n2 < 73) {
                    n2 += QuotedPrintableCodec.encodeByte(bl22, !bitSet.get(bl22), byteArrayOutputStream);
                    continue;
                }
                QuotedPrintableCodec.encodeByte(bl22, !bitSet.get(bl22) || QuotedPrintableCodec.isWhitespace(bl22), byteArrayOutputStream);
                byteArrayOutputStream.write(61);
                byteArrayOutputStream.write(13);
                byteArrayOutputStream.write(10);
                n2 = 1;
            }
            n = QuotedPrintableCodec.getUnsignedOctet(byArray.length - 3, byArray);
            boolean bl3 = bl2 = !bitSet.get(n) || QuotedPrintableCodec.isWhitespace(n) && n2 > 68;
            if ((n2 += QuotedPrintableCodec.encodeByte(n, bl2, byteArrayOutputStream)) > 71) {
                byteArrayOutputStream.write(61);
                byteArrayOutputStream.write(13);
                byteArrayOutputStream.write(10);
            }
            int n3 = byArray.length - 2;
            while (var7_14 < byArray.length) {
                n = QuotedPrintableCodec.getUnsignedOctet((int)var7_14, byArray);
                boolean bl4 = !bitSet.get(n) || var7_14 > byArray.length - 2 && QuotedPrintableCodec.isWhitespace(n);
                QuotedPrintableCodec.encodeByte(n, bl4, byteArrayOutputStream);
                ++var7_14;
            }
        } else {
            void var6_12;
            byte[] byArray2 = byArray;
            int n = byArray2.length;
            boolean bl5 = false;
            while (var6_12 < n) {
                int n4 = byArray2[var6_12];
                int n5 = n4;
                if (n5 < 0) {
                    n5 = 256 + n5;
                }
                if (bitSet.get(n5)) {
                    byteArrayOutputStream.write(n5);
                } else {
                    QuotedPrintableCodec.encodeQuotedPrintable(n5, byteArrayOutputStream);
                }
                ++var6_12;
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static final byte[] decodeQuotedPrintable(byte[] byArray) throws DecoderException {
        if (byArray == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < byArray.length; ++i) {
            byte by = byArray[i];
            if (by == 61) {
                try {
                    if (byArray[++i] == 13) continue;
                    int n = Utils.digit16(byArray[i]);
                    int n2 = Utils.digit16(byArray[++i]);
                    byteArrayOutputStream.write((char)((n << 4) + n2));
                    continue;
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    throw new DecoderException("Invalid quoted-printable encoding", arrayIndexOutOfBoundsException);
                }
            }
            if (by == 13 || by == 10) continue;
            byteArrayOutputStream.write(by);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] encode(byte[] byArray) {
        return QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, byArray, this.strict);
    }

    @Override
    public byte[] decode(byte[] byArray) throws DecoderException {
        return QuotedPrintableCodec.decodeQuotedPrintable(byArray);
    }

    @Override
    public String encode(String string) throws EncoderException {
        return this.encode(string, this.getCharset());
    }

    public String decode(String string, Charset charset) throws DecoderException {
        if (string == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(string)), charset);
    }

    public String decode(String string, String string2) throws DecoderException, UnsupportedEncodingException {
        if (string == null) {
            return null;
        }
        return new String(this.decode(StringUtils.getBytesUsAscii(string)), string2);
    }

    @Override
    public String decode(String string) throws DecoderException {
        return this.decode(string, this.getCharset());
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof byte[]) {
            return this.encode((byte[])object);
        }
        if (object instanceof String) {
            return this.encode((String)object);
        }
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be quoted-printable encoded");
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof byte[]) {
            return this.decode((byte[])object);
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be quoted-printable decoded");
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }

    public String encode(String string, Charset charset) {
        if (string == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(string.getBytes(charset)));
    }

    public String encode(String string, String string2) throws UnsupportedEncodingException {
        if (string == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(this.encode(string.getBytes(string2)));
    }

    static {
        int n;
        PRINTABLE_CHARS = new BitSet(256);
        for (n = 33; n <= 60; ++n) {
            PRINTABLE_CHARS.set(n);
        }
        for (n = 62; n <= 126; ++n) {
            PRINTABLE_CHARS.set(n);
        }
        PRINTABLE_CHARS.set(9);
        PRINTABLE_CHARS.set(32);
    }
}

