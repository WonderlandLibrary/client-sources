/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.handler.codec.redis.RedisCodecUtil;

final class RedisConstants {
    static final int TYPE_LENGTH = 1;
    static final int EOL_LENGTH = 2;
    static final int NULL_LENGTH = 2;
    static final int NULL_VALUE = -1;
    static final int REDIS_MESSAGE_MAX_LENGTH = 0x20000000;
    static final int REDIS_INLINE_MESSAGE_MAX_LENGTH = 65536;
    static final int POSITIVE_LONG_MAX_LENGTH = 19;
    static final int LONG_MAX_LENGTH = 20;
    static final short NULL_SHORT = RedisCodecUtil.makeShort('-', '1');
    static final short EOL_SHORT = RedisCodecUtil.makeShort('\r', '\n');

    private RedisConstants() {
    }
}

