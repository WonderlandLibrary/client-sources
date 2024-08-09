/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socks.SocksAuthRequest;
import io.netty.handler.codec.socks.SocksCommonUtils;
import io.netty.handler.codec.socks.SocksSubnegotiationVersion;
import java.util.List;

public class SocksAuthRequestDecoder
extends ReplayingDecoder<State> {
    private String username;

    public SocksAuthRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksAuthRequestDecoder$State[((State)((Object)this.state())).ordinal()]) {
            case 1: {
                if (byteBuf.readByte() != SocksSubnegotiationVersion.AUTH_PASSWORD.byteValue()) {
                    list.add(SocksCommonUtils.UNKNOWN_SOCKS_REQUEST);
                    break;
                }
                this.checkpoint(State.READ_USERNAME);
            }
            case 2: {
                byte by = byteBuf.readByte();
                this.username = SocksCommonUtils.readUsAscii(byteBuf, by);
                this.checkpoint(State.READ_PASSWORD);
            }
            case 3: {
                byte by = byteBuf.readByte();
                String string = SocksCommonUtils.readUsAscii(byteBuf, by);
                list.add(new SocksAuthRequest(this.username, string));
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
        READ_USERNAME,
        READ_PASSWORD;

    }
}

