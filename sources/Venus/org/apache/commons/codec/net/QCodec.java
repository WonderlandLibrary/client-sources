/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.BitSet;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.net.QuotedPrintableCodec;
import org.apache.commons.codec.net.RFC1522Codec;

public class QCodec
extends RFC1522Codec
implements StringEncoder,
StringDecoder {
    private final Charset charset;
    private static final BitSet PRINTABLE_CHARS;
    private static final byte BLANK = 32;
    private static final byte UNDERSCORE = 95;
    private boolean encodeBlanks = false;

    public QCodec() {
        this(Charsets.UTF_8);
    }

    public QCodec(Charset charset) {
        this.charset = charset;
    }

    public QCodec(String string) {
        this(Charset.forName(string));
    }

    @Override
    protected String getEncoding() {
        return "Q";
    }

    @Override
    protected byte[] doEncoding(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        byte[] byArray2 = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, byArray);
        if (this.encodeBlanks) {
            for (int i = 0; i < byArray2.length; ++i) {
                if (byArray2[i] != 32) continue;
                byArray2[i] = 95;
            }
        }
        return byArray2;
    }

    @Override
    protected byte[] doDecoding(byte[] byArray) throws DecoderException {
        if (byArray == null) {
            return null;
        }
        boolean bl = false;
        for (byte by : byArray) {
            if (by != 95) continue;
            bl = true;
            break;
        }
        if (bl) {
            byte[] byArray2 = new byte[byArray.length];
            for (int i = 0; i < byArray.length; ++i) {
                int n = byArray[i];
                byArray2[i] = n != 95 ? n : 32;
            }
            return QuotedPrintableCodec.decodeQuotedPrintable(byArray2);
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(byArray);
    }

    public String encode(String string, Charset charset) throws EncoderException {
        if (string == null) {
            return null;
        }
        return this.encodeText(string, charset);
    }

    public String encode(String string, String string2) throws EncoderException {
        if (string == null) {
            return null;
        }
        try {
            return this.encodeText(string, string2);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new EncoderException(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
        }
    }

    @Override
    public String encode(String string) throws EncoderException {
        if (string == null) {
            return null;
        }
        return this.encode(string, this.getCharset());
    }

    @Override
    public String decode(String string) throws DecoderException {
        if (string == null) {
            return null;
        }
        try {
            return this.decodeText(string);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new DecoderException(unsupportedEncodingException.getMessage(), unsupportedEncodingException);
        }
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.encode((String)object);
        }
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be encoded using Q codec");
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be decoded using Q codec");
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }

    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }

    public void setEncodeBlanks(boolean bl) {
        this.encodeBlanks = bl;
    }

    static {
        int n;
        PRINTABLE_CHARS = new BitSet(256);
        PRINTABLE_CHARS.set(32);
        PRINTABLE_CHARS.set(33);
        PRINTABLE_CHARS.set(34);
        PRINTABLE_CHARS.set(35);
        PRINTABLE_CHARS.set(36);
        PRINTABLE_CHARS.set(37);
        PRINTABLE_CHARS.set(38);
        PRINTABLE_CHARS.set(39);
        PRINTABLE_CHARS.set(40);
        PRINTABLE_CHARS.set(41);
        PRINTABLE_CHARS.set(42);
        PRINTABLE_CHARS.set(43);
        PRINTABLE_CHARS.set(44);
        PRINTABLE_CHARS.set(45);
        PRINTABLE_CHARS.set(46);
        PRINTABLE_CHARS.set(47);
        for (n = 48; n <= 57; ++n) {
            PRINTABLE_CHARS.set(n);
        }
        PRINTABLE_CHARS.set(58);
        PRINTABLE_CHARS.set(59);
        PRINTABLE_CHARS.set(60);
        PRINTABLE_CHARS.set(62);
        PRINTABLE_CHARS.set(64);
        for (n = 65; n <= 90; ++n) {
            PRINTABLE_CHARS.set(n);
        }
        PRINTABLE_CHARS.set(91);
        PRINTABLE_CHARS.set(92);
        PRINTABLE_CHARS.set(93);
        PRINTABLE_CHARS.set(94);
        PRINTABLE_CHARS.set(96);
        for (n = 97; n <= 122; ++n) {
            PRINTABLE_CHARS.set(n);
        }
        PRINTABLE_CHARS.set(123);
        PRINTABLE_CHARS.set(124);
        PRINTABLE_CHARS.set(125);
        PRINTABLE_CHARS.set(126);
    }
}

