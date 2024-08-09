/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http2.DefaultHttp2DataFrame;
import io.netty.handler.codec.http2.DefaultHttp2HeadersFrame;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2StreamChannel;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.handler.ssl.SslHandler;
import java.util.List;

@ChannelHandler.Sharable
public class Http2StreamFrameToHttpObjectCodec
extends MessageToMessageCodec<Http2StreamFrame, HttpObject> {
    private final boolean isServer;
    private final boolean validateHeaders;
    private HttpScheme scheme;

    public Http2StreamFrameToHttpObjectCodec(boolean bl, boolean bl2) {
        this.isServer = bl;
        this.validateHeaders = bl2;
        this.scheme = HttpScheme.HTTP;
    }

    public Http2StreamFrameToHttpObjectCodec(boolean bl) {
        this(bl, true);
    }

    @Override
    public boolean acceptInboundMessage(Object object) throws Exception {
        return object instanceof Http2HeadersFrame || object instanceof Http2DataFrame;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Http2StreamFrame http2StreamFrame, List<Object> list) throws Exception {
        if (http2StreamFrame instanceof Http2HeadersFrame) {
            Http2HeadersFrame http2HeadersFrame = (Http2HeadersFrame)http2StreamFrame;
            Http2Headers http2Headers = http2HeadersFrame.headers();
            Http2FrameStream http2FrameStream = http2HeadersFrame.stream();
            int n = http2FrameStream == null ? 0 : http2FrameStream.id();
            CharSequence charSequence = http2Headers.status();
            if (null != charSequence && HttpResponseStatus.CONTINUE.codeAsText().contentEquals(charSequence)) {
                FullHttpMessage fullHttpMessage = this.newFullMessage(n, http2Headers, channelHandlerContext.alloc());
                list.add(fullHttpMessage);
                return;
            }
            if (http2HeadersFrame.isEndStream()) {
                if (http2Headers.method() == null && charSequence == null) {
                    DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
                    HttpConversionUtil.addHttp2ToHttpHeaders(n, http2Headers, defaultLastHttpContent.trailingHeaders(), HttpVersion.HTTP_1_1, true, true);
                    list.add(defaultLastHttpContent);
                } else {
                    FullHttpMessage fullHttpMessage = this.newFullMessage(n, http2Headers, channelHandlerContext.alloc());
                    list.add(fullHttpMessage);
                }
            } else {
                HttpMessage httpMessage = this.newMessage(n, http2Headers);
                if (!HttpUtil.isContentLengthSet(httpMessage)) {
                    httpMessage.headers().add((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
                }
                list.add(httpMessage);
            }
        } else if (http2StreamFrame instanceof Http2DataFrame) {
            Http2DataFrame http2DataFrame = (Http2DataFrame)http2StreamFrame;
            if (http2DataFrame.isEndStream()) {
                list.add(new DefaultLastHttpContent(http2DataFrame.content().retain(), this.validateHeaders));
            } else {
                list.add(new DefaultHttpContent(http2DataFrame.content().retain()));
            }
        }
    }

    private void encodeLastContent(LastHttpContent lastHttpContent, List<Object> list) {
        boolean bl;
        boolean bl2 = bl = !(lastHttpContent instanceof FullHttpMessage) && lastHttpContent.trailingHeaders().isEmpty();
        if (lastHttpContent.content().isReadable() || bl) {
            list.add(new DefaultHttp2DataFrame(lastHttpContent.content().retain(), lastHttpContent.trailingHeaders().isEmpty()));
        }
        if (!lastHttpContent.trailingHeaders().isEmpty()) {
            Http2Headers http2Headers = HttpConversionUtil.toHttp2Headers(lastHttpContent.trailingHeaders(), this.validateHeaders);
            list.add(new DefaultHttp2HeadersFrame(http2Headers, true));
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        Object object;
        if (httpObject instanceof HttpResponse && (object = (HttpResponse)httpObject).status().equals(HttpResponseStatus.CONTINUE)) {
            if (object instanceof FullHttpResponse) {
                Http2Headers http2Headers = this.toHttp2Headers((HttpMessage)object);
                list.add(new DefaultHttp2HeadersFrame(http2Headers, false));
                return;
            }
            throw new EncoderException(HttpResponseStatus.CONTINUE.toString() + " must be a FullHttpResponse");
        }
        if (httpObject instanceof HttpMessage) {
            object = this.toHttp2Headers((HttpMessage)httpObject);
            boolean bl = false;
            if (httpObject instanceof FullHttpMessage) {
                FullHttpMessage fullHttpMessage = (FullHttpMessage)httpObject;
                bl = !fullHttpMessage.content().isReadable() && fullHttpMessage.trailingHeaders().isEmpty();
            }
            list.add(new DefaultHttp2HeadersFrame((Http2Headers)object, bl));
        }
        if (httpObject instanceof LastHttpContent) {
            object = (LastHttpContent)httpObject;
            this.encodeLastContent((LastHttpContent)object, list);
        } else if (httpObject instanceof HttpContent) {
            object = (HttpContent)httpObject;
            list.add(new DefaultHttp2DataFrame(object.content().retain(), false));
        }
    }

    private Http2Headers toHttp2Headers(HttpMessage httpMessage) {
        if (httpMessage instanceof HttpRequest) {
            httpMessage.headers().set((CharSequence)HttpConversionUtil.ExtensionHeaderNames.SCHEME.text(), (Object)this.scheme.name());
        }
        return HttpConversionUtil.toHttp2Headers(httpMessage, this.validateHeaders);
    }

    private HttpMessage newMessage(int n, Http2Headers http2Headers) throws Http2Exception {
        return this.isServer ? HttpConversionUtil.toHttpRequest(n, http2Headers, this.validateHeaders) : HttpConversionUtil.toHttpResponse(n, http2Headers, this.validateHeaders);
    }

    private FullHttpMessage newFullMessage(int n, Http2Headers http2Headers, ByteBufAllocator byteBufAllocator) throws Http2Exception {
        return this.isServer ? HttpConversionUtil.toFullHttpRequest(n, http2Headers, byteBufAllocator, this.validateHeaders) : HttpConversionUtil.toFullHttpResponse(n, http2Headers, byteBufAllocator, this.validateHeaders);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerAdded(channelHandlerContext);
        this.scheme = this.isSsl(channelHandlerContext) ? HttpScheme.HTTPS : HttpScheme.HTTP;
    }

    protected boolean isSsl(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        Channel channel2 = channel instanceof Http2StreamChannel ? channel.parent() : channel;
        return null != channel2.pipeline().get(SslHandler.class);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (Http2StreamFrame)object, (List<Object>)list);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }
}

