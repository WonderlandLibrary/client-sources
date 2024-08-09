/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.compression;

import io.jsonwebtoken.impl.compression.AbstractCompressionAlgorithm;
import io.jsonwebtoken.lang.Objects;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.InflaterOutputStream;

public class DeflateCompressionAlgorithm
extends AbstractCompressionAlgorithm {
    private static final String ID = "DEF";

    public DeflateCompressionAlgorithm() {
        super(ID);
    }

    @Override
    protected OutputStream doCompress(OutputStream outputStream) {
        return new DeflaterOutputStream(outputStream);
    }

    @Override
    protected InputStream doDecompress(InputStream inputStream) {
        return new InflaterInputStream(inputStream);
    }

    @Override
    protected byte[] doDecompress(byte[] byArray) throws IOException {
        try {
            return super.doDecompress(byArray);
        } catch (IOException iOException) {
            try {
                return this.doDecompressBackCompat(byArray);
            } catch (IOException iOException2) {
                throw iOException;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    byte[] doDecompressBackCompat(byte[] byArray) throws IOException {
        byte[] byArray2;
        InflaterOutputStream inflaterOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            inflaterOutputStream = new InflaterOutputStream(byteArrayOutputStream);
            inflaterOutputStream.write(byArray);
            inflaterOutputStream.flush();
            byArray2 = byteArrayOutputStream.toByteArray();
        } catch (Throwable throwable) {
            Objects.nullSafeClose(byteArrayOutputStream, inflaterOutputStream);
            throw throwable;
        }
        Objects.nullSafeClose(byteArrayOutputStream, inflaterOutputStream);
        return byArray2;
    }
}

