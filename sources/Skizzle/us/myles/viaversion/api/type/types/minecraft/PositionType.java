/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.type.Type;

public class PositionType
extends Type<Position> {
    public PositionType() {
        super(Position.class);
    }

    @Override
    public Position read(ByteBuf buffer) {
        long val = buffer.readLong();
        long x = val >> 38;
        long y = val >> 26 & 0xFFFL;
        long z = val << 38 >> 38;
        return new Position((int)x, (short)y, (int)z);
    }

    @Override
    public void write(ByteBuf buffer, Position object) {
        buffer.writeLong(((long)object.getX() & 0x3FFFFFFL) << 38 | ((long)object.getY() & 0xFFFL) << 26 | (long)(object.getZ() & 0x3FFFFFF));
    }
}

