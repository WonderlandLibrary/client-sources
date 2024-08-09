/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.Base64OutputStream;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.EncodingException;
import java.io.OutputStream;

public final class Base64UrlStreamEncoder
implements Encoder<OutputStream, OutputStream> {
    public static final Base64UrlStreamEncoder INSTANCE = new Base64UrlStreamEncoder();

    private Base64UrlStreamEncoder() {
    }

    @Override
    public OutputStream encode(OutputStream outputStream) throws EncodingException {
        return new Base64OutputStream(outputStream);
    }

    @Override
    public Object encode(Object object) throws EncodingException {
        return this.encode((OutputStream)object);
    }
}

