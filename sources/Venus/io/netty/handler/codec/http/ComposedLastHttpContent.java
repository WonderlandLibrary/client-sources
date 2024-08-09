/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ComposedLastHttpContent
implements LastHttpContent {
    private final HttpHeaders trailingHeaders;
    private DecoderResult result;

    ComposedLastHttpContent(HttpHeaders httpHeaders) {
        this.trailingHeaders = httpHeaders;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }

    @Override
    public LastHttpContent copy() {
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        defaultLastHttpContent.trailingHeaders().set(this.trailingHeaders());
        return defaultLastHttpContent;
    }

    @Override
    public LastHttpContent duplicate() {
        return this.copy();
    }

    @Override
    public LastHttpContent retainedDuplicate() {
        return this.copy();
    }

    @Override
    public LastHttpContent replace(ByteBuf byteBuf) {
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(byteBuf);
        defaultLastHttpContent.trailingHeaders().setAll(this.trailingHeaders());
        return defaultLastHttpContent;
    }

    @Override
    public LastHttpContent retain(int n) {
        return this;
    }

    @Override
    public LastHttpContent retain() {
        return this;
    }

    @Override
    public LastHttpContent touch() {
        return this;
    }

    @Override
    public LastHttpContent touch(Object object) {
        return this;
    }

    @Override
    public ByteBuf content() {
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public DecoderResult decoderResult() {
        return this.result;
    }

    @Override
    public DecoderResult getDecoderResult() {
        return this.decoderResult();
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.result = decoderResult;
    }

    @Override
    public int refCnt() {
        return 0;
    }

    @Override
    public boolean release() {
        return true;
    }

    @Override
    public boolean release(int n) {
        return true;
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

