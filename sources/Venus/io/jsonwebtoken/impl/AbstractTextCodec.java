/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.lang.Assert;
import java.nio.charset.Charset;

@Deprecated
public abstract class AbstractTextCodec
implements TextCodec {
    protected static final Charset UTF8 = Charset.forName("UTF-8");
    protected static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Override
    public String encode(String string) {
        Assert.hasText(string, "String argument to encode cannot be null or empty.");
        byte[] byArray = string.getBytes(UTF8);
        return this.encode(byArray);
    }

    @Override
    public String decodeToString(String string) {
        byte[] byArray = this.decode(string);
        return new String(byArray, UTF8);
    }
}

