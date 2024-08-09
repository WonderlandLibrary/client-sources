/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.AbstractTextCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

@Deprecated
public class Base64Codec
extends AbstractTextCodec {
    @Override
    public String encode(byte[] byArray) {
        return Encoders.BASE64.encode(byArray);
    }

    @Override
    public byte[] decode(String string) {
        return Decoders.BASE64.decode(string);
    }
}

