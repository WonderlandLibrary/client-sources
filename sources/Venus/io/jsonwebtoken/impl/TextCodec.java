/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.Base64UrlCodec;

@Deprecated
public interface TextCodec {
    @Deprecated
    public static final TextCodec BASE64 = new Base64Codec();
    @Deprecated
    public static final TextCodec BASE64URL = new Base64UrlCodec();

    public String encode(String var1);

    public String encode(byte[] var1);

    public byte[] decode(String var1);

    public String decodeToString(String var1);
}

