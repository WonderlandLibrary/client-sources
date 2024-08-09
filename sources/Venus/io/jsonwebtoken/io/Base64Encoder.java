/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64;
import io.jsonwebtoken.io.Base64Support;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.EncodingException;
import io.jsonwebtoken.lang.Assert;

class Base64Encoder
extends Base64Support
implements Encoder<byte[], String> {
    Base64Encoder() {
        this(Base64.DEFAULT);
    }

    Base64Encoder(Base64 base64) {
        super(base64);
    }

    @Override
    public String encode(byte[] byArray) throws EncodingException {
        Assert.notNull(byArray, "byte array argument cannot be null");
        return this.base64.encodeToString(byArray, true);
    }

    @Override
    public Object encode(Object object) throws EncodingException {
        return this.encode((byte[])object);
    }
}

