/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

final class SipHashFunction
extends AbstractStreamingHashFunction
implements Serializable {
    private final int c;
    private final int d;
    private final long k0;
    private final long k1;
    private static final long serialVersionUID = 0L;

    SipHashFunction(int n, int n2, long l, long l2) {
        Preconditions.checkArgument(n > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", n);
        Preconditions.checkArgument(n2 > 0, "The number of SipRound iterations (d=%s) during Finalization must be positive.", n2);
        this.c = n;
        this.d = n2;
        this.k0 = l;
        this.k1 = l2;
    }

    @Override
    public int bits() {
        return 1;
    }

    @Override
    public Hasher newHasher() {
        return new SipHasher(this.c, this.d, this.k0, this.k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.c + "" + this.d + "(" + this.k0 + ", " + this.k1 + ")";
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof SipHashFunction) {
            SipHashFunction sipHashFunction = (SipHashFunction)object;
            return this.c == sipHashFunction.c && this.d == sipHashFunction.d && this.k0 == sipHashFunction.k0 && this.k1 == sipHashFunction.k1;
        }
        return true;
    }

    public int hashCode() {
        return (int)((long)(this.getClass().hashCode() ^ this.c ^ this.d) ^ this.k0 ^ this.k1);
    }

    private static final class SipHasher
    extends AbstractStreamingHashFunction.AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;
        private final int c;
        private final int d;
        private long v0 = 8317987319222330741L;
        private long v1 = 7237128888997146477L;
        private long v2 = 7816392313619706465L;
        private long v3 = 8387220255154660723L;
        private long b = 0L;
        private long finalM = 0L;

        SipHasher(int n, int n2, long l, long l2) {
            super(8);
            this.c = n;
            this.d = n2;
            this.v0 ^= l;
            this.v1 ^= l2;
            this.v2 ^= l;
            this.v3 ^= l2;
        }

        @Override
        protected void process(ByteBuffer byteBuffer) {
            this.b += 8L;
            this.processM(byteBuffer.getLong());
        }

        @Override
        protected void processRemaining(ByteBuffer byteBuffer) {
            this.b += (long)byteBuffer.remaining();
            int n = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= ((long)byteBuffer.get() & 0xFFL) << n;
                n += 8;
            }
        }

        @Override
        public HashCode makeHash() {
            this.finalM ^= this.b << 56;
            this.processM(this.finalM);
            this.v2 ^= 0xFFL;
            this.sipRound(this.d);
            return HashCode.fromLong(this.v0 ^ this.v1 ^ this.v2 ^ this.v3);
        }

        private void processM(long l) {
            this.v3 ^= l;
            this.sipRound(this.c);
            this.v0 ^= l;
        }

        private void sipRound(int n) {
            for (int i = 0; i < n; ++i) {
                this.v0 += this.v1;
                this.v2 += this.v3;
                this.v1 = Long.rotateLeft(this.v1, 13);
                this.v3 = Long.rotateLeft(this.v3, 16);
                this.v1 ^= this.v0;
                this.v3 ^= this.v2;
                this.v0 = Long.rotateLeft(this.v0, 32);
                this.v2 += this.v1;
                this.v0 += this.v3;
                this.v1 = Long.rotateLeft(this.v1, 17);
                this.v3 = Long.rotateLeft(this.v3, 21);
                this.v1 ^= this.v2;
                this.v3 ^= this.v0;
                this.v2 = Long.rotateLeft(this.v2, 32);
            }
        }
    }
}

