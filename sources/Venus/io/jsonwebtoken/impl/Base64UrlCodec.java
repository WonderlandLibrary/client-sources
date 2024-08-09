/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.AbstractTextCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;

@Deprecated
public class Base64UrlCodec
extends AbstractTextCodec {
    @Override
    public String encode(byte[] byArray) {
        return Encoders.BASE64URL.encode(byArray);
    }

    @Override
    public byte[] decode(String string) {
        return Decoders.BASE64URL.decode(string);
    }
}

