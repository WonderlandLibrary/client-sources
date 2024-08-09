/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;
import net.minecraft.util.Util;

public final class UUIDCodec {
    public static final Codec<UUID> CODEC = Codec.INT_STREAM.comapFlatMap(UUIDCodec::lambda$static$0, UUIDCodec::lambda$static$1);

    public static UUID decodeUUID(int[] nArray) {
        return new UUID((long)nArray[0] << 32 | (long)nArray[1] & 0xFFFFFFFFL, (long)nArray[2] << 32 | (long)nArray[3] & 0xFFFFFFFFL);
    }

    public static int[] encodeUUID(UUID uUID) {
        long l = uUID.getMostSignificantBits();
        long l2 = uUID.getLeastSignificantBits();
        return UUIDCodec.encodeBits(l, l2);
    }

    private static int[] encodeBits(long l, long l2) {
        return new int[]{(int)(l >> 32), (int)l, (int)(l2 >> 32), (int)l2};
    }

    private static IntStream lambda$static$1(UUID uUID) {
        return Arrays.stream(UUIDCodec.encodeUUID(uUID));
    }

    private static DataResult lambda$static$0(IntStream intStream) {
        return Util.validateIntStreamSize(intStream, 4).map(UUIDCodec::decodeUUID);
    }
}

