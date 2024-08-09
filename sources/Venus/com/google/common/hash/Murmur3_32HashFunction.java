/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

final class Murmur3_32HashFunction
extends AbstractStreamingHashFunction
implements Serializable {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private final int seed;
    private static final long serialVersionUID = 0L;

    Murmur3_32HashFunction(int n) {
        this.seed = n;
    }

    @Override
    public int bits() {
        return 1;
    }

    @Override
    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_32(" + this.seed + ")";
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof Murmur3_32HashFunction) {
            Murmur3_32HashFunction murmur3_32HashFunction = (Murmur3_32HashFunction)object;
            return this.seed == murmur3_32HashFunction.seed;
        }
        return true;
    }

    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }

    @Override
    public HashCode hashInt(int n) {
        int n2 = Murmur3_32HashFunction.mixK1(n);
        int n3 = Murmur3_32HashFunction.mixH1(this.seed, n2);
        return Murmur3_32HashFunction.fmix(n3, 4);
    }

    @Override
    public HashCode hashLong(long l) {
        int n = (int)l;
        int n2 = (int)(l >>> 32);
        int n3 = Murmur3_32HashFunction.mixK1(n);
        int n4 = Murmur3_32HashFunction.mixH1(this.seed, n3);
        n3 = Murmur3_32HashFunction.mixK1(n2);
        n4 = Murmur3_32HashFunction.mixH1(n4, n3);
        return Murmur3_32HashFunction.fmix(n4, 8);
    }

    @Override
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int n;
        int n2 = this.seed;
        for (n = 1; n < charSequence.length(); n += 2) {
            int n3 = charSequence.charAt(n - 1) | charSequence.charAt(n) << 16;
            n3 = Murmur3_32HashFunction.mixK1(n3);
            n2 = Murmur3_32HashFunction.mixH1(n2, n3);
        }
        if ((charSequence.length() & 1) == 1) {
            n = charSequence.charAt(charSequence.length() - 1);
            n = Murmur3_32HashFunction.mixK1(n);
            n2 ^= n;
        }
        return Murmur3_32HashFunction.fmix(n2, 2 * charSequence.length());
    }

    private static int mixK1(int n) {
        n *= -862048943;
        n = Integer.rotateLeft(n, 15);
        return n *= 461845907;
    }

    private static int mixH1(int n, int n2) {
        n ^= n2;
        n = Integer.rotateLeft(n, 13);
        n = n * 5 + -430675100;
        return n;
    }

    private static HashCode fmix(int n, int n2) {
        n ^= n2;
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        n *= -1028477387;
        n ^= n >>> 16;
        return HashCode.fromInt(n);
    }

    static int access$000(int n) {
        return Murmur3_32HashFunction.mixK1(n);
    }

    static int access$100(int n, int n2) {
        return Murmur3_32HashFunction.mixH1(n, n2);
    }

    static HashCode access$200(int n, int n2) {
        return Murmur3_32HashFunction.fmix(n, n2);
    }

    private static final class Murmur3_32Hasher
    extends AbstractStreamingHashFunction.AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 4;
        private int h1;
        private int length;

        Murmur3_32Hasher(int n) {
            super(4);
            this.h1 = n;
            this.length = 0;
        }

        @Override
        protected void process(ByteBuffer byteBuffer) {
            int n = Murmur3_32HashFunction.access$000(byteBuffer.getInt());
            this.h1 = Murmur3_32HashFunction.access$100(this.h1, n);
            this.length += 4;
        }

        @Override
        protected void processRemaining(ByteBuffer byteBuffer) {
            this.length += byteBuffer.remaining();
            int n = 0;
            int n2 = 0;
            while (byteBuffer.hasRemaining()) {
                n ^= UnsignedBytes.toInt(byteBuffer.get()) << n2;
                n2 += 8;
            }
            this.h1 ^= Murmur3_32HashFunction.access$000(n);
        }

        @Override
        public HashCode makeHash() {
            return Murmur3_32HashFunction.access$200(this.h1, this.length);
        }
    }
}

