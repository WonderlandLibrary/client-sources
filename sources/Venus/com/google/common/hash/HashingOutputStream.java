/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Beta
public final class HashingOutputStream
extends FilterOutputStream {
    private final Hasher hasher;

    public HashingOutputStream(HashFunction hashFunction, OutputStream outputStream) {
        super(Preconditions.checkNotNull(outputStream));
        this.hasher = Preconditions.checkNotNull(hashFunction.newHasher());
    }

    @Override
    public void write(int n) throws IOException {
        this.hasher.putByte((byte)n);
        this.out.write(n);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.hasher.putBytes(byArray, n, n2);
        this.out.write(byArray, n, n2);
    }

    public HashCode hash() {
        return this.hasher.hash();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}

