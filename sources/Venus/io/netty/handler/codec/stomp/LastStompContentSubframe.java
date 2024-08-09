/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.stomp.DefaultLastStompContentSubframe;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.util.ReferenceCounted;

public interface LastStompContentSubframe
extends StompContentSubframe {
    public static final LastStompContentSubframe EMPTY_LAST_CONTENT = new LastStompContentSubframe(){

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public LastStompContentSubframe copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override
        public LastStompContentSubframe duplicate() {
            return this;
        }

        @Override
        public LastStompContentSubframe retainedDuplicate() {
            return this;
        }

        @Override
        public LastStompContentSubframe replace(ByteBuf byteBuf) {
            return new DefaultLastStompContentSubframe(byteBuf);
        }

        @Override
        public LastStompContentSubframe retain() {
            return this;
        }

        @Override
        public LastStompContentSubframe retain(int n) {
            return this;
        }

        @Override
        public LastStompContentSubframe touch() {
            return this;
        }

        @Override
        public LastStompContentSubframe touch(Object object) {
            return this;
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
        public DecoderResult decoderResult() {
            return DecoderResult.SUCCESS;
        }

        @Override
        public void setDecoderResult(DecoderResult decoderResult) {
            throw new UnsupportedOperationException("read only");
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
    };

    @Override
    public LastStompContentSubframe copy();

    @Override
    public LastStompContentSubframe duplicate();

    @Override
    public LastStompContentSubframe retainedDuplicate();

    @Override
    public LastStompContentSubframe replace(ByteBuf var1);

    @Override
    public LastStompContentSubframe retain();

    @Override
    public LastStompContentSubframe retain(int var1);

    @Override
    public LastStompContentSubframe touch();

    @Override
    public LastStompContentSubframe touch(Object var1);
}

