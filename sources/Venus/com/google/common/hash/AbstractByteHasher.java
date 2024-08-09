/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@CanIgnoreReturnValue
abstract class AbstractByteHasher
extends AbstractHasher {
    private final ByteBuffer scratch = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    AbstractByteHasher() {
    }

    protected abstract void update(byte var1);

    protected void update(byte[] byArray) {
        this.update(byArray, 0, byArray.length);
    }

    protected void update(byte[] byArray, int n, int n2) {
        for (int i = n; i < n + n2; ++i) {
            this.update(byArray[i]);
        }
    }

    @Override
    public Hasher putByte(byte by) {
        this.update(by);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] byArray) {
        Preconditions.checkNotNull(byArray);
        this.update(byArray);
        return this;
    }

    @Override
    public Hasher putBytes(byte[] byArray, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
        this.update(byArray, n, n2);
        return this;
    }

    private Hasher update(int n) {
        try {
            this.update(this.scratch.array(), 0, n);
        } finally {
            this.scratch.clear();
        }
        return this;
    }

    @Override
    public Hasher putShort(short s) {
        this.scratch.putShort(s);
        return this.update(2);
    }

    @Override
    public Hasher putInt(int n) {
        this.scratch.putInt(n);
        return this.update(4);
    }

    @Override
    public Hasher putLong(long l) {
        this.scratch.putLong(l);
        return this.update(8);
    }

    @Override
    public Hasher putChar(char c) {
        this.scratch.putChar(c);
        return this.update(2);
    }

    @Override
    public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
        funnel.funnel(t, this);
        return this;
    }

    @Override
    public PrimitiveSink putChar(char c) {
        return this.putChar(c);
    }

    @Override
    public PrimitiveSink putLong(long l) {
        return this.putLong(l);
    }

    @Override
    public PrimitiveSink putInt(int n) {
        return this.putInt(n);
    }

    @Override
    public PrimitiveSink putShort(short s) {
        return this.putShort(s);
    }

    @Override
    public PrimitiveSink putBytes(byte[] byArray, int n, int n2) {
        return this.putBytes(byArray, n, n2);
    }

    @Override
    public PrimitiveSink putBytes(byte[] byArray) {
        return this.putBytes(byArray);
    }

    @Override
    public PrimitiveSink putByte(byte by) {
        return this.putByte(by);
    }
}

