/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoderByteBuf;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ReplayingDecoder<S>
extends ByteToMessageDecoder {
    static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
    private final ReplayingDecoderByteBuf replayable = new ReplayingDecoderByteBuf();
    private S state;
    private int checkpoint = -1;

    protected ReplayingDecoder() {
        this(null);
    }

    protected ReplayingDecoder(S s) {
        this.state = s;
    }

    protected void checkpoint() {
        this.checkpoint = this.internalBuffer().readerIndex();
    }

    protected void checkpoint(S s) {
        this.checkpoint();
        this.state(s);
    }

    protected S state() {
        return this.state;
    }

    protected S state(S s) {
        S s2 = this.state;
        this.state = s;
        return s2;
    }

    @Override
    final void channelInputClosed(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        try {
            this.replayable.terminate();
            if (this.cumulation != null) {
                this.callDecode(channelHandlerContext, this.internalBuffer(), list);
            } else {
                this.replayable.setCumulation(Unpooled.EMPTY_BUFFER);
            }
            this.decodeLast(channelHandlerContext, this.replayable, list);
        } catch (Signal signal) {
            signal.expect(REPLAY);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        this.replayable.setCumulation(byteBuf);
        try {
            while (byteBuf.isReadable()) {
                int n = this.checkpoint = byteBuf.readerIndex();
                int n2 = list.size();
                if (n2 > 0) {
                    ReplayingDecoder.fireChannelRead(channelHandlerContext, list, n2);
                    list.clear();
                    if (channelHandlerContext.isRemoved()) {
                        return;
                    }
                    n2 = 0;
                }
                S s = this.state;
                int n3 = byteBuf.readableBytes();
                try {
                    this.decodeRemovalReentryProtection(channelHandlerContext, this.replayable, list);
                    if (channelHandlerContext.isRemoved()) {
                        return;
                    }
                    if (n2 == list.size()) {
                        if (n3 != byteBuf.readableBytes() || s != this.state) continue;
                        throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() must consume the inbound data or change its state if it did not decode anything.");
                    }
                } catch (Signal signal) {
                    signal.expect(REPLAY);
                    if (channelHandlerContext.isRemoved()) {
                        return;
                    }
                    int n4 = this.checkpoint;
                    if (n4 < 0) return;
                    byteBuf.readerIndex(n4);
                    return;
                }
                if (n == byteBuf.readerIndex() && s == this.state) {
                    throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() method must consume the inbound data or change its state if it decoded something.");
                }
                if (this.isSingleDecode()) return;
            }
            return;
        } catch (DecoderException decoderException) {
            throw decoderException;
        } catch (Exception exception) {
            throw new DecoderException(exception);
        }
    }
}

