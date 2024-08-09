/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.SocksVersion;
import io.netty.handler.codec.socksx.v5.DefaultSocks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import java.util.List;

public class Socks5InitialRequestDecoder
extends ReplayingDecoder<State> {
    public Socks5InitialRequestDecoder() {
        super(State.INIT);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v5$Socks5InitialRequestDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readByte();
                    if (n != SocksVersion.SOCKS5.byteValue()) {
                        throw new DecoderException("unsupported version: " + n + " (expected: " + SocksVersion.SOCKS5.byteValue() + ')');
                    }
                    int n2 = byteBuf.readUnsignedByte();
                    if (this.actualReadableBytes() < n2) break;
                    Socks5AuthMethod[] socks5AuthMethodArray = new Socks5AuthMethod[n2];
                    for (int i = 0; i < n2; ++i) {
                        socks5AuthMethodArray[i] = Socks5AuthMethod.valueOf(byteBuf.readByte());
                    }
                    list.add(new DefaultSocks5InitialRequest(socks5AuthMethodArray));
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
        DefaultSocks5InitialRequest defaultSocks5InitialRequest = new DefaultSocks5InitialRequest(Socks5AuthMethod.NO_AUTH);
        defaultSocks5InitialRequest.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks5InitialRequest);
    }

    static enum State {
        INIT,
        SUCCESS,
        FAILURE;

    }
}

