/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class GlobalPositionType
extends Type<GlobalPosition> {
    public GlobalPositionType() {
        super(GlobalPosition.class);
    }

    @Override
    public GlobalPosition read(ByteBuf buffer) throws Exception {
        String dimension = (String)Type.STRING.read(buffer);
        return ((Position)Type.POSITION1_14.read(buffer)).withDimension(dimension);
    }

    @Override
    public void write(ByteBuf buffer, GlobalPosition object) throws Exception {
        Type.STRING.write(buffer, object.dimension());
        Type.POSITION1_14.write(buffer, object);
    }

    public static final class OptionalGlobalPositionType
    extends OptionalType<GlobalPosition> {
        public OptionalGlobalPositionType() {
            super(Type.GLOBAL_POSITION);
        }
    }
}

