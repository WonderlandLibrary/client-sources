/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.socks.SocksMessageType;
import io.netty.handler.codec.socks.SocksProtocolVersion;

public abstract class SocksMessage {
    private final SocksMessageType type;
    private final SocksProtocolVersion protocolVersion = SocksProtocolVersion.SOCKS5;

    protected SocksMessage(SocksMessageType type2) {
        if (type2 == null) {
            throw new NullPointerException("type");
        }
        this.type = type2;
    }

    public SocksMessageType type() {
        return this.type;
    }

    public SocksProtocolVersion protocolVersion() {
        return this.protocolVersion;
    }

    @Deprecated
    public abstract void encodeAsByteBuf(ByteBuf var1);
}

