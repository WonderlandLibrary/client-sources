/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64Decoder;
import io.jsonwebtoken.io.Base64UrlDecoder;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.ExceptionPropagatingDecoder;

public final class Decoders {
    public static final Decoder<CharSequence, byte[]> BASE64 = new ExceptionPropagatingDecoder<CharSequence, byte[]>(new Base64Decoder());
    public static final Decoder<CharSequence, byte[]> BASE64URL = new ExceptionPropagatingDecoder<CharSequence, byte[]>(new Base64UrlDecoder());

    private Decoders() {
    }
}

