/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.spdy.DefaultSpdyRstStreamFrame;
import io.netty.handler.codec.spdy.DefaultSpdySynReplyFrame;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyDataFrame;
import io.netty.handler.codec.spdy.SpdyFrame;
import io.netty.handler.codec.spdy.SpdyHeaders;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyHttpHeaders;
import io.netty.handler.codec.spdy.SpdyRstStreamFrame;
import io.netty.handler.codec.spdy.SpdyStreamStatus;
import io.netty.handler.codec.spdy.SpdySynReplyFrame;
import io.netty.handler.codec.spdy.SpdySynStreamFrame;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.util.ReferenceCountUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpdyHttpDecoder
extends MessageToMessageDecoder<SpdyFrame> {
    private final boolean validateHeaders;
    private final int spdyVersion;
    private final int maxContentLength;
    private final Map<Integer, FullHttpMessage> messageMap;

    public SpdyHttpDecoder(SpdyVersion spdyVersion, int n) {
        this(spdyVersion, n, new HashMap<Integer, FullHttpMessage>(), true);
    }

    public SpdyHttpDecoder(SpdyVersion spdyVersion, int n, boolean bl) {
        this(spdyVersion, n, new HashMap<Integer, FullHttpMessage>(), bl);
    }

    protected SpdyHttpDecoder(SpdyVersion spdyVersion, int n, Map<Integer, FullHttpMessage> map) {
        this(spdyVersion, n, map, true);
    }

    protected SpdyHttpDecoder(SpdyVersion spdyVersion, int n, Map<Integer, FullHttpMessage> map, boolean bl) {
        if (spdyVersion == null) {
            throw new NullPointerException("version");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("maxContentLength must be a positive integer: " + n);
        }
        this.spdyVersion = spdyVersion.getVersion();
        this.maxContentLength = n;
        this.messageMap = map;
        this.validateHeaders = bl;
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        for (Map.Entry<Integer, FullHttpMessage> entry : this.messageMap.entrySet()) {
            ReferenceCountUtil.safeRelease(entry.getValue());
        }
        this.messageMap.clear();
        super.channelInactive(channelHandlerContext);
    }

    protected FullHttpMessage putMessage(int n, FullHttpMessage fullHttpMessage) {
        return this.messageMap.put(n, fullHttpMessage);
    }

    protected FullHttpMessage getMessage(int n) {
        return this.messageMap.get(n);
    }

    protected FullHttpMessage removeMessage(int n) {
        return this.messageMap.remove(n);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, SpdyFrame spdyFrame, List<Object> list) throws Exception {
        block37: {
            if (spdyFrame instanceof SpdySynStreamFrame) {
                SpdySynStreamFrame spdySynStreamFrame = (SpdySynStreamFrame)spdyFrame;
                int n = spdySynStreamFrame.streamId();
                if (SpdyCodecUtil.isServerId(n)) {
                    int n2 = spdySynStreamFrame.associatedStreamId();
                    if (n2 == 0) {
                        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.INVALID_STREAM);
                        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                        return;
                    }
                    if (spdySynStreamFrame.isLast()) {
                        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.PROTOCOL_ERROR);
                        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                        return;
                    }
                    if (spdySynStreamFrame.isTruncated()) {
                        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.INTERNAL_ERROR);
                        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                        return;
                    }
                    try {
                        FullHttpRequest fullHttpRequest = SpdyHttpDecoder.createHttpRequest(spdySynStreamFrame, channelHandlerContext.alloc());
                        fullHttpRequest.headers().setInt(SpdyHttpHeaders.Names.STREAM_ID, n);
                        fullHttpRequest.headers().setInt(SpdyHttpHeaders.Names.ASSOCIATED_TO_STREAM_ID, n2);
                        fullHttpRequest.headers().setInt(SpdyHttpHeaders.Names.PRIORITY, spdySynStreamFrame.priority());
                        list.add(fullHttpRequest);
                    } catch (Throwable throwable) {
                        DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.PROTOCOL_ERROR);
                        channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                    }
                } else {
                    if (spdySynStreamFrame.isTruncated()) {
                        DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(n);
                        defaultSpdySynReplyFrame.setLast(true);
                        SpdyHeaders spdyHeaders = defaultSpdySynReplyFrame.headers();
                        spdyHeaders.setInt(SpdyHeaders.HttpNames.STATUS, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.code());
                        spdyHeaders.setObject(SpdyHeaders.HttpNames.VERSION, (Object)HttpVersion.HTTP_1_0);
                        channelHandlerContext.writeAndFlush(defaultSpdySynReplyFrame);
                        return;
                    }
                    try {
                        FullHttpRequest fullHttpRequest = SpdyHttpDecoder.createHttpRequest(spdySynStreamFrame, channelHandlerContext.alloc());
                        fullHttpRequest.headers().setInt(SpdyHttpHeaders.Names.STREAM_ID, n);
                        if (spdySynStreamFrame.isLast()) {
                            list.add(fullHttpRequest);
                            break block37;
                        }
                        this.putMessage(n, fullHttpRequest);
                    } catch (Throwable throwable) {
                        DefaultSpdySynReplyFrame defaultSpdySynReplyFrame = new DefaultSpdySynReplyFrame(n);
                        defaultSpdySynReplyFrame.setLast(true);
                        SpdyHeaders spdyHeaders = defaultSpdySynReplyFrame.headers();
                        spdyHeaders.setInt(SpdyHeaders.HttpNames.STATUS, HttpResponseStatus.BAD_REQUEST.code());
                        spdyHeaders.setObject(SpdyHeaders.HttpNames.VERSION, (Object)HttpVersion.HTTP_1_0);
                        channelHandlerContext.writeAndFlush(defaultSpdySynReplyFrame);
                    }
                }
            } else if (spdyFrame instanceof SpdySynReplyFrame) {
                SpdySynReplyFrame spdySynReplyFrame = (SpdySynReplyFrame)spdyFrame;
                int n = spdySynReplyFrame.streamId();
                if (spdySynReplyFrame.isTruncated()) {
                    DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.INTERNAL_ERROR);
                    channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                    return;
                }
                try {
                    FullHttpResponse fullHttpResponse = SpdyHttpDecoder.createHttpResponse(spdySynReplyFrame, channelHandlerContext.alloc(), this.validateHeaders);
                    fullHttpResponse.headers().setInt(SpdyHttpHeaders.Names.STREAM_ID, n);
                    if (spdySynReplyFrame.isLast()) {
                        HttpUtil.setContentLength(fullHttpResponse, 0L);
                        list.add(fullHttpResponse);
                        break block37;
                    }
                    this.putMessage(n, fullHttpResponse);
                } catch (Throwable throwable) {
                    DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.PROTOCOL_ERROR);
                    channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                }
            } else if (spdyFrame instanceof SpdyHeadersFrame) {
                SpdyHeadersFrame spdyHeadersFrame = (SpdyHeadersFrame)spdyFrame;
                int n = spdyHeadersFrame.streamId();
                FullHttpMessage fullHttpMessage = this.getMessage(n);
                if (fullHttpMessage == null) {
                    if (SpdyCodecUtil.isServerId(n)) {
                        if (spdyHeadersFrame.isTruncated()) {
                            DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.INTERNAL_ERROR);
                            channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                            return;
                        }
                        try {
                            fullHttpMessage = SpdyHttpDecoder.createHttpResponse(spdyHeadersFrame, channelHandlerContext.alloc(), this.validateHeaders);
                            fullHttpMessage.headers().setInt(SpdyHttpHeaders.Names.STREAM_ID, n);
                            if (spdyHeadersFrame.isLast()) {
                                HttpUtil.setContentLength(fullHttpMessage, 0L);
                                list.add(fullHttpMessage);
                            } else {
                                this.putMessage(n, fullHttpMessage);
                            }
                        } catch (Throwable throwable) {
                            DefaultSpdyRstStreamFrame defaultSpdyRstStreamFrame = new DefaultSpdyRstStreamFrame(n, SpdyStreamStatus.PROTOCOL_ERROR);
                            channelHandlerContext.writeAndFlush(defaultSpdyRstStreamFrame);
                        }
                    }
                    return;
                }
                if (!spdyHeadersFrame.isTruncated()) {
                    for (Map.Entry entry : spdyHeadersFrame.headers()) {
                        fullHttpMessage.headers().add((CharSequence)entry.getKey(), entry.getValue());
                    }
                }
                if (spdyHeadersFrame.isLast()) {
                    HttpUtil.setContentLength(fullHttpMessage, fullHttpMessage.content().readableBytes());
                    this.removeMessage(n);
                    list.add(fullHttpMessage);
                }
            } else if (spdyFrame instanceof SpdyDataFrame) {
                SpdyDataFrame spdyDataFrame = (SpdyDataFrame)spdyFrame;
                int n = spdyDataFrame.streamId();
                FullHttpMessage fullHttpMessage = this.getMessage(n);
                if (fullHttpMessage == null) {
                    return;
                }
                ByteBuf byteBuf = fullHttpMessage.content();
                if (byteBuf.readableBytes() > this.maxContentLength - spdyDataFrame.content().readableBytes()) {
                    this.removeMessage(n);
                    throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
                }
                ByteBuf byteBuf2 = spdyDataFrame.content();
                int n3 = byteBuf2.readableBytes();
                byteBuf.writeBytes(byteBuf2, byteBuf2.readerIndex(), n3);
                if (spdyDataFrame.isLast()) {
                    HttpUtil.setContentLength(fullHttpMessage, byteBuf.readableBytes());
                    this.removeMessage(n);
                    list.add(fullHttpMessage);
                }
            } else if (spdyFrame instanceof SpdyRstStreamFrame) {
                SpdyRstStreamFrame spdyRstStreamFrame = (SpdyRstStreamFrame)spdyFrame;
                int n = spdyRstStreamFrame.streamId();
                this.removeMessage(n);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static FullHttpRequest createHttpRequest(SpdyHeadersFrame spdyHeadersFrame, ByteBufAllocator byteBufAllocator) throws Exception {
        SpdyHeaders spdyHeaders = spdyHeadersFrame.headers();
        HttpMethod httpMethod = HttpMethod.valueOf(spdyHeaders.getAsString(SpdyHeaders.HttpNames.METHOD));
        String string = spdyHeaders.getAsString(SpdyHeaders.HttpNames.PATH);
        HttpVersion httpVersion = HttpVersion.valueOf(spdyHeaders.getAsString(SpdyHeaders.HttpNames.VERSION));
        spdyHeaders.remove(SpdyHeaders.HttpNames.METHOD);
        spdyHeaders.remove(SpdyHeaders.HttpNames.PATH);
        spdyHeaders.remove(SpdyHeaders.HttpNames.VERSION);
        boolean bl = true;
        ByteBuf byteBuf = byteBufAllocator.buffer();
        try {
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(httpVersion, httpMethod, string, byteBuf);
            spdyHeaders.remove(SpdyHeaders.HttpNames.SCHEME);
            CharSequence charSequence = (CharSequence)spdyHeaders.get(SpdyHeaders.HttpNames.HOST);
            spdyHeaders.remove(SpdyHeaders.HttpNames.HOST);
            defaultFullHttpRequest.headers().set((CharSequence)HttpHeaderNames.HOST, (Object)charSequence);
            for (Map.Entry entry : spdyHeadersFrame.headers()) {
                defaultFullHttpRequest.headers().add((CharSequence)entry.getKey(), entry.getValue());
            }
            HttpUtil.setKeepAlive(defaultFullHttpRequest, true);
            defaultFullHttpRequest.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            bl = false;
            DefaultFullHttpRequest defaultFullHttpRequest2 = defaultFullHttpRequest;
            return defaultFullHttpRequest2;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static FullHttpResponse createHttpResponse(SpdyHeadersFrame spdyHeadersFrame, ByteBufAllocator byteBufAllocator, boolean bl) throws Exception {
        SpdyHeaders spdyHeaders = spdyHeadersFrame.headers();
        HttpResponseStatus httpResponseStatus = HttpResponseStatus.parseLine((CharSequence)spdyHeaders.get(SpdyHeaders.HttpNames.STATUS));
        HttpVersion httpVersion = HttpVersion.valueOf(spdyHeaders.getAsString(SpdyHeaders.HttpNames.VERSION));
        spdyHeaders.remove(SpdyHeaders.HttpNames.STATUS);
        spdyHeaders.remove(SpdyHeaders.HttpNames.VERSION);
        boolean bl2 = true;
        ByteBuf byteBuf = byteBufAllocator.buffer();
        try {
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(httpVersion, httpResponseStatus, byteBuf, bl);
            for (Map.Entry entry : spdyHeadersFrame.headers()) {
                defaultFullHttpResponse.headers().add((CharSequence)entry.getKey(), entry.getValue());
            }
            HttpUtil.setKeepAlive(defaultFullHttpResponse, true);
            defaultFullHttpResponse.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            defaultFullHttpResponse.headers().remove(HttpHeaderNames.TRAILER);
            bl2 = false;
            DefaultFullHttpResponse defaultFullHttpResponse2 = defaultFullHttpResponse;
            return defaultFullHttpResponse2;
        } finally {
            if (bl2) {
                byteBuf.release();
            }
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (SpdyFrame)object, (List<Object>)list);
    }
}

