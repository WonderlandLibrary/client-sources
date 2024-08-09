/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

public abstract class AbstractStringRedisMessage
implements RedisMessage {
    private final String content;

    AbstractStringRedisMessage(String string) {
        this.content = ObjectUtil.checkNotNull(string, "content");
    }

    public final String content() {
        return this.content;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "content=" + this.content + ']';
    }
}

