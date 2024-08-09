/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelId;

final class Http2StreamChannelId
implements ChannelId {
    private static final long serialVersionUID = -6642338822166867585L;
    private final int id;
    private final ChannelId parentId;

    Http2StreamChannelId(ChannelId channelId, int n) {
        this.parentId = channelId;
        this.id = n;
    }

    @Override
    public String asShortText() {
        return this.parentId.asShortText() + '/' + this.id;
    }

    @Override
    public String asLongText() {
        return this.parentId.asLongText() + '/' + this.id;
    }

    @Override
    public int compareTo(ChannelId channelId) {
        if (channelId instanceof Http2StreamChannelId) {
            Http2StreamChannelId http2StreamChannelId = (Http2StreamChannelId)channelId;
            int n = this.parentId.compareTo(http2StreamChannelId.parentId);
            if (n == 0) {
                return this.id - http2StreamChannelId.id;
            }
            return n;
        }
        return this.parentId.compareTo(channelId);
    }

    public int hashCode() {
        return this.id * 31 + this.parentId.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof Http2StreamChannelId)) {
            return true;
        }
        Http2StreamChannelId http2StreamChannelId = (Http2StreamChannelId)object;
        return this.id == http2StreamChannelId.id && this.parentId.equals(http2StreamChannelId.parentId);
    }

    public String toString() {
        return this.asShortText();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ChannelId)object);
    }
}

