/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.hash.AbstractByteHasher;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import java.io.Serializable;
import java.util.zip.Checksum;

final class ChecksumHashFunction
extends AbstractStreamingHashFunction
implements Serializable {
    private final Supplier<? extends Checksum> checksumSupplier;
    private final int bits;
    private final String toString;
    private static final long serialVersionUID = 0L;

    ChecksumHashFunction(Supplier<? extends Checksum> supplier, int n, String string) {
        this.checksumSupplier = Preconditions.checkNotNull(supplier);
        Preconditions.checkArgument(n == 32 || n == 64, "bits (%s) must be either 32 or 64", n);
        this.bits = n;
        this.toString = Preconditions.checkNotNull(string);
    }

    @Override
    public int bits() {
        return this.bits;
    }

    @Override
    public Hasher newHasher() {
        return new ChecksumHasher(this, this.checksumSupplier.get(), null);
    }

    public String toString() {
        return this.toString;
    }

    static int access$100(ChecksumHashFunction checksumHashFunction) {
        return checksumHashFunction.bits;
    }

    private final class ChecksumHasher
    extends AbstractByteHasher {
        private final Checksum checksum;
        final ChecksumHashFunction this$0;

        private ChecksumHasher(ChecksumHashFunction checksumHashFunction, Checksum checksum) {
            this.this$0 = checksumHashFunction;
            this.checksum = Preconditions.checkNotNull(checksum);
        }

        @Override
        protected void update(byte by) {
            this.checksum.update(by);
        }

        @Override
        protected void update(byte[] byArray, int n, int n2) {
            this.checksum.update(byArray, n, n2);
        }

        @Override
        public HashCode hash() {
            long l = this.checksum.getValue();
            if (ChecksumHashFunction.access$100(this.this$0) == 32) {
                return HashCode.fromInt((int)l);
            }
            return HashCode.fromLong(l);
        }

        ChecksumHasher(ChecksumHashFunction checksumHashFunction, Checksum checksum, 1 var3_3) {
            this(checksumHashFunction, checksum);
        }
    }
}

