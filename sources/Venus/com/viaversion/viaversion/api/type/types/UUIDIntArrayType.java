/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UUIDIntArrayType
extends Type<UUID> {
    public UUIDIntArrayType() {
        super(UUID.class);
    }

    @Override
    public UUID read(ByteBuf byteBuf) {
        int[] nArray = new int[]{byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt()};
        return UUIDIntArrayType.uuidFromIntArray(nArray);
    }

    @Override
    public void write(ByteBuf byteBuf, UUID uUID) {
        int[] nArray = UUIDIntArrayType.uuidToIntArray(uUID);
        byteBuf.writeInt(nArray[0]);
        byteBuf.writeInt(nArray[1]);
        byteBuf.writeInt(nArray[2]);
        byteBuf.writeInt(nArray[3]);
    }

    public static UUID uuidFromIntArray(int[] nArray) {
        return new UUID((long)nArray[0] << 32 | (long)nArray[1] & 0xFFFFFFFFL, (long)nArray[2] << 32 | (long)nArray[3] & 0xFFFFFFFFL);
    }

    public static int[] uuidToIntArray(UUID uUID) {
        return UUIDIntArrayType.bitsToIntArray(uUID.getMostSignificantBits(), uUID.getLeastSignificantBits());
    }

    public static int[] bitsToIntArray(long l, long l2) {
        return new int[]{(int)(l >> 32), (int)l, (int)(l2 >> 32), (int)l2};
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (UUID)object);
    }
}

