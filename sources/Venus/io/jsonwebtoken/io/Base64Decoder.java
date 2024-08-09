/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64;
import io.jsonwebtoken.io.Base64Support;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.lang.Assert;

class Base64Decoder
extends Base64Support
implements Decoder<CharSequence, byte[]> {
    Base64Decoder() {
        super(Base64.DEFAULT);
    }

    Base64Decoder(Base64 base64) {
        super(base64);
    }

    @Override
    public byte[] decode(CharSequence charSequence) throws DecodingException {
        Assert.notNull(charSequence, "String argument cannot be null");
        return this.base64.decodeFast(charSequence);
    }

    @Override
    public Object decode(Object object) throws DecodingException {
        return this.decode((CharSequence)object);
    }
}

