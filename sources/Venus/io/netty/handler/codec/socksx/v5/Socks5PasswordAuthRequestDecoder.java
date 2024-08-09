/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.v5.DefaultSocks5PasswordAuthRequest;
import io.netty.util.CharsetUtil;
import java.util.List;

public class Socks5PasswordAuthRequestDecoder
extends ReplayingDecoder<State> {
    public Socks5PasswordAuthRequestDecoder() {
        super(State.INIT);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v5$Socks5PasswordAuthRequestDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readerIndex();
                    byte by = byteBuf.getByte(n);
                    if (by != 1) {
                        throw new DecoderException("unsupported subnegotiation version: " + by + " (expected: 1)");
                    }
                    short s = byteBuf.getUnsignedByte(n + 1);
                    short s2 = byteBuf.getUnsignedByte(n + 2 + s);
                    int n2 = s + s2 + 3;
                    byteBuf.skipBytes(n2);
                    list.add(new DefaultSocks5PasswordAuthRequest(byteBuf.toString(n + 2, s, CharsetUtil.US_ASCII), byteBuf.toString(n + 3 + s, s2, CharsetUtil.US_ASCII)));
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
        DefaultSocks5PasswordAuthRequest defaultSocks5PasswordAuthRequest = new DefaultSocks5PasswordAuthRequest("", "");
        defaultSocks5PasswordAuthRequest.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks5PasswordAuthRequest);
    }

    static enum State {
        INIT,
        SUCCESS,
        FAILURE;

    }
}

