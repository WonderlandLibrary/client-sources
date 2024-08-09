/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.socksx.v4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.socksx.v4.DefaultSocks4CommandResponse;
import io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.netty.util.NetUtil;
import java.util.List;

public class Socks4ClientDecoder
extends ReplayingDecoder<State> {
    public Socks4ClientDecoder() {
        super(State.START);
        this.setSingleDecode(false);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$socksx$v4$Socks4ClientDecoder$State[((State)((Object)this.state())).ordinal()]) {
                case 1: {
                    int n = byteBuf.readUnsignedByte();
                    if (n != 0) {
                        throw new DecoderException("unsupported reply version: " + n + " (expected: 0)");
                    }
                    Socks4CommandStatus socks4CommandStatus = Socks4CommandStatus.valueOf(byteBuf.readByte());
                    int n2 = byteBuf.readUnsignedShort();
                    String string = NetUtil.intToIpAddress(byteBuf.readInt());
                    list.add(new DefaultSocks4CommandResponse(socks4CommandStatus, string, n2));
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
        DefaultSocks4CommandResponse defaultSocks4CommandResponse = new DefaultSocks4CommandResponse(Socks4CommandStatus.REJECTED_OR_FAILED);
        defaultSocks4CommandResponse.setDecoderResult(DecoderResult.failure(exception));
        list.add(defaultSocks4CommandResponse);
        this.checkpoint(State.FAILURE);
    }

    static enum State {
        START,
        SUCCESS,
        FAILURE;

    }
}

