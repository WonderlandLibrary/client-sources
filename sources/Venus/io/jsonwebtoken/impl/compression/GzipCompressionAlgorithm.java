/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.compression;

import io.jsonwebtoken.impl.compression.AbstractCompressionAlgorithm;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompressionAlgorithm
extends AbstractCompressionAlgorithm {
    private static final String ID = "GZIP";

    public GzipCompressionAlgorithm() {
        super(ID);
    }

    @Override
    protected OutputStream doCompress(OutputStream outputStream) throws IOException {
        return new GZIPOutputStream(outputStream);
    }

    @Override
    protected InputStream doDecompress(InputStream inputStream) throws IOException {
        return new GZIPInputStream(inputStream);
    }
}

