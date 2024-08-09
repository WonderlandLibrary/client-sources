/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.AbstractStringRedisMessage;

public final class InlineCommandRedisMessage
extends AbstractStringRedisMessage {
    public InlineCommandRedisMessage(String string) {
        super(string);
    }
}

