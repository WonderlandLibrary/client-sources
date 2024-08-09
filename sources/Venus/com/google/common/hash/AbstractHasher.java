/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@CanIgnoreReturnValue
abstract class AbstractHasher
implements Hasher {
    AbstractHasher() {
    }

    @Override
    public final Hasher putBoolean(boolean bl) {
        return this.putByte(bl ? (byte)1 : 0);
    }

    @Override
    public final Hasher putDouble(double d) {
        return this.putLong(Double.doubleToRawLongBits(d));
    }

    @Override
    public final Hasher putFloat(float f) {
        return this.putInt(Float.floatToRawIntBits(f));
    }

    @Override
    public Hasher putUnencodedChars(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            this.putChar(charSequence.charAt(i));
        }
        return this;
    }

    @Override
    public Hasher putString(CharSequence charSequence, Charset charset) {
        return this.putBytes(charSequence.toString().getBytes(charset));
    }

    @Override
    public PrimitiveSink putString(CharSequence charSequence, Charset charset) {
        return this.putString(charSequence, charset);
    }

    @Override
    public PrimitiveSink putUnencodedChars(CharSequence charSequence) {
        return this.putUnencodedChars(charSequence);
    }

    @Override
    public PrimitiveSink putBoolean(boolean bl) {
        return this.putBoolean(bl);
    }

    @Override
    public PrimitiveSink putDouble(double d) {
        return this.putDouble(d);
    }

    @Override
    public PrimitiveSink putFloat(float f) {
        return this.putFloat(f);
    }
}

