/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.ComposedLastHttpContent;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpContentDecoder;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public abstract class HttpContentEncoder
extends MessageToMessageCodec<HttpRequest, HttpObject> {
    private static final CharSequence ZERO_LENGTH_HEAD;
    private static final CharSequence ZERO_LENGTH_CONNECT;
    private static final int CONTINUE_CODE;
    private final Queue<CharSequence> acceptEncodingQueue = new ArrayDeque<CharSequence>();
    private EmbeddedChannel encoder;
    private State state = State.AWAIT_HEADERS;
    static final boolean $assertionsDisabled;

    @Override
    public boolean acceptOutboundMessage(Object object) throws Exception {
        return object instanceof HttpContent || object instanceof HttpResponse;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, List<Object> list) throws Exception {
        HttpMethod httpMethod;
        CharSequence charSequence = httpRequest.headers().get(HttpHeaderNames.ACCEPT_ENCODING);
        if (charSequence == null) {
            charSequence = HttpContentDecoder.IDENTITY;
        }
        if ((httpMethod = httpRequest.method()) == HttpMethod.HEAD) {
            charSequence = ZERO_LENGTH_HEAD;
        } else if (httpMethod == HttpMethod.CONNECT) {
            charSequence = ZERO_LENGTH_CONNECT;
        }
        this.acceptEncodingQueue.add(charSequence);
        list.add(ReferenceCountUtil.retain(httpRequest));
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpObject httpObject, List<Object> list) throws Exception {
        boolean bl = httpObject instanceof HttpResponse && httpObject instanceof LastHttpContent;
        switch (1.$SwitchMap$io$netty$handler$codec$http$HttpContentEncoder$State[this.state.ordinal()]) {
            case 1: {
                CharSequence charSequence;
                HttpContentEncoder.ensureHeaders(httpObject);
                if (!$assertionsDisabled && this.encoder != null) {
                    throw new AssertionError();
                }
                HttpResponse httpResponse = (HttpResponse)httpObject;
                int n = httpResponse.status().code();
                if (n == CONTINUE_CODE) {
                    charSequence = null;
                } else {
                    charSequence = this.acceptEncodingQueue.poll();
                    if (charSequence == null) {
                        throw new IllegalStateException("cannot send more responses than requests");
                    }
                }
                if (HttpContentEncoder.isPassthru(httpResponse.protocolVersion(), n, charSequence)) {
                    if (bl) {
                        list.add(ReferenceCountUtil.retain(httpResponse));
                        break;
                    }
                    list.add(httpResponse);
                    this.state = State.PASS_THROUGH;
                    break;
                }
                if (bl && !((ByteBufHolder)((Object)httpResponse)).content().isReadable()) {
                    list.add(ReferenceCountUtil.retain(httpResponse));
                    break;
                }
                Result result = this.beginEncode(httpResponse, charSequence.toString());
                if (result == null) {
                    if (bl) {
                        list.add(ReferenceCountUtil.retain(httpResponse));
                        break;
                    }
                    list.add(httpResponse);
                    this.state = State.PASS_THROUGH;
                    break;
                }
                this.encoder = result.contentEncoder();
                httpResponse.headers().set((CharSequence)HttpHeaderNames.CONTENT_ENCODING, (Object)result.targetContentEncoding());
                if (bl) {
                    DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(httpResponse.protocolVersion(), httpResponse.status());
                    defaultHttpResponse.headers().set(httpResponse.headers());
                    list.add(defaultHttpResponse);
                    HttpContentEncoder.ensureContent(httpResponse);
                    this.encodeFullResponse(defaultHttpResponse, (HttpContent)((Object)httpResponse), list);
                    break;
                }
                httpResponse.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
                httpResponse.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
                list.add(httpResponse);
                this.state = State.AWAIT_CONTENT;
                if (!(httpObject instanceof HttpContent)) break;
            }
            case 2: {
                HttpContentEncoder.ensureContent(httpObject);
                if (!this.encodeContent((HttpContent)httpObject, list)) break;
                this.state = State.AWAIT_HEADERS;
                break;
            }
            case 3: {
                HttpContentEncoder.ensureContent(httpObject);
                list.add(ReferenceCountUtil.retain(httpObject));
                if (!(httpObject instanceof LastHttpContent)) break;
                this.state = State.AWAIT_HEADERS;
            }
        }
    }

    private void encodeFullResponse(HttpResponse httpResponse, HttpContent httpContent, List<Object> list) {
        int n = list.size();
        this.encodeContent(httpContent, list);
        if (HttpUtil.isContentLengthSet(httpResponse)) {
            int n2 = 0;
            for (int i = n; i < list.size(); ++i) {
                Object object = list.get(i);
                if (!(object instanceof HttpContent)) continue;
                n2 += ((HttpContent)object).content().readableBytes();
            }
            HttpUtil.setContentLength(httpResponse, n2);
        } else {
            httpResponse.headers().set((CharSequence)HttpHeaderNames.TRANSFER_ENCODING, (Object)HttpHeaderValues.CHUNKED);
        }
    }

    private static boolean isPassthru(HttpVersion httpVersion, int n, CharSequence charSequence) {
        return n < 200 || n == 204 || n == 304 || charSequence == ZERO_LENGTH_HEAD || charSequence == ZERO_LENGTH_CONNECT && n == 200 || httpVersion == HttpVersion.HTTP_1_0;
    }

    private static void ensureHeaders(HttpObject httpObject) {
        if (!(httpObject instanceof HttpResponse)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpResponse.class.getSimpleName() + ')');
        }
    }

    private static void ensureContent(HttpObject httpObject) {
        if (!(httpObject instanceof HttpContent)) {
            throw new IllegalStateException("unexpected message type: " + httpObject.getClass().getName() + " (expected: " + HttpContent.class.getSimpleName() + ')');
        }
    }

    private boolean encodeContent(HttpContent httpContent, List<Object> list) {
        ByteBuf byteBuf = httpContent.content();
        this.encode(byteBuf, list);
        if (httpContent instanceof LastHttpContent) {
            this.finishEncode(list);
            LastHttpContent lastHttpContent = (LastHttpContent)httpContent;
            HttpHeaders httpHeaders = lastHttpContent.trailingHeaders();
            if (httpHeaders.isEmpty()) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            } else {
                list.add(new ComposedLastHttpContent(httpHeaders));
            }
            return false;
        }
        return true;
    }

    protected abstract Result beginEncode(HttpResponse var1, String var2) throws Exception;

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

    private void cleanup() {
        if (this.encoder != null) {
            this.encoder.finishAndReleaseAll();
            this.encoder = null;
        }
    }

    private void cleanupSafely(ChannelHandlerContext channelHandlerContext) {
        try {
            this.cleanup();
        } catch (Throwable throwable) {
            channelHandlerContext.fireExceptionCaught(throwable);
        }
    }

    private void encode(ByteBuf byteBuf, List<Object> list) {
        this.encoder.writeOutbound(byteBuf.retain());
        this.fetchEncoderOutput(list);
    }

    private void finishEncode(List<Object> list) {
        if (this.encoder.finish()) {
            this.fetchEncoderOutput(list);
        }
        this.encoder = null;
    }

    private void fetchEncoderOutput(List<Object> list) {
        ByteBuf byteBuf;
        while ((byteBuf = (ByteBuf)this.encoder.readOutbound()) != null) {
            if (!byteBuf.isReadable()) {
                byteBuf.release();
                continue;
            }
            list.add(new DefaultHttpContent(byteBuf));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (HttpRequest)object, (List<Object>)list);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (HttpObject)object, (List<Object>)list);
    }

    static {
        $assertionsDisabled = !HttpContentEncoder.class.desiredAssertionStatus();
        ZERO_LENGTH_HEAD = "HEAD";
        ZERO_LENGTH_CONNECT = "CONNECT";
        CONTINUE_CODE = HttpResponseStatus.CONTINUE.code();
    }

    public static final class Result {
        private final String targetContentEncoding;
        private final EmbeddedChannel contentEncoder;

        public Result(String string, EmbeddedChannel embeddedChannel) {
            if (string == null) {
                throw new NullPointerException("targetContentEncoding");
            }
            if (embeddedChannel == null) {
                throw new NullPointerException("contentEncoder");
            }
            this.targetContentEncoding = string;
            this.contentEncoder = embeddedChannel;
        }

        public String targetContentEncoding() {
            return this.targetContentEncoding;
        }

        public EmbeddedChannel contentEncoder() {
            return this.contentEncoder;
        }
    }

    private static enum State {
        PASS_THROUGH,
        AWAIT_HEADERS,
        AWAIT_CONTENT;

    }
}

