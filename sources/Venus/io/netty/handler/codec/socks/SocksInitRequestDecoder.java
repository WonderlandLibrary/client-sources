/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socks;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socks.SocksAuthScheme;
import io.netty.handler.codec.socks.SocksCommonUtils;
import io.netty.handler.codec.socks.SocksInitRequest;
import io.netty.handler.codec.socks.SocksProtocolVersion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocksInitRequestDecoder
extends ReplayingDecoder<State> {
    public SocksInitRequestDecoder() {
        super(State.CHECK_PROTOCOL_VERSION);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$socks$SocksInitRequestDecoder$State[((State)((Object)this.state())).ordinal()]) {
            case 1: {
                if (byteBuf.readByte() != SocksProtocolVersion.SOCKS5.byteValue()) {
                    list.add(SocksCommonUtils.UNKNOWN_SOCKS_REQUEST);
                    break;
                }
                this.checkpoint(State.READ_AUTH_SCHEMES);
            }
            case 2: {
                List<SocksAuthScheme> list2;
                int n = byteBuf.readByte();
                if (n > 0) {
                    list2 = new ArrayList(n);
                    for (int i = 0; i < n; ++i) {
                        list2.add(SocksAuthScheme.valueOf(byteBuf.readByte()));
                    }
                } else {
                    list2 = Collections.emptyList();
                }
                list.add(new SocksInitRequest(list2));
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
        READ_AUTH_SCHEMES;

    }
}

