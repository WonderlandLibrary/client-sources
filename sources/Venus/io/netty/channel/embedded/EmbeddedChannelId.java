/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.embedded;

import io.netty.channel.ChannelId;

final class EmbeddedChannelId
implements ChannelId {
    private static final long serialVersionUID = -251711922203466130L;
    static final ChannelId INSTANCE = new EmbeddedChannelId();

    private EmbeddedChannelId() {
    }

    @Override
    public String asShortText() {
        return this.toString();
    }

    @Override
    public String asLongText() {
        return this.toString();
    }

    @Override
    public int compareTo(ChannelId channelId) {
        if (channelId instanceof EmbeddedChannelId) {
            return 1;
        }
        return this.asLongText().compareTo(channelId.asLongText());
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object object) {
        return object instanceof EmbeddedChannelId;
    }

    public String toString() {
        return "embedded";
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ChannelId)object);
    }
}

