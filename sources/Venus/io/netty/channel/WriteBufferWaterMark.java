/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

public final class WriteBufferWaterMark {
    private static final int DEFAULT_LOW_WATER_MARK = 32768;
    private static final int DEFAULT_HIGH_WATER_MARK = 65536;
    public static final WriteBufferWaterMark DEFAULT = new WriteBufferWaterMark(32768, 65536, false);
    private final int low;
    private final int high;

    public WriteBufferWaterMark(int n, int n2) {
        this(n, n2, true);
    }

    WriteBufferWaterMark(int n, int n2, boolean bl) {
        if (bl) {
            if (n < 0) {
                throw new IllegalArgumentException("write buffer's low water mark must be >= 0");
            }
            if (n2 < n) {
                throw new IllegalArgumentException("write buffer's high water mark cannot be less than  low water mark (" + n + "): " + n2);
            }
        }
        this.low = n;
        this.high = n2;
    }

    public int low() {
        return this.low;
    }

    public int high() {
        return this.high;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(55).append("WriteBufferWaterMark(low: ").append(this.low).append(", high: ").append(this.high).append(")");
        return stringBuilder.toString();
    }
}

