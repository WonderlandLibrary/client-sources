/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.AeadResult;
import io.jsonwebtoken.security.DigestSupplier;
import io.jsonwebtoken.security.IvSupplier;
import java.io.OutputStream;

public class DefaultAeadResult
implements AeadResult,
DigestSupplier,
IvSupplier {
    private final OutputStream out;
    private byte[] tag;
    private byte[] iv;

    public DefaultAeadResult(OutputStream outputStream) {
        this.out = Assert.notNull(outputStream, "OutputStream cannot be null.");
    }

    @Override
    public OutputStream getOutputStream() {
        return this.out;
    }

    @Override
    public byte[] getDigest() {
        return this.tag;
    }

    @Override
    public AeadResult setTag(byte[] byArray) {
        this.tag = Assert.notEmpty(byArray, "Authentication Tag cannot be null or empty.");
        return this;
    }

    @Override
    public AeadResult setIv(byte[] byArray) {
        this.iv = Assert.notEmpty(byArray, "Initialization Vector cannot be null or empty.");
        return this;
    }

    @Override
    public byte[] getIv() {
        return this.iv;
    }
}

