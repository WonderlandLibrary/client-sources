/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.internal.StringUtil;

public final class IntegerRedisMessage
implements RedisMessage {
    private final long value;

    public IntegerRedisMessage(long l) {
        this.value = l;
    }

    public long value() {
        return this.value;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "value=" + this.value + ']';
    }
}

