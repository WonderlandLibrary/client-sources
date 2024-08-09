/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.RFC1522Codec;

public class BCodec
extends RFC1522Codec
implements StringEncoder,
StringDecoder {
    private final Charset charset;

    public BCodec() {
        this(Charsets.UTF_8);
    }

    public BCodec(Charset charset) {
        this.charset = charset;
    }

    public BCodec(String string) {
        this(Charset.forName(string));
    }

    @Override
    protected String getEncoding() {
        return "B";
    }

    @Override
    protected byte[] doEncoding(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return Base64.encodeBase64(byArray);
    }

    @Override
    protected byte[] doDecoding(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return Base64.decodeBase64(byArray);
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
        throw new EncoderException("Objects of type " + object.getClass().getName() + " cannot be encoded using BCodec");
    }

    @Override
    public Object decode(Object object) throws DecoderException {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return this.decode((String)object);
        }
        throw new DecoderException("Objects of type " + object.getClass().getName() + " cannot be decoded using BCodec");
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }
}

