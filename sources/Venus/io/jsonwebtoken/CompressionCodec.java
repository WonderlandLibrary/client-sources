/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.CompressionException;
import io.jsonwebtoken.io.CompressionAlgorithm;

@Deprecated
public interface CompressionCodec
extends CompressionAlgorithm {
    @Deprecated
    public String getAlgorithmName();

    @Deprecated
    public byte[] compress(byte[] var1) throws CompressionException;

    @Deprecated
    public byte[] decompress(byte[] var1) throws CompressionException;
}

