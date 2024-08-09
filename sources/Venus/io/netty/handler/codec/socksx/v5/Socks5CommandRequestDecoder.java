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
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandRequest;
import io.netty.handler.codec.socksx.v5.Socks5AddressDecoder;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import java.util.List;

public class Socks5CommandRequestDecoder
extends ReplayingDecoder<State> {
    private final Socks5AddressDecoder addressDecoder;

    public Socks5CommandRequestDecoder() {
        this(Socks5AddressDecoder.DEFAULT);
    }

    public Socks5CommandRequestDecoder(Socks5AddressDecoder socks5AddressDecoder) {
        super(State.INIT);
        if (socks5AddressDecoder == null) {
            throw new NullPointerException("addressDecoder");
        }
        this.addressDecoder = socks5AddressDecoder;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v5$Socks5CommandRequestDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readByte();
                    if (n != SocksVersion.SOCKS5.byteValue()) {
                        throw new DecoderException("unsupported version: " + n + " (expected: " + SocksVersion.SOCKS5.byteValue() + ')');
                    }
                    Socks5CommandType socks5CommandType = Socks5CommandType.valueOf(byteBuf.readByte());
                    byteBuf.skipBytes(1);
                    Socks5AddressType socks5AddressType = Socks5AddressType.valueOf(byteBuf.readByte());
                    String string = this.addressDecoder.decodeAddress(socks5AddressType, byteBuf);
                    int n2 = byteBuf.readUnsignedShort();
                    list.add(new DefaultSocks5CommandRequest(socks5CommandType, socks5AddressType, string, n2));
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
        DefaultSocks5CommandRequest defaultSocks5CommandRequest = new DefaultSocks5CommandRequest(Socks5CommandType.CONNECT, Socks5AddressType.IPv4, "0.0.0.0", 1);
        defaultSocks5CommandRequest.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks5CommandRequest);
    }

    static enum State {
        INIT,
        SUCCESS,
        FAILURE;

    }
}

