/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ArrayRedisMessage
extends AbstractReferenceCounted
implements RedisMessage {
    private final List<RedisMessage> children;
    public static final ArrayRedisMessage NULL_INSTANCE = new ArrayRedisMessage(){

        @Override
        public boolean isNull() {
            return false;
        }

        @Override
        public ArrayRedisMessage retain() {
            return this;
        }

        @Override
        public ArrayRedisMessage retain(int n) {
            return this;
        }

        @Override
        public ArrayRedisMessage touch() {
            return this;
        }

        @Override
        public ArrayRedisMessage touch(Object object) {
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
        public String toString() {
            return "NullArrayRedisMessage";
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
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }
    };
    public static final ArrayRedisMessage EMPTY_INSTANCE = new ArrayRedisMessage(){

        @Override
        public ArrayRedisMessage retain() {
            return this;
        }

        @Override
        public ArrayRedisMessage retain(int n) {
            return this;
        }

        @Override
        public ArrayRedisMessage touch() {
            return this;
        }

        @Override
        public ArrayRedisMessage touch(Object object) {
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
        public String toString() {
            return "EmptyArrayRedisMessage";
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
        public ReferenceCounted touch(Object object) {
            return this.touch(object);
        }
    };

    private ArrayRedisMessage() {
        this.children = Collections.emptyList();
    }

    public ArrayRedisMessage(List<RedisMessage> list) {
        this.children = ObjectUtil.checkNotNull(list, "children");
    }

    public final List<RedisMessage> children() {
        return this.children;
    }

    public boolean isNull() {
        return true;
    }

    @Override
    protected void deallocate() {
        for (RedisMessage redisMessage : this.children) {
            ReferenceCountUtil.release(redisMessage);
        }
    }

    @Override
    public ArrayRedisMessage touch(Object object) {
        for (RedisMessage redisMessage : this.children) {
            ReferenceCountUtil.touch(redisMessage);
        }
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "children=" + this.children.size() + ']';
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    ArrayRedisMessage(1 var1_1) {
        this();
    }
}

