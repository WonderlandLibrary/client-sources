/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socks.SocksAuthScheme;
import io.netty.handler.codec.socks.SocksCommonUtils;
import io.netty.handler.codec.socks.SocksInitResponse;
import io.netty.handler.codec.socks.SocksProtocolVersion;
import java.util.List;

public class SocksInitResponseDecoder
extends ReplayingDecoder<State> {
    public SocksInitResponseDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksInitResponseDecoder$State[((State)((Object)this.state())).ordinal()]) {
            case 1: {
                if (byteBuf.readByte() != SocksProtocolVersion.SOCKS5.byteValue()) {
                    list.add(SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE);
                    break;
                }
                this.checkpoint(State.READ_PREFERRED_AUTH_TYPE);
            }
            case 2: {
                SocksAuthScheme socksAuthScheme = SocksAuthScheme.valueOf(byteBuf.readByte());
                list.add(new SocksInitResponse(socksAuthScheme));
                break;
            }
            default: {
                throw new Error();
            }
        }
        channelHandlerContext.pipeline().remove(this);
    }

    static enum State {
        CHECK_PROTOCOL_VERSION,
        READ_PREFERRED_AUTH_TYPE;

    }
}

