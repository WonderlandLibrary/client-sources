/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionException;
import io.jsonwebtoken.Header;

@Deprecated
public interface CompressionCodecResolver {
    public CompressionCodec resolveCompressionCodec(Header var1) throws CompressionException;
}

