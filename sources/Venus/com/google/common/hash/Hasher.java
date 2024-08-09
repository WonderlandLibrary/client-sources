/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Beta
@CanIgnoreReturnValue
public interface Hasher
extends PrimitiveSink {
    @Override
    public Hasher putByte(byte var1);

    @Override
    public Hasher putBytes(byte[] var1);

    @Override
    public Hasher putBytes(byte[] var1, int var2, int var3);

    @Override
    public Hasher putShort(short var1);

    @Override
    public Hasher putInt(int var1);

    @Override
    public Hasher putLong(long var1);

    @Override
    public Hasher putFloat(float var1);

    @Override
    public Hasher putDouble(double var1);

    @Override
    public Hasher putBoolean(boolean var1);

    @Override
    public Hasher putChar(char var1);

    @Override
    public Hasher putUnencodedChars(CharSequence var1);

    @Override
    public Hasher putString(CharSequence var1, Charset var2);

    public <T> Hasher putObject(T var1, Funnel<? super T> var2);

    public HashCode hash();

    @Deprecated
    public int hashCode();

    @Override
    default public PrimitiveSink putString(CharSequence charSequence, Charset charset) {
        return this.putString(charSequence, charset);
    }

    @Override
    default public PrimitiveSink putUnencodedChars(CharSequence charSequence) {
        return this.putUnencodedChars(charSequence);
    }

    @Override
    default public PrimitiveSink putChar(char c) {
        return this.putChar(c);
    }

    @Override
    default public PrimitiveSink putBoolean(boolean bl) {
        return this.putBoolean(bl);
    }

    @Override
    default public PrimitiveSink putDouble(double d) {
        return this.putDouble(d);
    }

    @Override
    default public PrimitiveSink putFloat(float f) {
        return this.putFloat(f);
    }

    @Override
    default public PrimitiveSink putLong(long l) {
        return this.putLong(l);
    }

    @Override
    default public PrimitiveSink putInt(int n) {
        return this.putInt(n);
    }

    @Override
    default public PrimitiveSink putShort(short s) {
        return this.putShort(s);
    }

    @Override
    default public PrimitiveSink putBytes(byte[] byArray, int n, int n2) {
        return this.putBytes(byArray, n, n2);
    }

    @Override
    default public PrimitiveSink putBytes(byte[] byArray) {
        return this.putBytes(byArray);
    }

    @Override
    default public PrimitiveSink putByte(byte by) {
        return this.putByte(by);
    }
}

