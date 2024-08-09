/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.spdy.DefaultSpdyDataFrame;
import io.netty.handler.codec.spdy.DefaultSpdyGoAwayFrame;
import io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame;
import io.netty.handler.codec.spdy.DefaultSpdyPingFrame;
import io.netty.handler.codec.spdy.DefaultSpdyRstStreamFrame;
import io.netty.handler.codec.spdy.DefaultSpdySettingsFrame;
import io.netty.handler.codec.spdy.DefaultSpdySynReplyFrame;
import io.netty.handler.codec.spdy.DefaultSpdySynStreamFrame;
import io.netty.handler.codec.spdy.DefaultSpdyWindowUpdateFrame;
import io.netty.handler.codec.spdy.SpdyDataFrame;
import io.netty.handler.codec.spdy.SpdyFrameDecoder;
import io.netty.handler.codec.spdy.SpdyFrameDecoderDelegate;
import io.netty.handler.codec.spdy.SpdyFrameEncoder;
import io.netty.handler.codec.spdy.SpdyGoAwayFrame;
import io.netty.handler.codec.spdy.SpdyHeaderBlockDecoder;
import io.netty.handler.codec.spdy.SpdyHeaderBlockEncoder;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyPingFrame;
import io.netty.handler.codec.spdy.SpdyProtocolException;
import io.netty.handler.codec.spdy.SpdyRstStreamFrame;
import io.netty.handler.codec.spdy.SpdySettingsFrame;
import io.netty.handler.codec.spdy.SpdySynReplyFrame;
import io.netty.handler.codec.spdy.SpdySynStreamFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.handler.codec.spdy.SpdyWindowUpdateFrame;
import io.netty.util.concurrent.Future;
import java.net.SocketAddress;
import java.util.List;

public class SpdyFrameCodec
extends ByteToMessageDecoder
implements SpdyFrameDecoderDelegate,
ChannelOutboundHandler {
    private static final SpdyProtocolException INVALID_FRAME = new SpdyProtocolException("Received invalid frame");
    private final SpdyFrameDecoder spdyFrameDecoder;
    private final SpdyFrameEncoder spdyFrameEncoder;
    private final SpdyHeaderBlockDecoder spdyHeaderBlockDecoder;
    private final SpdyHeaderBlockEncoder spdyHeaderBlockEncoder;
    private SpdyHeadersFrame spdyHeadersFrame;
    private SpdySettingsFrame spdySettingsFrame;
    private ChannelHandlerContext ctx;
    private boolean read;
    private final boolean validateHeaders;

    public SpdyFrameCodec(SpdyVersion spdyVersion) {
        this(spdyVersion, true);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, boolean bl) {
        this(spdyVersion, 8192, 16384, 6, 15, 8, bl);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, int n, int n2, int n3, int n4, int n5) {
        this(spdyVersion, n, n2, n3, n4, n5, true);
    }

    public SpdyFrameCodec(SpdyVersion spdyVersion, int n, int n2, int n3, int n4, int n5, boolean bl) {
        this(spdyVersion, n, SpdyHeaderBlockDecoder.newInstance(spdyVersion, n2), SpdyHeaderBlockEncoder.newInstance(spdyVersion, n3, n4, n5), bl);
    }

    protected SpdyFrameCodec(SpdyVersion spdyVersion, int n, SpdyHeaderBlockDecoder spdyHeaderBlockDecoder, SpdyHeaderBlockEncoder spdyHeaderBlockEncoder, boolean bl) {
        this.spdyFrameDecoder = new SpdyFrameDecoder(spdyVersion, this, n);
        this.spdyFrameEncoder = new SpdyFrameEncoder(spdyVersion);
        this.spdyHeaderBlockDecoder = spdyHeaderBlockDecoder;
        this.spdyHeaderBlockEncoder = spdyHeaderBlockEncoder;
        this.validateHeaders = bl;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerAdded(channelHandlerContext);
        this.ctx = channelHandlerContext;
        channelHandlerContext.channel().closeFuture().addListener(new ChannelFutureListener(this){
            final SpdyFrameCodec this$0;
            {
                this.this$0 = spdyFrameCodec;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                SpdyFrameCodec.access$000(this.this$0).end();
                SpdyFrameCodec.access$100(this.this$0).end();
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        this.spdyFrameDecoder.decode(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.read && !channelHandlerContext.channel().config().isAutoRead()) {
            channelHandlerContext.read();
        }
        this.read = false;
        super.channelReadComplete(channelHandlerContext);
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (object instanceof SpdyDataFrame) {
            SpdyDataFrame spdyDataFrame = (SpdyDataFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodeDataFrame(channelHandlerContext.alloc(), spdyDataFrame.streamId(), spdyDataFrame.isLast(), spdyDataFrame.content());
            spdyDataFrame.release();
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdySynStreamFrame) {
            ByteBuf byteBuf;
            SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)object;
            ByteBuf byteBuf2 = this.spdyHeaderBlockEncoder.encode(channelHandlerContext.alloc(), spdySynStreamFrame);
            try {
                byteBuf = this.spdyFrameEncoder.encodeSynStreamFrame(channelHandlerContext.alloc(), spdySynStreamFrame.streamId(), spdySynStreamFrame.associatedStreamId(), spdySynStreamFrame.priority(), spdySynStreamFrame.isLast(), spdySynStreamFrame.isUnidirectional(), byteBuf2);
            } finally {
                byteBuf2.release();
            }
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdySynReplyFrame) {
            ByteBuf byteBuf;
            SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)object;
            ByteBuf byteBuf3 = this.spdyHeaderBlockEncoder.encode(channelHandlerContext.alloc(), spdySynReplyFrame);
            try {
                byteBuf = this.spdyFrameEncoder.encodeSynReplyFrame(channelHandlerContext.alloc(), spdySynReplyFrame.streamId(), spdySynReplyFrame.isLast(), byteBuf3);
            } finally {
                byteBuf3.release();
            }
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdyRstStreamFrame) {
            SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodeRstStreamFrame(channelHandlerContext.alloc(), spdyRstStreamFrame.streamId(), spdyRstStreamFrame.status().code());
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdySettingsFrame) {
            SpdySettingsFrame spdySettingsFrame = (SpdySettingsFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodeSettingsFrame(channelHandlerContext.alloc(), spdySettingsFrame);
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdyPingFrame) {
            SpdyPingFrame spdyPingFrame = (SpdyPingFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodePingFrame(channelHandlerContext.alloc(), spdyPingFrame.id());
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdyGoAwayFrame) {
            SpdyGoAwayFrame spdyGoAwayFrame = (SpdyGoAwayFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodeGoAwayFrame(channelHandlerContext.alloc(), spdyGoAwayFrame.lastGoodStreamId(), spdyGoAwayFrame.status().code());
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdyHeadersFrame) {
            ByteBuf byteBuf;
            SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)object;
            ByteBuf byteBuf4 = this.spdyHeaderBlockEncoder.encode(channelHandlerContext.alloc(), spdyHeadersFrame);
            try {
                byteBuf = this.spdyFrameEncoder.encodeHeadersFrame(channelHandlerContext.alloc(), spdyHeadersFrame.streamId(), spdyHeadersFrame.isLast(), byteBuf4);
            } finally {
                byteBuf4.release();
            }
            channelHandlerContext.write(byteBuf, channelPromise);
        } else if (object instanceof SpdyWindowUpdateFrame) {
            SpdyWindowUpdateFrame spdyWindowUpdateFrame = (SpdyWindowUpdateFrame)object;
            ByteBuf byteBuf = this.spdyFrameEncoder.encodeWindowUpdateFrame(channelHandlerContext.alloc(), spdyWindowUpdateFrame.streamId(), spdyWindowUpdateFrame.deltaWindowSize());
            channelHandlerContext.write(byteBuf, channelPromise);
        } else {
            throw new UnsupportedMessageTypeException(object, new Class[0]);
        }
    }

    @Override
    public void readDataFrame(int n, boolean bl, ByteBuf byteBuf) {
        this.read = true;
        DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(n, byteBuf);
        defaultSpdyDataFrame.setLast(bl);
        this.ctx.fireChannelRead(defaultSpdyDataFrame);
    }

    @Override
    public void readSynStreamFrame(int n, int n2, byte by, boolean bl, boolean bl2) {
        DefaultSpdySynStreamFrame defaultSpdySynStreamFrame = new DefaultSpdySynStreamFrame(n, n2, by, this.validateHeaders);
        defaultSpdySynStreamFrame.setLast(bl);
        defaultSpdySynStreamFrame.setUnidirectional(bl2);
        this.spdyHeadersFrame = defaultSpdySynStreamFrame;
    }

    @Override
    public void readSynReplyFrame(int n, boolean bl) {
        DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(n, this.validateHeaders);
        defaultSpdySynReplyFrame.setLast(bl);
        this.spdyHeadersFrame = defaultSpdySynReplyFrame;
    }

    @Override
    public void readRstStreamFrame(int n, int n2) {
        this.read = true;
        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, n2);
        this.ctx.fireChannelRead(defaultSpdyRstStreamFrame);
    }

    @Override
    public void readSettingsFrame(boolean bl) {
        this.read = true;
        this.spdySettingsFrame = new DefaultSpdySettingsFrame();
        this.spdySettingsFrame.setClearPreviouslyPersistedSettings(bl);
    }

    @Override
    public void readSetting(int n, int n2, boolean bl, boolean bl2) {
        this.spdySettingsFrame.setValue(n, n2, bl, bl2);
    }

    @Override
    public void readSettingsEnd() {
        this.read = true;
        SpdySettingsFrame spdySettingsFrame = this.spdySettingsFrame;
        this.spdySettingsFrame = null;
        this.ctx.fireChannelRead(spdySettingsFrame);
    }

    @Override
    public void readPingFrame(int n) {
        this.read = true;
        DefaultSpdyPingFrame defaultSpdyPingFrame = new DefaultSpdyPingFrame(n);
        this.ctx.fireChannelRead(defaultSpdyPingFrame);
    }

    @Override
    public void readGoAwayFrame(int n, int n2) {
        this.read = true;
        DefaultSpdyGoAwayFrame defaultSpdyGoAwayFrame = new DefaultSpdyGoAwayFrame(n, n2);
        this.ctx.fireChannelRead(defaultSpdyGoAwayFrame);
    }

    @Override
    public void readHeadersFrame(int n, boolean bl) {
        this.spdyHeadersFrame = new DefaultSpdyHeadersFrame(n, this.validateHeaders);
        this.spdyHeadersFrame.setLast(bl);
    }

    @Override
    public void readWindowUpdateFrame(int n, int n2) {
        this.read = true;
        DefaultSpdyWindowUpdateFrame defaultSpdyWindowUpdateFrame = new DefaultSpdyWindowUpdateFrame(n, n2);
        this.ctx.fireChannelRead(defaultSpdyWindowUpdateFrame);
    }

    @Override
    public void readHeaderBlock(ByteBuf byteBuf) {
        try {
            this.spdyHeaderBlockDecoder.decode(this.ctx.alloc(), byteBuf, this.spdyHeadersFrame);
        } catch (Exception exception) {
            this.ctx.fireExceptionCaught(exception);
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void readHeaderBlockEnd() {
        SpdyHeadersFrame spdyHeadersFrame = null;
        try {
            this.spdyHeaderBlockDecoder.endHeaderBlock(this.spdyHeadersFrame);
            spdyHeadersFrame = this.spdyHeadersFrame;
            this.spdyHeadersFrame = null;
        } catch (Exception exception) {
            this.ctx.fireExceptionCaught(exception);
        }
        if (spdyHeadersFrame != null) {
            this.read = true;
            this.ctx.fireChannelRead(spdyHeadersFrame);
        }
    }

    @Override
    public void readFrameError(String string) {
        this.ctx.fireExceptionCaught(INVALID_FRAME);
    }

    static SpdyHeaderBlockDecoder access$000(SpdyFrameCodec spdyFrameCodec) {
        return spdyFrameCodec.spdyHeaderBlockDecoder;
    }

    static SpdyHeaderBlockEncoder access$100(SpdyFrameCodec spdyFrameCodec) {
        return spdyFrameCodec.spdyHeaderBlockEncoder;
    }
}

