/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

final class RedisCodecUtil {
    private RedisCodecUtil() {
    }

    static byte[] longToAsciiBytes(long l) {
        return Long.toString(l).getBytes(CharsetUtil.US_ASCII);
    }

    static short makeShort(char c, char c2) {
        return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)(c2 << 8 | c) : (short)(c << 8 | c2);
    }

    static byte[] shortToBytes(short s) {
        byte[] byArray = new byte[2];
        if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
            byArray[1] = (byte)(s >> 8 & 0xFF);
            byArray[0] = (byte)(s & 0xFF);
        } else {
            byArray[0] = (byte)(s >> 8 & 0xFF);
            byArray[1] = (byte)(s & 0xFF);
        }
        return byArray;
    }
}

