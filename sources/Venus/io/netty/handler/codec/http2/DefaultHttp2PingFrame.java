/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2PingFrame;
import io.netty.util.internal.StringUtil;

public class DefaultHttp2PingFrame
implements Http2PingFrame {
    private final long content;
    private final boolean ack;

    public DefaultHttp2PingFrame(long l) {
        this(l, false);
    }

    DefaultHttp2PingFrame(long l, boolean bl) {
        this.content = l;
        this.ack = bl;
    }

    @Override
    public boolean ack() {
        return this.ack;
    }

    @Override
    public String name() {
        return "PING";
    }

    @Override
    public long content() {
        return this.content;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Http2PingFrame)) {
            return true;
        }
        Http2PingFrame http2PingFrame = (Http2PingFrame)object;
        return this.ack == http2PingFrame.ack() && this.content == http2PingFrame.content();
    }

    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + (this.ack ? 1 : 0);
        return n;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(content=" + this.content + ", ack=" + this.ack + ')';
    }
}

