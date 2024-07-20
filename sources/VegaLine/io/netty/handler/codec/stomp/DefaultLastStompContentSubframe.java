/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.stomp.DefaultStompContentSubframe;
import io.netty.handler.codec.stomp.LastStompContentSubframe;

public class DefaultLastStompContentSubframe
extends DefaultStompContentSubframe
implements LastStompContentSubframe {
    public DefaultLastStompContentSubframe(ByteBuf content) {
        super(content);
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
    public LastStompContentSubframe replace(ByteBuf content) {
        return new DefaultLastStompContentSubframe(content);
    }

    @Override
    public DefaultLastStompContentSubframe retain() {
        super.retain();
        return this;
    }

    @Override
    public LastStompContentSubframe retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public LastStompContentSubframe touch() {
        super.touch();
        return this;
    }

    @Override
    public LastStompContentSubframe touch(Object hint) {
        super.touch(hint);
        return this;
    }

    @Override
    public String toString() {
        return "DefaultLastStompContent{decoderResult=" + this.decoderResult() + '}';
    }
}

