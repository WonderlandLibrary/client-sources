/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.ComposedLastHttpContent;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpMessage;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;
import java.util.List;

public abstract class HttpContentDecoder
extends MessageToMessageDecoder<HttpObject> {
    static final String IDENTITY = HttpHeaderValues.IDENTITY.toString();
    protected ChannelHandlerContext ctx;
    private EmbeddedChannel decoder;
    private boolean continueResponse;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        HttpObject httpObject2;
        if (httpObject instanceof HttpResponse && ((HttpResponse)httpObject).status().code() == 100) {
            if (!(httpObject instanceof LastHttpContent)) {
                this.continueResponse = true;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
            return;
        }
        if (this.continueResponse) {
            if (httpObject instanceof LastHttpContent) {
                this.continueResponse = false;
            }
            list.add(ReferenceCountUtil.retain(httpObject));
            return;
        }
        if (httpObject instanceof HttpMessage) {
            String string;
            this.cleanup();
            httpObject2 = (HttpMessage)httpObject;
            HttpHeaders httpHeaders = httpObject2.headers();
            String string2 = httpHeaders.get(HttpHeaderNames.CONTENT_ENCODING);
            string2 = string2 != null ? string2.trim() : IDENTITY;
            this.decoder = this.newContentDecoder(string2);
            if (this.decoder == null) {
                if (httpObject2 instanceof HttpContent) {
                    ((HttpContent)httpObject2).retain();
                }
                list.add(httpObject2);
                return;
            }
            if (httpHeaders.contains(HttpHeaderNames.CONTENT_LENGTH)) {
                httpHeaders.remove(HttpHeaderNames.CONTENT_LENGTH);
                httpHeaders.set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
            }
            if (HttpHeaderValues.IDENTITY.contentEquals(string = this.getTargetContentEncoding(string2))) {
                httpHeaders.remove(HttpHeaderNames.CONTENT_ENCODING);
            } else {
                httpHeaders.set((CharSequence)HttpHeaderNames.CONTENT_ENCODING, (Object)string);
            }
            if (httpObject2 instanceof HttpContent) {
                DefaultHttpMessage defaultHttpMessage;
                if (httpObject2 instanceof HttpRequest) {
                    HttpRequest httpRequest = (HttpRequest)httpObject2;
                    defaultHttpMessage = new DefaultHttpRequest(httpRequest.protocolVersion(), httpRequest.method(), httpRequest.uri());
                } else if (httpObject2 instanceof HttpResponse) {
                    HttpResponse httpResponse = (HttpResponse)httpObject2;
                    defaultHttpMessage = new DefaultHttpResponse(httpResponse.protocolVersion(), httpResponse.status());
                } else {
                    throw new CodecException("Object of class " + httpObject2.getClass().getName() + " is not a HttpRequest or HttpResponse");
                }
                defaultHttpMessage.headers().set(httpObject2.headers());
                defaultHttpMessage.setDecoderResult(httpObject2.decoderResult());
                list.add(defaultHttpMessage);
            } else {
                list.add(httpObject2);
            }
        }
        if (httpObject instanceof HttpContent) {
            httpObject2 = (HttpContent)httpObject;
            if (this.decoder == null) {
                list.add(httpObject2.retain());
            } else {
                this.decodeContent((HttpContent)httpObject2, list);
            }
        }
    }

    private void decodeContent(HttpContent httpContent, List<Object> list) {
        ByteBuf byteBuf = httpContent.content();
        this.decode(byteBuf, list);
        if (httpContent instanceof LastHttpContent) {
            this.finishDecode(list);
            LastHttpContent lastHttpContent = (LastHttpContent)httpContent;
            HttpHeaders httpHeaders = lastHttpContent.trailingHeaders();
            if (httpHeaders.isEmpty()) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            } else {
                list.add(new ComposedLastHttpContent(httpHeaders));
            }
        }
    }

    protected abstract EmbeddedChannel newContentDecoder(String var1) throws Exception;

    protected String getTargetContentEncoding(String string) throws Exception {
        return IDENTITY;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanupSafely(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanupSafely(channelHandlerContext);
        super.channelInactive(channelHandlerContext);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        super.handlerAdded(channelHandlerContext);
    }

    private void cleanup() {
        if (this.decoder != null) {
            this.decoder.finishAndReleaseAll();
            this.decoder = null;
        }
    }

    private void cleanupSafely(ChannelHandlerContext channelHandlerContext) {
        try {
            this.cleanup();
        } catch (Throwable throwable) {
            channelHandlerContext.fireExceptionCaught(throwable);
        }
    }

    private void decode(ByteBuf byteBuf, List<Object> list) {
        this.decoder.writeInbound(byteBuf.retain());
        this.fetchDecoderOutput(list);
    }

    private void finishDecode(List<Object> list) {
        if (this.decoder.finish()) {
            this.fetchDecoderOutput(list);
        }
        this.decoder = null;
    }

    private void fetchDecoderOutput(List<Object> list) {
        ByteBuf byteBuf;
        while ((byteBuf = (ByteBuf)this.decoder.readInbound()) != null) {
            if (!byteBuf.isReadable()) {
                byteBuf.release();
                continue;
            }
            list.add(new DefaultHttpContent(byteBuf));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }
}

