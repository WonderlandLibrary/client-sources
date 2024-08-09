/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMessageUtil;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultFullHttpRequest
extends DefaultHttpRequest
implements FullHttpRequest {
    private final ByteBuf content;
    private final HttpHeaders trailingHeader;
    private int hash;

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string) {
        this(httpVersion, httpMethod, string, Unpooled.buffer(0));
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, ByteBuf byteBuf) {
        this(httpVersion, httpMethod, string, byteBuf, true);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, boolean bl) {
        this(httpVersion, httpMethod, string, Unpooled.buffer(0), bl);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, ByteBuf byteBuf, boolean bl) {
        super(httpVersion, httpMethod, string, bl);
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.trailingHeader = new DefaultHttpHeaders(bl);
    }

    public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod httpMethod, String string, ByteBuf byteBuf, HttpHeaders httpHeaders, HttpHeaders httpHeaders2) {
        super(httpVersion, httpMethod, string, httpHeaders);
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.trailingHeader = ObjectUtil.checkNotNull(httpHeaders2, "trailingHeader");
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeader;
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
    public FullHttpRequest retain() {
        this.content.retain();
        return this;
    }

    @Override
    public FullHttpRequest retain(int n) {
        this.content.retain(n);
        return this;
    }

    @Override
    public FullHttpRequest touch() {
        this.content.touch();
        return this;
    }

    @Override
    public FullHttpRequest touch(Object object) {
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
    public FullHttpRequest setProtocolVersion(HttpVersion httpVersion) {
        super.setProtocolVersion(httpVersion);
        return this;
    }

    @Override
    public FullHttpRequest setMethod(HttpMethod httpMethod) {
        super.setMethod(httpMethod);
        return this;
    }

    @Override
    public FullHttpRequest setUri(String string) {
        super.setUri(string);
        return this;
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
        if (!(object instanceof DefaultFullHttpRequest)) {
            return true;
        }
        DefaultFullHttpRequest defaultFullHttpRequest = (DefaultFullHttpRequest)object;
        return super.equals(defaultFullHttpRequest) && this.content().equals(defaultFullHttpRequest.content()) && this.trailingHeaders().equals(defaultFullHttpRequest.trailingHeaders());
    }

    @Override
    public String toString() {
        return HttpMessageUtil.appendFullRequest(new StringBuilder(256), this).toString();
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

