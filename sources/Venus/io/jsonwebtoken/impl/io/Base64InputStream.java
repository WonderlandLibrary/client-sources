/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.Base64Codec;
import io.jsonwebtoken.impl.io.BaseNCodec;
import io.jsonwebtoken.impl.io.BaseNCodecInputStream;
import io.jsonwebtoken.impl.io.CodecPolicy;
import java.io.IOException;
import java.io.InputStream;

public class Base64InputStream
extends BaseNCodecInputStream {
    public Base64InputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    Base64InputStream(InputStream inputStream, boolean bl) {
        super(inputStream, new Base64Codec(0, BaseNCodec.CHUNK_SEPARATOR, false, CodecPolicy.STRICT), bl);
    }

    @Override
    public long skip(long l) throws IOException {
        return super.skip(l);
    }

    @Override
    public void reset() throws IOException {
        super.reset();
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        return super.read(byArray, n, n2);
    }

    @Override
    public int read() throws IOException {
        return super.read();
    }

    @Override
    public boolean markSupported() {
        return super.markSupported();
    }

    @Override
    public void mark(int n) {
        super.mark(n);
    }

    @Override
    public boolean isStrictDecoding() {
        return super.isStrictDecoding();
    }

    @Override
    public int available() throws IOException {
        return super.available();
    }
}

