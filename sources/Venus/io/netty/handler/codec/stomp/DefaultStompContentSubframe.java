/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultStompContentSubframe
extends DefaultByteBufHolder
implements StompContentSubframe {
    private DecoderResult decoderResult = DecoderResult.SUCCESS;

    public DefaultStompContentSubframe(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public StompContentSubframe copy() {
        return (StompContentSubframe)super.copy();
    }

    @Override
    public StompContentSubframe duplicate() {
        return (StompContentSubframe)super.duplicate();
    }

    @Override
    public StompContentSubframe retainedDuplicate() {
        return (StompContentSubframe)super.retainedDuplicate();
    }

    @Override
    public StompContentSubframe replace(ByteBuf byteBuf) {
        return new DefaultStompContentSubframe(byteBuf);
    }

    @Override
    public StompContentSubframe retain() {
        super.retain();
        return this;
    }

    @Override
    public StompContentSubframe retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public StompContentSubframe touch() {
        super.touch();
        return this;
    }

    @Override
    public StompContentSubframe touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = decoderResult;
    }

    @Override
    public String toString() {
        return "DefaultStompContent{decoderResult=" + this.decoderResult + '}';
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

