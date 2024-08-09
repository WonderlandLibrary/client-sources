/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpExpectationFailedEvent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMessageUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class HttpObjectAggregator
extends MessageAggregator<HttpObject, HttpMessage, HttpContent, FullHttpMessage> {
    private static final InternalLogger logger;
    private static final FullHttpResponse CONTINUE;
    private static final FullHttpResponse EXPECTATION_FAILED;
    private static final FullHttpResponse TOO_LARGE_CLOSE;
    private static final FullHttpResponse TOO_LARGE;
    private final boolean closeOnExpectationFailed;
    static final boolean $assertionsDisabled;

    public HttpObjectAggregator(int n) {
        this(n, false);
    }

    public HttpObjectAggregator(int n, boolean bl) {
        super(n);
        this.closeOnExpectationFailed = bl;
    }

    @Override
    protected boolean isStartMessage(HttpObject httpObject) throws Exception {
        return httpObject instanceof HttpMessage;
    }

    @Override
    protected boolean isContentMessage(HttpObject httpObject) throws Exception {
        return httpObject instanceof HttpContent;
    }

    @Override
    protected boolean isLastContentMessage(HttpContent httpContent) throws Exception {
        return httpContent instanceof LastHttpContent;
    }

    @Override
    protected boolean isAggregated(HttpObject httpObject) throws Exception {
        return httpObject instanceof FullHttpMessage;
    }

    @Override
    protected boolean isContentLengthInvalid(HttpMessage httpMessage, int n) {
        try {
            return HttpUtil.getContentLength(httpMessage, -1L) > (long)n;
        } catch (NumberFormatException numberFormatException) {
            return true;
        }
    }

    private static Object continueResponse(HttpMessage httpMessage, int n, ChannelPipeline channelPipeline) {
        if (HttpUtil.isUnsupportedExpectation(httpMessage)) {
            channelPipeline.fireUserEventTriggered(HttpExpectationFailedEvent.INSTANCE);
            return EXPECTATION_FAILED.retainedDuplicate();
        }
        if (HttpUtil.is100ContinueExpected(httpMessage)) {
            if (HttpUtil.getContentLength(httpMessage, -1L) <= (long)n) {
                return CONTINUE.retainedDuplicate();
            }
            channelPipeline.fireUserEventTriggered(HttpExpectationFailedEvent.INSTANCE);
            return TOO_LARGE.retainedDuplicate();
        }
        return null;
    }

    @Override
    protected Object newContinueResponse(HttpMessage httpMessage, int n, ChannelPipeline channelPipeline) {
        Object object = HttpObjectAggregator.continueResponse(httpMessage, n, channelPipeline);
        if (object != null) {
            httpMessage.headers().remove(HttpHeaderNames.EXPECT);
        }
        return object;
    }

    @Override
    protected boolean closeAfterContinueResponse(Object object) {
        return this.closeOnExpectationFailed && this.ignoreContentAfterContinueResponse(object);
    }

    @Override
    protected boolean ignoreContentAfterContinueResponse(Object object) {
        if (object instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse)object;
            return httpResponse.status().codeClass().equals((Object)HttpStatusClass.CLIENT_ERROR);
        }
        return true;
    }

    @Override
    protected FullHttpMessage beginAggregation(HttpMessage httpMessage, ByteBuf byteBuf) throws Exception {
        AggregatedFullHttpMessage aggregatedFullHttpMessage;
        if (!$assertionsDisabled && httpMessage instanceof FullHttpMessage) {
            throw new AssertionError();
        }
        HttpUtil.setTransferEncodingChunked(httpMessage, false);
        if (httpMessage instanceof HttpRequest) {
            aggregatedFullHttpMessage = new AggregatedFullHttpRequest((HttpRequest)httpMessage, byteBuf, null);
        } else if (httpMessage instanceof HttpResponse) {
            aggregatedFullHttpMessage = new AggregatedFullHttpResponse((HttpResponse)httpMessage, byteBuf, null);
        } else {
            throw new Error();
        }
        return aggregatedFullHttpMessage;
    }

    @Override
    protected void aggregate(FullHttpMessage fullHttpMessage, HttpContent httpContent) throws Exception {
        if (httpContent instanceof LastHttpContent) {
            ((AggregatedFullHttpMessage)fullHttpMessage).setTrailingHeaders(((LastHttpContent)httpContent).trailingHeaders());
        }
    }

    @Override
    protected void finishAggregation(FullHttpMessage fullHttpMessage) throws Exception {
        if (!HttpUtil.isContentLengthSet(fullHttpMessage)) {
            fullHttpMessage.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)String.valueOf(fullHttpMessage.content().readableBytes()));
        }
    }

    @Override
    protected void handleOversizedMessage(ChannelHandlerContext channelHandlerContext, HttpMessage httpMessage) throws Exception {
        if (httpMessage instanceof HttpRequest) {
            Object object;
            if (httpMessage instanceof FullHttpMessage || !HttpUtil.is100ContinueExpected(httpMessage) && !HttpUtil.isKeepAlive(httpMessage)) {
                object = channelHandlerContext.writeAndFlush(TOO_LARGE_CLOSE.retainedDuplicate());
                object.addListener(new ChannelFutureListener(this, channelHandlerContext){
                    final ChannelHandlerContext val$ctx;
                    final HttpObjectAggregator this$0;
                    {
                        this.this$0 = httpObjectAggregator;
                        this.val$ctx = channelHandlerContext;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            HttpObjectAggregator.access$000().debug("Failed to send a 413 Request Entity Too Large.", channelFuture.cause());
                        }
                        this.val$ctx.close();
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            } else {
                channelHandlerContext.writeAndFlush(TOO_LARGE.retainedDuplicate()).addListener(new ChannelFutureListener(this, channelHandlerContext){
                    final ChannelHandlerContext val$ctx;
                    final HttpObjectAggregator this$0;
                    {
                        this.this$0 = httpObjectAggregator;
                        this.val$ctx = channelHandlerContext;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (!channelFuture.isSuccess()) {
                            HttpObjectAggregator.access$000().debug("Failed to send a 413 Request Entity Too Large.", channelFuture.cause());
                            this.val$ctx.close();
                        }
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
            object = channelHandlerContext.pipeline().get(HttpObjectDecoder.class);
            if (object != null) {
                ((HttpObjectDecoder)object).reset();
            }
        } else {
            if (httpMessage instanceof HttpResponse) {
                channelHandlerContext.close();
                throw new TooLongFrameException("Response entity too large: " + httpMessage);
            }
            throw new IllegalStateException();
        }
    }

    @Override
    protected void handleOversizedMessage(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.handleOversizedMessage(channelHandlerContext, (HttpMessage)object);
    }

    @Override
    protected void finishAggregation(ByteBufHolder byteBufHolder) throws Exception {
        this.finishAggregation((FullHttpMessage)byteBufHolder);
    }

    @Override
    protected void aggregate(ByteBufHolder byteBufHolder, ByteBufHolder byteBufHolder2) throws Exception {
        this.aggregate((FullHttpMessage)byteBufHolder, (HttpContent)byteBufHolder2);
    }

    @Override
    protected ByteBufHolder beginAggregation(Object object, ByteBuf byteBuf) throws Exception {
        return this.beginAggregation((HttpMessage)object, byteBuf);
    }

    @Override
    protected Object newContinueResponse(Object object, int n, ChannelPipeline channelPipeline) throws Exception {
        return this.newContinueResponse((HttpMessage)object, n, channelPipeline);
    }

    @Override
    protected boolean isContentLengthInvalid(Object object, int n) throws Exception {
        return this.isContentLengthInvalid((HttpMessage)object, n);
    }

    @Override
    protected boolean isAggregated(Object object) throws Exception {
        return this.isAggregated((HttpObject)object);
    }

    @Override
    protected boolean isLastContentMessage(ByteBufHolder byteBufHolder) throws Exception {
        return this.isLastContentMessage((HttpContent)byteBufHolder);
    }

    @Override
    protected boolean isContentMessage(Object object) throws Exception {
        return this.isContentMessage((HttpObject)object);
    }

    @Override
    protected boolean isStartMessage(Object object) throws Exception {
        return this.isStartMessage((HttpObject)object);
    }

    static InternalLogger access$000() {
        return logger;
    }

    static {
        $assertionsDisabled = !HttpObjectAggregator.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(HttpObjectAggregator.class);
        CONTINUE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER);
        EXPECTATION_FAILED = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.EXPECTATION_FAILED, Unpooled.EMPTY_BUFFER);
        TOO_LARGE_CLOSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);
        TOO_LARGE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_ENTITY_TOO_LARGE, Unpooled.EMPTY_BUFFER);
        EXPECTATION_FAILED.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
        TOO_LARGE.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
        TOO_LARGE_CLOSE.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)0);
        TOO_LARGE_CLOSE.headers().set((CharSequence)HttpHeaderNames.CONNECTION, (Object)HttpHeaderValues.CLOSE);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class AggregatedFullHttpResponse
    extends AggregatedFullHttpMessage
    implements FullHttpResponse {
        AggregatedFullHttpResponse(HttpResponse httpResponse, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            super(httpResponse, byteBuf, httpHeaders);
        }

        @Override
        public FullHttpResponse copy() {
            return this.replace(this.content().copy());
        }

        @Override
        public FullHttpResponse duplicate() {
            return this.replace(this.content().duplicate());
        }

        @Override
        public FullHttpResponse retainedDuplicate() {
            return this.replace(this.content().retainedDuplicate());
        }

        @Override
        public FullHttpResponse replace(ByteBuf byteBuf) {
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.getProtocolVersion(), this.getStatus(), byteBuf, this.headers().copy(), this.trailingHeaders().copy());
            defaultFullHttpResponse.setDecoderResult(this.decoderResult());
            return defaultFullHttpResponse;
        }

        @Override
        public FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
            ((HttpResponse)this.message).setStatus(httpResponseStatus);
            return this;
        }

        @Override
        public HttpResponseStatus getStatus() {
            return ((HttpResponse)this.message).status();
        }

        @Override
        public HttpResponseStatus status() {
            return this.getStatus();
        }

        @Override
        public FullHttpResponse setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        @Override
        public FullHttpResponse retain(int n) {
            super.retain(n);
            return this;
        }

        @Override
        public FullHttpResponse retain() {
            super.retain();
            return this;
        }

        @Override
        public FullHttpResponse touch(Object object) {
            super.touch(object);
            return this;
        }

        @Override
        public FullHttpResponse touch() {
            super.touch();
            return this;
        }

        public String toString() {
            return HttpMessageUtil.appendFullResponse(new StringBuilder(256), this).toString();
        }

        @Override
        public FullHttpMessage retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public FullHttpMessage duplicate() {
            return this.duplicate();
        }

        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }

        @Override
        public FullHttpMessage touch() {
            return this.touch();
        }

        @Override
        public FullHttpMessage touch(Object object) {
            return this.touch(object);
        }

        @Override
        public FullHttpMessage retain(int n) {
            return this.retain(n);
        }

        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }

        @Override
        public FullHttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public FullHttpMessage replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public LastHttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastHttpContent touch() {
            return this.touch();
        }

        @Override
        public LastHttpContent retain() {
            return this.retain();
        }

        @Override
        public LastHttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastHttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public LastHttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastHttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastHttpContent copy() {
            return this.copy();
        }

        @Override
        public HttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public HttpContent touch() {
            return this.touch();
        }

        @Override
        public HttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public HttpContent retain() {
            return this.retain();
        }

        @Override
        public HttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public HttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public HttpContent copy() {
            return this.copy();
        }

        @Override
        public ByteBufHolder touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ByteBufHolder touch() {
            return this.touch();
        }

        @Override
        public ByteBufHolder retain(int n) {
            return this.retain(n);
        }

        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }

        @Override
        public ByteBufHolder replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public ByteBufHolder retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }

        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }

        @Override
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ReferenceCounted touch() {
            return this.touch();
        }

        @Override
        public ReferenceCounted retain(int n) {
            return this.retain(n);
        }

        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }

        @Override
        public HttpResponse setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public HttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
            return this.setStatus(httpResponseStatus);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class AggregatedFullHttpRequest
    extends AggregatedFullHttpMessage
    implements FullHttpRequest {
        AggregatedFullHttpRequest(HttpRequest httpRequest, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            super(httpRequest, byteBuf, httpHeaders);
        }

        @Override
        public FullHttpRequest copy() {
            return this.replace(this.content().copy());
        }

        @Override
        public FullHttpRequest duplicate() {
            return this.replace(this.content().duplicate());
        }

        @Override
        public FullHttpRequest retainedDuplicate() {
            return this.replace(this.content().retainedDuplicate());
        }

        @Override
        public FullHttpRequest replace(ByteBuf byteBuf) {
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(this.protocolVersion(), this.method(), this.uri(), byteBuf, this.headers().copy(), this.trailingHeaders().copy());
            defaultFullHttpRequest.setDecoderResult(this.decoderResult());
            return defaultFullHttpRequest;
        }

        @Override
        public FullHttpRequest retain(int n) {
            super.retain(n);
            return this;
        }

        @Override
        public FullHttpRequest retain() {
            super.retain();
            return this;
        }

        @Override
        public FullHttpRequest touch() {
            super.touch();
            return this;
        }

        @Override
        public FullHttpRequest touch(Object object) {
            super.touch(object);
            return this;
        }

        @Override
        public FullHttpRequest setMethod(HttpMethod httpMethod) {
            ((HttpRequest)this.message).setMethod(httpMethod);
            return this;
        }

        @Override
        public FullHttpRequest setUri(String string) {
            ((HttpRequest)this.message).setUri(string);
            return this;
        }

        @Override
        public HttpMethod getMethod() {
            return ((HttpRequest)this.message).method();
        }

        @Override
        public String getUri() {
            return ((HttpRequest)this.message).uri();
        }

        @Override
        public HttpMethod method() {
            return this.getMethod();
        }

        @Override
        public String uri() {
            return this.getUri();
        }

        @Override
        public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
            super.setProtocolVersion(httpVersion);
            return this;
        }

        public String toString() {
            return HttpMessageUtil.appendFullRequest(new StringBuilder(256), this).toString();
        }

        @Override
        public FullHttpMessage retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public FullHttpMessage duplicate() {
            return this.duplicate();
        }

        @Override
        public FullHttpMessage copy() {
            return this.copy();
        }

        @Override
        public FullHttpMessage touch() {
            return this.touch();
        }

        @Override
        public FullHttpMessage touch(Object object) {
            return this.touch(object);
        }

        @Override
        public FullHttpMessage retain(int n) {
            return this.retain(n);
        }

        @Override
        public FullHttpMessage retain() {
            return this.retain();
        }

        @Override
        public FullHttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public FullHttpMessage replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public LastHttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastHttpContent touch() {
            return this.touch();
        }

        @Override
        public LastHttpContent retain() {
            return this.retain();
        }

        @Override
        public LastHttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastHttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public LastHttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastHttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastHttpContent copy() {
            return this.copy();
        }

        @Override
        public HttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public HttpContent touch() {
            return this.touch();
        }

        @Override
        public HttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public HttpContent retain() {
            return this.retain();
        }

        @Override
        public HttpContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public HttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public HttpContent copy() {
            return this.copy();
        }

        @Override
        public ByteBufHolder touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ByteBufHolder touch() {
            return this.touch();
        }

        @Override
        public ByteBufHolder retain(int n) {
            return this.retain(n);
        }

        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }

        @Override
        public ByteBufHolder replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public ByteBufHolder retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }

        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }

        @Override
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ReferenceCounted touch() {
            return this.touch();
        }

        @Override
        public ReferenceCounted retain(int n) {
            return this.retain(n);
        }

        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }

        @Override
        public HttpRequest setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public HttpRequest setUri(String string) {
            return this.setUri(string);
        }

        @Override
        public HttpRequest setMethod(HttpMethod httpMethod) {
            return this.setMethod(httpMethod);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static abstract class AggregatedFullHttpMessage
    implements FullHttpMessage {
        protected final HttpMessage message;
        private final ByteBuf content;
        private HttpHeaders trailingHeaders;

        AggregatedFullHttpMessage(HttpMessage httpMessage, ByteBuf byteBuf, HttpHeaders httpHeaders) {
            this.message = httpMessage;
            this.content = byteBuf;
            this.trailingHeaders = httpHeaders;
        }

        @Override
        public HttpHeaders trailingHeaders() {
            HttpHeaders httpHeaders = this.trailingHeaders;
            if (httpHeaders == null) {
                return EmptyHttpHeaders.INSTANCE;
            }
            return httpHeaders;
        }

        void setTrailingHeaders(HttpHeaders httpHeaders) {
            this.trailingHeaders = httpHeaders;
        }

        @Override
        public HttpVersion getProtocolVersion() {
            return this.message.protocolVersion();
        }

        @Override
        public HttpVersion protocolVersion() {
            return this.message.protocolVersion();
        }

        @Override
        public FullHttpMessage setProtocolVersion(HttpVersion httpVersion) {
            this.message.setProtocolVersion(httpVersion);
            return this;
        }

        @Override
        public HttpHeaders headers() {
            return this.message.headers();
        }

        @Override
        public DecoderResult decoderResult() {
            return this.message.decoderResult();
        }

        @Override
        public DecoderResult getDecoderResult() {
            return this.message.decoderResult();
        }

        @Override
        public void setDecoderResult(DecoderResult decoderResult) {
            this.message.setDecoderResult(decoderResult);
        }

        @Override
        public ByteBuf content() {
            return this.content;
        }

        @Override
        public int refCnt() {
            return this.content.refCnt();
        }

        @Override
        public FullHttpMessage retain() {
            this.content.retain();
            return this;
        }

        @Override
        public FullHttpMessage retain(int n) {
            this.content.retain(n);
            return this;
        }

        @Override
        public FullHttpMessage touch(Object object) {
            this.content.touch(object);
            return this;
        }

        @Override
        public FullHttpMessage touch() {
            this.content.touch();
            return this;
        }

        @Override
        public boolean release() {
            return this.content.release();
        }

        @Override
        public boolean release(int n) {
            return this.content.release(n);
        }

        @Override
        public abstract FullHttpMessage copy();

        @Override
        public abstract FullHttpMessage duplicate();

        @Override
        public abstract FullHttpMessage retainedDuplicate();

        @Override
        public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
            return this.setProtocolVersion(httpVersion);
        }

        @Override
        public LastHttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastHttpContent touch() {
            return this.touch();
        }

        @Override
        public LastHttpContent retain() {
            return this.retain();
        }

        @Override
        public LastHttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastHttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastHttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastHttpContent copy() {
            return this.copy();
        }

        @Override
        public HttpContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public HttpContent touch() {
            return this.touch();
        }

        @Override
        public HttpContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public HttpContent retain() {
            return this.retain();
        }

        @Override
        public HttpContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public HttpContent duplicate() {
            return this.duplicate();
        }

        @Override
        public HttpContent copy() {
            return this.copy();
        }

        @Override
        public ByteBufHolder touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ByteBufHolder touch() {
            return this.touch();
        }

        @Override
        public ByteBufHolder retain(int n) {
            return this.retain(n);
        }

        @Override
        public ByteBufHolder retain() {
            return this.retain();
        }

        @Override
        public ByteBufHolder retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public ByteBufHolder duplicate() {
            return this.duplicate();
        }

        @Override
        public ByteBufHolder copy() {
            return this.copy();
        }

        @Override
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }

        @Override
        public ReferenceCounted touch() {
            return this.touch();
        }

        @Override
        public ReferenceCounted retain(int n) {
            return this.retain(n);
        }

        @Override
        public ReferenceCounted retain() {
            return this.retain();
        }
    }
}

