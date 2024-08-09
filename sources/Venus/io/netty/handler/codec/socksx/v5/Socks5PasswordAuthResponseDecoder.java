/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.v5.DefaultSocks5PasswordAuthResponse;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;
import java.util.List;

public class Socks5PasswordAuthResponseDecoder
extends ReplayingDecoder<State> {
    public Socks5PasswordAuthResponseDecoder() {
        super(State.INIT);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v5$Socks5PasswordAuthResponseDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readByte();
                    if (n != 1) {
                        throw new DecoderException("unsupported subnegotiation version: " + n + " (expected: 1)");
                    }
                    list.add(new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.valueOf(byteBuf.readByte())));
                    this.checkpoint(State.SUCCESS);
                }
                case 2: {
                    int n = this.actualReadableBytes();
                    if (n <= 0) break;
                    list.add(byteBuf.readRetainedSlice(n));
                    break;
                }
                case 3: {
                    byteBuf.skipBytes(this.actualReadableBytes());
                }
            }
        } catch (Exception exception) {
            this.fail(list, exception);
        }
    }

    private void fail(List<Object> list, Exception exception) {
        if (!(exception instanceof DecoderException)) {
            exception = new DecoderException(exception);
        }
        this.checkpoint(State.FAILURE);
        DefaultSocks5PasswordAuthResponse defaultSocks5PasswordAuthResponse = new DefaultSocks5PasswordAuthResponse(Socks5PasswordAuthStatus.FAILURE);
        defaultSocks5PasswordAuthResponse.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks5PasswordAuthResponse);
    }

    static enum State {
        INIT,
        SUCCESS,
        FAILURE;

    }
}

