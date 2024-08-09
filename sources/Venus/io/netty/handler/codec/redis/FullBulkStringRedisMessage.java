/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.handler.codec.redis.LastBulkStringRedisContent;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FullBulkStringRedisMessage
extends DefaultByteBufHolder
implements LastBulkStringRedisContent {
    public static final FullBulkStringRedisMessage NULL_INSTANCE = new FullBulkStringRedisMessage(){

        @Override
        public boolean isNull() {
            return false;
        }

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public FullBulkStringRedisMessage copy() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage duplicate() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage retainedDuplicate() {
            return this;
        }

        @Override
        public int refCnt() {
            return 0;
        }

        @Override
        public FullBulkStringRedisMessage retain() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage retain(int n) {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage touch() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage touch(Object object) {
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
        public LastBulkStringRedisContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastBulkStringRedisContent touch() {
            return this.touch();
        }

        @Override
        public LastBulkStringRedisContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastBulkStringRedisContent retain() {
            return this.retain();
        }

        @Override
        public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
            return super.replace(byteBuf);
        }

        @Override
        public LastBulkStringRedisContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastBulkStringRedisContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastBulkStringRedisContent copy() {
            return this.copy();
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
            return super.replace(byteBuf);
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
            return super.replace(byteBuf);
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
    public static final FullBulkStringRedisMessage EMPTY_INSTANCE = new FullBulkStringRedisMessage(){

        @Override
        public ByteBuf content() {
            return Unpooled.EMPTY_BUFFER;
        }

        @Override
        public FullBulkStringRedisMessage copy() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage duplicate() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage retainedDuplicate() {
            return this;
        }

        @Override
        public int refCnt() {
            return 0;
        }

        @Override
        public FullBulkStringRedisMessage retain() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage retain(int n) {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage touch() {
            return this;
        }

        @Override
        public FullBulkStringRedisMessage touch(Object object) {
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
        public LastBulkStringRedisContent touch(Object object) {
            return this.touch(object);
        }

        @Override
        public LastBulkStringRedisContent touch() {
            return this.touch();
        }

        @Override
        public LastBulkStringRedisContent retain(int n) {
            return this.retain(n);
        }

        @Override
        public LastBulkStringRedisContent retain() {
            return this.retain();
        }

        @Override
        public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
            return super.replace(byteBuf);
        }

        @Override
        public LastBulkStringRedisContent retainedDuplicate() {
            return this.retainedDuplicate();
        }

        @Override
        public LastBulkStringRedisContent duplicate() {
            return this.duplicate();
        }

        @Override
        public LastBulkStringRedisContent copy() {
            return this.copy();
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
            return super.replace(byteBuf);
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
            return super.replace(byteBuf);
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

    private FullBulkStringRedisMessage() {
        this(Unpooled.EMPTY_BUFFER);
    }

    public FullBulkStringRedisMessage(ByteBuf byteBuf) {
        super(byteBuf);
    }

    public boolean isNull() {
        return true;
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "content=" + this.content() + ']';
    }

    @Override
    public FullBulkStringRedisMessage copy() {
        return (FullBulkStringRedisMessage)super.copy();
    }

    @Override
    public FullBulkStringRedisMessage duplicate() {
        return (FullBulkStringRedisMessage)super.duplicate();
    }

    @Override
    public FullBulkStringRedisMessage retainedDuplicate() {
        return (FullBulkStringRedisMessage)super.retainedDuplicate();
    }

    @Override
    public FullBulkStringRedisMessage replace(ByteBuf byteBuf) {
        return new FullBulkStringRedisMessage(byteBuf);
    }

    @Override
    public FullBulkStringRedisMessage retain() {
        super.retain();
        return this;
    }

    @Override
    public FullBulkStringRedisMessage retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public FullBulkStringRedisMessage touch() {
        super.touch();
        return this;
    }

    @Override
    public FullBulkStringRedisMessage touch(Object object) {
        super.touch(object);
        return this;
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
    public LastBulkStringRedisContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public LastBulkStringRedisContent touch() {
        return this.touch();
    }

    @Override
    public LastBulkStringRedisContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public LastBulkStringRedisContent retain() {
        return this.retain();
    }

    @Override
    public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public LastBulkStringRedisContent retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public LastBulkStringRedisContent duplicate() {
        return this.duplicate();
    }

    @Override
    public LastBulkStringRedisContent copy() {
        return this.copy();
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

    FullBulkStringRedisMessage(1 var1_1) {
        this();
    }
}

