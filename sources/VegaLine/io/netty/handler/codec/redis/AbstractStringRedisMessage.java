/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractStringRedisMessage
implements RedisMessage {
    private final String content;

    AbstractStringRedisMessage(String content) {
        this.content = ObjectUtil.checkNotNull(content, "content");
    }

    public final String content() {
        return this.content;
    }
}

