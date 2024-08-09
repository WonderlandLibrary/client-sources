/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;

public class Codec
implements Converter<byte[], CharSequence> {
    public static final Codec BASE64 = new Codec(Encoders.BASE64, Decoders.BASE64);
    public static final Codec BASE64URL = new Codec(Encoders.BASE64URL, Decoders.BASE64URL);
    private final Encoder<byte[], String> encoder;
    private final Decoder<CharSequence, byte[]> decoder;

    public Codec(Encoder<byte[], String> encoder, Decoder<CharSequence, byte[]> decoder) {
        this.encoder = Assert.notNull(encoder, "Encoder cannot be null.");
        this.decoder = Assert.notNull(decoder, "Decoder cannot be null.");
    }

    @Override
    public String applyTo(byte[] byArray) {
        return this.encoder.encode(byArray);
    }

    @Override
    public byte[] applyFrom(CharSequence charSequence) {
        try {
            return this.decoder.decode(charSequence);
        } catch (DecodingException decodingException) {
            String string = "Cannot decode input String. Cause: " + decodingException.getMessage();
            throw new IllegalArgumentException(string, decodingException);
        }
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom((CharSequence)object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((byte[])object);
    }
}

