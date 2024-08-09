/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisCodecException;
import io.netty.handler.codec.redis.RedisMessage;

public class BulkStringHeaderRedisMessage
implements RedisMessage {
    private final int bulkStringLength;

    public BulkStringHeaderRedisMessage(int n) {
        if (n <= 0) {
            throw new RedisCodecException("bulkStringLength: " + n + " (expected: > 0)");
        }
        this.bulkStringLength = n;
    }

    public final int bulkStringLength() {
        return this.bulkStringLength;
    }

    public boolean isNull() {
        return this.bulkStringLength == -1;
    }
}

