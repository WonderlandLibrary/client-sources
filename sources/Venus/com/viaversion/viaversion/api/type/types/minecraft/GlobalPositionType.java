/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GlobalPositionType
extends Type<GlobalPosition> {
    public GlobalPositionType() {
        super(GlobalPosition.class);
    }

    @Override
    public GlobalPosition read(ByteBuf byteBuf) throws Exception {
        String string = (String)Type.STRING.read(byteBuf);
        return ((Position)Type.POSITION1_14.read(byteBuf)).withDimension(string);
    }

    @Override
    public void write(ByteBuf byteBuf, GlobalPosition globalPosition) throws Exception {
        Type.STRING.write(byteBuf, globalPosition.dimension());
        Type.POSITION1_14.write(byteBuf, globalPosition);
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (GlobalPosition)object);
    }

    public static final class OptionalGlobalPositionType
    extends OptionalType<GlobalPosition> {
        public OptionalGlobalPositionType() {
            super(Type.GLOBAL_POSITION);
        }
    }
}

