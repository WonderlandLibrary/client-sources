/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

public final class ChannelMetadata {
    private final boolean hasDisconnect;
    private final int defaultMaxMessagesPerRead;

    public ChannelMetadata(boolean bl) {
        this(bl, 1);
    }

    public ChannelMetadata(boolean bl, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("defaultMaxMessagesPerRead: " + n + " (expected > 0)");
        }
        this.hasDisconnect = bl;
        this.defaultMaxMessagesPerRead = n;
    }

    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }

    public int defaultMaxMessagesPerRead() {
        return this.defaultMaxMessagesPerRead;
    }
}

