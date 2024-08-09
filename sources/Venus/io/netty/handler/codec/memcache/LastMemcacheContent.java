/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.memcache.DefaultLastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.util.ReferenceCounted;

public interface LastMemcacheContent
extends MemcacheContent {
    public static final LastMemcacheContent EMPTY_LAST_CONTENT = new LastMemcacheContent(){

        @Override
        public LastMemcacheContent copy() {
            return EMPTY_LAST_CONTENT;
        }

        @Override
        public LastMemcacheContent duplicate() {
            return this;
        }

        @Override
        public LastMemcacheContent retainedDuplicate() {
            return this;
        }

        @Override
        public LastMemcacheContent replace(ByteBuf byteBuf) {
            return new DefaultLastMemcacheContent(byteBuf);
        }

        @Override
        public LastMemcacheContent retain(int n) {
            return this;
        }

        @Override
        public LastMemcacheContent retain() {
            return this;
        }

        @Override
        public LastMemcacheContent touch() {
            return this;
        }

        @Override
        public LastMemcacheContent touch(Object object) {
            return this;
        }

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
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
        public MemcacheContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public MemcacheContent touch() {
            return this.touch();
        }

        @Override
        public MemcacheContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public MemcacheContent retain() {
            return this.retain();
        }

        @Override
        public MemcacheContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public MemcacheContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public MemcacheContent duplicate() {
            return this.duplicate();
        }

        @Override
        public MemcacheContent copy() {
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
    public LastMemcacheContent copy();

    @Override
    public LastMemcacheContent duplicate();

    @Override
    public LastMemcacheContent retainedDuplicate();

    @Override
    public LastMemcacheContent replace(ByteBuf var1);

    @Override
    public LastMemcacheContent retain(int var1);

    @Override
    public LastMemcacheContent retain();

    @Override
    public LastMemcacheContent touch();

    @Override
    public LastMemcacheContent touch(Object var1);
}

