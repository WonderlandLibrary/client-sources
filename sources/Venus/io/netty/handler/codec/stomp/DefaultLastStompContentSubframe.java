/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.stomp.DefaultStompContentSubframe;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultLastStompContentSubframe
extends DefaultStompContentSubframe
implements LastStompContentSubframe {
    public DefaultLastStompContentSubframe(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public LastStompContentSubframe copy() {
        return (LastStompContentSubframe)super.copy();
    }

    @Override
    public LastStompContentSubframe duplicate() {
        return (LastStompContentSubframe)super.duplicate();
    }

    @Override
    public LastStompContentSubframe retainedDuplicate() {
        return (LastStompContentSubframe)super.retainedDuplicate();
    }

    @Override
    public LastStompContentSubframe replace(ByteBuf byteBuf) {
        return new DefaultLastStompContentSubframe(byteBuf);
    }

    @Override
    public DefaultLastStompContentSubframe retain() {
        super.retain();
        return this;
    }

    @Override
    public LastStompContentSubframe retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public LastStompContentSubframe touch() {
        super.touch();
        return this;
    }

    @Override
    public LastStompContentSubframe touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public String toString() {
        return "DefaultLastStompContent{decoderResult=" + this.decoderResult() + '}';
    }

    @Override
    public StompContentSubframe touch(Object object) {
        return this.touch(object);
    }

    @Override
    public StompContentSubframe touch() {
        return this.touch();
    }

    @Override
    public StompContentSubframe retain(int n) {
        return this.retain(n);
    }

    @Override
    public StompContentSubframe retain() {
        return this.retain();
    }

    @Override
    public StompContentSubframe replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public StompContentSubframe retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public StompContentSubframe duplicate() {
        return this.duplicate();
    }

    @Override
    public StompContentSubframe copy() {
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
    public LastStompContentSubframe retain() {
        return this.retain();
    }
}

