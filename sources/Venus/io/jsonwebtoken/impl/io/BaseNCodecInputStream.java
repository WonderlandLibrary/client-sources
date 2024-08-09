/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.BaseNCodec;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

class BaseNCodecInputStream
extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];
    private final byte[] buf;
    private final BaseNCodec.Context context = new BaseNCodec.Context();

    protected BaseNCodecInputStream(InputStream inputStream, BaseNCodec baseNCodec, boolean bl) {
        super(inputStream);
        this.doEncode = bl;
        this.baseNCodec = baseNCodec;
        this.buf = new byte[bl ? 4096 : 8192];
    }

    @Override
    public int available() throws IOException {
        return this.context.eof ? 0 : 1;
    }

    public boolean isStrictDecoding() {
        return this.baseNCodec.isStrictDecoding();
    }

    @Override
    public synchronized void mark(int n) {
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        int n = this.read(this.singleByte, 0, 1);
        while (n == 0) {
            n = this.read(this.singleByte, 0, 1);
        }
        if (n > 0) {
            int n2 = this.singleByte[0];
            return n2 < 0 ? 256 + n2 : n2;
        }
        return 1;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        int n4;
        Objects.requireNonNull(byArray, "array");
        if (n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n > byArray.length || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return 1;
        }
        for (n3 = 0; n3 < n2; n3 += n4) {
            if (!this.baseNCodec.hasData(this.context)) {
                n4 = this.in.read(this.buf);
                if (this.doEncode) {
                    this.baseNCodec.encode(this.buf, 0, n4, this.context);
                } else {
                    this.baseNCodec.decode(this.buf, 0, n4, this.context);
                }
            }
            if ((n4 = this.baseNCodec.readResults(byArray, n + n3, n2 - n3, this.context)) >= 0) continue;
            return n3 != 0 ? n3 : -1;
        }
        return n3;
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    @Override
    public long skip(long l) throws IOException {
        long l2;
        int n;
        if (l < 0L) {
            throw new IllegalArgumentException("Negative skip length: " + l);
        }
        byte[] byArray = new byte[512];
        for (l2 = l; l2 > 0L; l2 -= (long)n) {
            n = (int)Math.min((long)byArray.length, l2);
            if ((n = this.read(byArray, 0, n)) == -1) break;
        }
        return l - l2;
    }
}

