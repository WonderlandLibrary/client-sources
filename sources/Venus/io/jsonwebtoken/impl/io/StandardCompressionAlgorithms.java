/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.compression.DeflateCompressionAlgorithm;
import io.jsonwebtoken.impl.compression.GzipCompressionAlgorithm;
import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.lang.Collections;

public final class StandardCompressionAlgorithms
extends IdRegistry<CompressionAlgorithm> {
    public static final String NAME = "Compression Algorithm";

    public StandardCompressionAlgorithms() {
        super(NAME, Collections.of(new DeflateCompressionAlgorithm(), new GzipCompressionAlgorithm()));
    }
}

