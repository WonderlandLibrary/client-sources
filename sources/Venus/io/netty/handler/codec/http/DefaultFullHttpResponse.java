/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.CombinedHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMessageUtil;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultFullHttpResponse
extends DefaultHttpResponse
implements FullHttpResponse {
    private final ByteBuf content;
    private final HttpHeaders trailingHeaders;
    private int hash;

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0));
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf) {
        this(httpVersion, httpResponseStatus, byteBuf, true);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean bl) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0), bl, false);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, boolean bl, boolean bl2) {
        this(httpVersion, httpResponseStatus, Unpooled.buffer(0), bl, bl2);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, boolean bl) {
        this(httpVersion, httpResponseStatus, byteBuf, bl, false);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, boolean bl, boolean bl2) {
        super(httpVersion, httpResponseStatus, bl, bl2);
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.trailingHeaders = bl2 ? new CombinedHttpHeaders(bl) : new DefaultHttpHeaders(bl);
    }

    public DefaultFullHttpResponse(HttpVersion httpVersion, HttpResponseStatus httpResponseStatus, ByteBuf byteBuf, HttpHeaders httpHeaders, HttpHeaders httpHeaders2) {
        super(httpVersion, httpResponseStatus, httpHeaders);
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.trailingHeaders = ObjectUtil.checkNotNull(httpHeaders2, "trailingHeaders");
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
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
    public FullHttpResponse retain() {
        this.content.retain();
        return this;
    }

    @Override
    public FullHttpResponse retain(int n) {
        this.content.retain(n);
        return this;
    }

    @Override
    public FullHttpResponse touch() {
        this.content.touch();
        return this;
    }

    @Override
    public FullHttpResponse touch(Object object) {
        this.content.touch(object);
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
    public FullHttpResponse setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override
    public FullHttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
        super.setStatus(httpResponseStatus);
        return this;
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
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(this.protocolVersion(), this.status(), byteBuf, this.headers().copy(), this.trailingHeaders().copy());
        defaultFullHttpResponse.setDecoderResult(this.decoderResult());
        return defaultFullHttpResponse;
    }

    @Override
    public int hashCode() {
        int n = this.hash;
        if (n == 0) {
            if (this.content().refCnt() != 0) {
                try {
                    n = 31 + this.content().hashCode();
                } catch (IllegalReferenceCountException illegalReferenceCountException) {
                    n = 31;
                }
            } else {
                n = 31;
            }
            n = 31 * n + this.trailingHeaders().hashCode();
            this.hash = n = 31 * n + super.hashCode();
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultFullHttpResponse)) {
            return true;
        }
        DefaultFullHttpResponse defaultFullHttpResponse = (DefaultFullHttpResponse)object;
        return super.equals(defaultFullHttpResponse) && this.content().equals(defaultFullHttpResponse.content()) && this.trailingHeaders().equals(defaultFullHttpResponse.trailingHeaders());
    }

    @Override
    public String toString() {
        return HttpMessageUtil.appendFullResponse(new StringBuilder(256), this).toString();
    }

    @Override
    public HttpResponse setProtocolVersion(HttpVersion httpVersion) {
        return this.setProtocolVersion(httpVersion);
    }

    @Override
    public HttpResponse setStatus(HttpResponseStatus httpResponseStatus) {
        return this.setStatus(httpResponseStatus);
    }

    @Override
    public HttpMessage setProtocolVersion(HttpVersion httpVersion) {
        return this.setProtocolVersion(httpVersion);
    }

    @Override
    public FullHttpMessage touch(Object object) {
        return this.touch(object);
    }

    @Override
    public FullHttpMessage touch() {
        return this.touch();
    }

    @Override
    public FullHttpMessage retain() {
        return this.retain();
    }

    @Override
    public FullHttpMessage retain(int n) {
        return this.retain(n);
    }

    @Override
    public FullHttpMessage replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
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
}

