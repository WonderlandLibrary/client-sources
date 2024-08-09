/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.digest;

import java.util.zip.Checksum;

public class XXHash32
implements Checksum {
    private static final int BUF_SIZE = 16;
    private static final int ROTATE_BITS = 13;
    private static final int PRIME1 = -1640531535;
    private static final int PRIME2 = -2048144777;
    private static final int PRIME3 = -1028477379;
    private static final int PRIME4 = 668265263;
    private static final int PRIME5 = 374761393;
    private final byte[] oneByte = new byte[1];
    private final int[] state = new int[4];
    private final byte[] buffer = new byte[16];
    private final int seed;
    private int totalLen;
    private int pos;

    public XXHash32() {
        this(0);
    }

    public XXHash32(int n) {
        this.seed = n;
        this.initializeState();
    }

    @Override
    public void reset() {
        this.initializeState();
        this.totalLen = 0;
        this.pos = 0;
    }

    @Override
    public void update(int n) {
        this.oneByte[0] = (byte)(n & 0xFF);
        this.update(this.oneByte, 0, 1);
    }

    @Override
    public void update(byte[] byArray, int n, int n2) {
        int n3;
        if (n2 <= 0) {
            return;
        }
        this.totalLen += n2;
        int n4 = n + n2;
        if (this.pos + n2 < 16) {
            System.arraycopy(byArray, n, this.buffer, this.pos, n2);
            this.pos += n2;
            return;
        }
        if (this.pos > 0) {
            n3 = 16 - this.pos;
            System.arraycopy(byArray, n, this.buffer, this.pos, n3);
            this.process(this.buffer, 0);
            n += n3;
        }
        n3 = n4 - 16;
        while (n <= n3) {
            this.process(byArray, n);
            n += 16;
        }
        if (n < n4) {
            this.pos = n4 - n;
            System.arraycopy(byArray, n, this.buffer, 0, this.pos);
        }
    }

    @Override
    public long getValue() {
        int n;
        int n2 = this.totalLen > 16 ? Integer.rotateLeft(this.state[0], 1) + Integer.rotateLeft(this.state[1], 7) + Integer.rotateLeft(this.state[2], 12) + Integer.rotateLeft(this.state[3], 18) : this.state[2] + 374761393;
        n2 += this.totalLen;
        int n3 = this.pos - 4;
        for (n = 0; n <= n3; n += 4) {
            n2 = Integer.rotateLeft(n2 + XXHash32.getInt(this.buffer, n) * -1028477379, 17) * 668265263;
        }
        while (n < this.pos) {
            n2 = Integer.rotateLeft(n2 + (this.buffer[n++] & 0xFF) * 374761393, 11) * -1640531535;
        }
        n2 ^= n2 >>> 15;
        n2 *= -2048144777;
        n2 ^= n2 >>> 13;
        n2 *= -1028477379;
        n2 ^= n2 >>> 16;
        return (long)n2 & 0xFFFFFFFFL;
    }

    private static int getInt(byte[] byArray, int n) {
        return (int)(XXHash32.fromLittleEndian(byArray, n, 4) & 0xFFFFFFFFL);
    }

    private void initializeState() {
        this.state[0] = this.seed + -1640531535 + -2048144777;
        this.state[1] = this.seed + -2048144777;
        this.state[2] = this.seed;
        this.state[3] = this.seed - -1640531535;
    }

    private void process(byte[] byArray, int n) {
        int n2 = this.state[0];
        int n3 = this.state[1];
        int n4 = this.state[2];
        int n5 = this.state[3];
        n2 = Integer.rotateLeft(n2 + XXHash32.getInt(byArray, n) * -2048144777, 13) * -1640531535;
        n3 = Integer.rotateLeft(n3 + XXHash32.getInt(byArray, n + 4) * -2048144777, 13) * -1640531535;
        n4 = Integer.rotateLeft(n4 + XXHash32.getInt(byArray, n + 8) * -2048144777, 13) * -1640531535;
        n5 = Integer.rotateLeft(n5 + XXHash32.getInt(byArray, n + 12) * -2048144777, 13) * -1640531535;
        this.state[0] = n2;
        this.state[1] = n3;
        this.state[2] = n4;
        this.state[3] = n5;
        this.pos = 0;
    }

    private static long fromLittleEndian(byte[] byArray, int n, int n2) {
        if (n2 > 8) {
            throw new IllegalArgumentException("can't read more than eight bytes into a long value");
        }
        long l = 0L;
        for (int i = 0; i < n2; ++i) {
            l |= ((long)byArray[n + i] & 0xFFL) << 8 * i;
        }
        return l;
    }
}

