/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.PositionType;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Position1_14Type
extends Type<Position> {
    public Position1_14Type() {
        super(Position.class);
    }

    @Override
    public Position read(ByteBuf byteBuf) {
        long l = byteBuf.readLong();
        long l2 = l >> 38;
        long l3 = l << 52 >> 52;
        long l4 = l << 26 >> 38;
        return new Position((int)l2, (int)l3, (int)l4);
    }

    @Override
    public void write(ByteBuf byteBuf, Position position) {
        byteBuf.writeLong(((long)position.x() & 0x3FFFFFFL) << 38 | (long)(position.y() & 0xFFF) | ((long)position.z() & 0x3FFFFFFL) << 12);
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return PositionType.class;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Position)object);
    }

    public static final class OptionalPosition1_14Type
    extends OptionalType<Position> {
        public OptionalPosition1_14Type() {
            super(Type.POSITION1_14);
        }
    }
}

