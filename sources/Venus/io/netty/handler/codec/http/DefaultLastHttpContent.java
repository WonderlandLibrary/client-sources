/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultLastHttpContent
extends DefaultHttpContent
implements LastHttpContent {
    private final HttpHeaders trailingHeaders;
    private final boolean validateHeaders;

    public DefaultLastHttpContent() {
        this(Unpooled.buffer(0));
    }

    public DefaultLastHttpContent(ByteBuf byteBuf) {
        this(byteBuf, true);
    }

    public DefaultLastHttpContent(ByteBuf byteBuf, boolean bl) {
        super(byteBuf);
        this.trailingHeaders = new TrailingHttpHeaders(bl);
        this.validateHeaders = bl;
    }

    @Override
    public LastHttpContent copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public LastHttpContent duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public LastHttpContent retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public LastHttpContent replace(ByteBuf byteBuf) {
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(byteBuf, this.validateHeaders);
        defaultLastHttpContent.trailingHeaders().set(this.trailingHeaders());
        return defaultLastHttpContent;
    }

    @Override
    public LastHttpContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public LastHttpContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastHttpContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastHttpContent touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return this.trailingHeaders;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append(StringUtil.NEWLINE);
        this.appendHeaders(stringBuilder);
        stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
        return stringBuilder.toString();
    }

    private void appendHeaders(StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : this.trailingHeaders()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(StringUtil.NEWLINE);
        }
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

    private static final class TrailingHttpHeaders
    extends DefaultHttpHeaders {
        private static final DefaultHeaders.NameValidator<CharSequence> TrailerNameValidator = new DefaultHeaders.NameValidator<CharSequence>(){

            @Override
            public void validateName(CharSequence charSequence) {
                DefaultHttpHeaders.HttpNameValidator.validateName(charSequence);
                if (HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase(charSequence) || HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase(charSequence) || HttpHeaderNames.TRAILER.contentEqualsIgnoreCase(charSequence)) {
                    throw new IllegalArgumentException("prohibited trailing header: " + charSequence);
                }
            }

            @Override
            public void validateName(Object object) {
                this.validateName((CharSequence)object);
            }
        };

        TrailingHttpHeaders(boolean bl) {
            super(bl, bl ? TrailerNameValidator : DefaultHeaders.NameValidator.NOT_NULL);
        }
    }
}

