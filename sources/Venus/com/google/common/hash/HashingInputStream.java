/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

@Beta
public final class HashingInputStream
extends FilterInputStream {
    private final Hasher hasher;

    public HashingInputStream(HashFunction hashFunction, InputStream inputStream) {
        super(Preconditions.checkNotNull(inputStream));
        this.hasher = Preconditions.checkNotNull(hashFunction.newHasher());
    }

    @Override
    @CanIgnoreReturnValue
    public int read() throws IOException {
        int n = this.in.read();
        if (n != -1) {
            this.hasher.putByte((byte)n);
        }
        return n;
    }

    @Override
    @CanIgnoreReturnValue
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.in.read(byArray, n, n2);
        if (n3 != -1) {
            this.hasher.putBytes(byArray, n, n3);
        }
        return n3;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int n) {
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("reset not supported");
    }

    public HashCode hash() {
        return this.hasher.hash();
    }
}

