/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.handler.codec.redis.DefaultLastBulkStringRedisContent;
import io.netty.util.ReferenceCounted;

public interface LastBulkStringRedisContent
extends BulkStringRedisContent {
    public static final LastBulkStringRedisContent EMPTY_LAST_CONTENT = new LastBulkStringRedisContent(){

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public LastBulkStringRedisContent copy() {
            return this;
        }

        @Override
        public LastBulkStringRedisContent duplicate() {
            return this;
        }

        @Override
        public LastBulkStringRedisContent retainedDuplicate() {
            return this;
        }

        @Override
        public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
            return new DefaultLastBulkStringRedisContent(byteBuf);
        }

        @Override
        public LastBulkStringRedisContent retain(int n) {
            return this;
        }

        @Override
        public LastBulkStringRedisContent retain() {
            return this;
        }

        @Override
        public int refCnt() {
            return 0;
        }

        @Override
        public LastBulkStringRedisContent touch() {
            return this;
        }

        @Override
        public LastBulkStringRedisContent touch(Object object) {
            return this;
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
        public BulkStringRedisContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public BulkStringRedisContent touch() {
            return this.touch();
        }

        @Override
        public BulkStringRedisContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public BulkStringRedisContent retain() {
            return this.retain();
        }

        @Override
        public BulkStringRedisContent replace(ByteBuf byteBuf) {
            return this.replace(byteBuf);
        }

        @Override
        public BulkStringRedisContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public BulkStringRedisContent duplicate() {
            return this.duplicate();
        }

        @Override
        public BulkStringRedisContent copy() {
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
    public LastBulkStringRedisContent copy();

    @Override
    public LastBulkStringRedisContent duplicate();

    @Override
    public LastBulkStringRedisContent retainedDuplicate();

    @Override
    public LastBulkStringRedisContent replace(ByteBuf var1);

    @Override
    public LastBulkStringRedisContent retain();

    @Override
    public LastBulkStringRedisContent retain(int var1);

    @Override
    public LastBulkStringRedisContent touch();

    @Override
    public LastBulkStringRedisContent touch(Object var1);
}

