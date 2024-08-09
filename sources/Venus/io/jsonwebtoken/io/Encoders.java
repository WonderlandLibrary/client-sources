/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Base64Encoder;
import io.jsonwebtoken.io.Base64UrlEncoder;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.ExceptionPropagatingEncoder;

public final class Encoders {
    public static final Encoder<byte[], String> BASE64 = new ExceptionPropagatingEncoder<byte[], String>(new Base64Encoder());
    public static final Encoder<byte[], String> BASE64URL = new ExceptionPropagatingEncoder<byte[], String>(new Base64UrlEncoder());

    private Encoders() {
    }
}

