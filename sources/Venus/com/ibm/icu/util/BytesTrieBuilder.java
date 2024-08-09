/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.StringTrieBuilder;
import java.nio.ByteBuffer;

public final class BytesTrieBuilder
extends StringTrieBuilder {
    private final byte[] intBytes = new byte[5];
    private byte[] bytes;
    private int bytesLength;
    static final boolean $assertionsDisabled = !BytesTrieBuilder.class.desiredAssertionStatus();

    public BytesTrieBuilder add(byte[] byArray, int n, int n2) {
        this.addImpl(new BytesAsCharSequence(byArray, n), n2);
        return this;
    }

    public BytesTrie build(StringTrieBuilder.Option option) {
        this.buildBytes(option);
        return new BytesTrie(this.bytes, this.bytes.length - this.bytesLength);
    }

    public ByteBuffer buildByteBuffer(StringTrieBuilder.Option option) {
        this.buildBytes(option);
        return ByteBuffer.wrap(this.bytes, this.bytes.length - this.bytesLength, this.bytesLength);
    }

    private void buildBytes(StringTrieBuilder.Option option) {
        if (this.bytes == null) {
            this.bytes = new byte[1024];
        }
        this.buildImpl(option);
    }

    public BytesTrieBuilder clear() {
        this.clearImpl();
        this.bytes = null;
        this.bytesLength = 0;
        return this;
    }

    @Override
    @Deprecated
    protected boolean matchNodesCanHaveValues() {
        return true;
    }

    @Override
    @Deprecated
    protected int getMaxBranchLinearSubNodeLength() {
        return 0;
    }

    @Override
    @Deprecated
    protected int getMinLinearMatch() {
        return 1;
    }

    @Override
    @Deprecated
    protected int getMaxLinearMatchLength() {
        return 1;
    }

    private void ensureCapacity(int n) {
        if (n > this.bytes.length) {
            int n2 = this.bytes.length;
            while ((n2 *= 2) <= n) {
            }
            byte[] byArray = new byte[n2];
            System.arraycopy(this.bytes, this.bytes.length - this.bytesLength, byArray, byArray.length - this.bytesLength, this.bytesLength);
            this.bytes = byArray;
        }
    }

    @Override
    @Deprecated
    protected int write(int n) {
        int n2 = this.bytesLength + 1;
        this.ensureCapacity(n2);
        this.bytesLength = n2;
        this.bytes[this.bytes.length - this.bytesLength] = (byte)n;
        return this.bytesLength;
    }

    @Override
    @Deprecated
    protected int write(int n, int n2) {
        int n3 = this.bytesLength + n2;
        this.ensureCapacity(n3);
        this.bytesLength = n3;
        int n4 = this.bytes.length - this.bytesLength;
        while (n2 > 0) {
            this.bytes[n4++] = (byte)this.strings.charAt(n++);
            --n2;
        }
        return this.bytesLength;
    }

    private int write(byte[] byArray, int n) {
        int n2 = this.bytesLength + n;
        this.ensureCapacity(n2);
        this.bytesLength = n2;
        System.arraycopy(byArray, 0, this.bytes, this.bytes.length - this.bytesLength, n);
        return this.bytesLength;
    }

    @Override
    @Deprecated
    protected int writeValueAndFinal(int n, boolean bl) {
        if (0 <= n && n <= 64) {
            return this.write(16 + n << 1 | (bl ? 1 : 0));
        }
        int n2 = 1;
        if (n < 0 || n > 0xFFFFFF) {
            this.intBytes[0] = 127;
            this.intBytes[1] = (byte)(n >> 24);
            this.intBytes[2] = (byte)(n >> 16);
            this.intBytes[3] = (byte)(n >> 8);
            this.intBytes[4] = (byte)n;
            n2 = 5;
        } else {
            if (n <= 6911) {
                this.intBytes[0] = (byte)(81 + (n >> 8));
            } else {
                if (n <= 0x11FFFF) {
                    this.intBytes[0] = (byte)(108 + (n >> 16));
                } else {
                    this.intBytes[0] = 126;
                    this.intBytes[1] = (byte)(n >> 16);
                    n2 = 2;
                }
                this.intBytes[n2++] = (byte)(n >> 8);
            }
            this.intBytes[n2++] = (byte)n;
        }
        this.intBytes[0] = (byte)(this.intBytes[0] << 1 | (bl ? 1 : 0));
        return this.write(this.intBytes, n2);
    }

    @Override
    @Deprecated
    protected int writeValueAndType(boolean bl, int n, int n2) {
        int n3 = this.write(n2);
        if (bl) {
            n3 = this.writeValueAndFinal(n, true);
        }
        return n3;
    }

    @Override
    @Deprecated
    protected int writeDeltaTo(int n) {
        int n2;
        int n3 = this.bytesLength - n;
        if (!$assertionsDisabled && n3 < 0) {
            throw new AssertionError();
        }
        if (n3 <= 191) {
            return this.write(n3);
        }
        if (n3 <= 12287) {
            this.intBytes[0] = (byte)(192 + (n3 >> 8));
            n2 = 1;
        } else {
            if (n3 <= 917503) {
                this.intBytes[0] = (byte)(240 + (n3 >> 16));
                n2 = 2;
            } else {
                if (n3 <= 0xFFFFFF) {
                    this.intBytes[0] = -2;
                    n2 = 3;
                } else {
                    this.intBytes[0] = -1;
                    this.intBytes[1] = (byte)(n3 >> 24);
                    n2 = 4;
                }
                this.intBytes[1] = (byte)(n3 >> 16);
            }
            this.intBytes[1] = (byte)(n3 >> 8);
        }
        this.intBytes[n2++] = (byte)n3;
        return this.write(this.intBytes, n2);
    }

    private static final class BytesAsCharSequence
    implements CharSequence {
        private byte[] s;
        private int len;

        public BytesAsCharSequence(byte[] byArray, int n) {
            this.s = byArray;
            this.len = n;
        }

        @Override
        public char charAt(int n) {
            return (char)(this.s[n] & 0xFF);
        }

        @Override
        public int length() {
            return this.len;
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            return null;
        }
    }
}

