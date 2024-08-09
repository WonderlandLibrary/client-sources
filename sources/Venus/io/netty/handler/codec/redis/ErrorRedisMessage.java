/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.AbstractStringRedisMessage;

public final class ErrorRedisMessage
extends AbstractStringRedisMessage {
    public ErrorRedisMessage(String string) {
        super(string);
    }
}

