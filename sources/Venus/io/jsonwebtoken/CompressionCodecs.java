/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.Jwts;

@Deprecated
public final class CompressionCodecs {
    @Deprecated
    public static final CompressionCodec DEFLATE = (CompressionCodec)Jwts.ZIP.DEF;
    @Deprecated
    public static final CompressionCodec GZIP = (CompressionCodec)Jwts.ZIP.GZIP;

    private CompressionCodecs() {
    }
}

