/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.VillagerData;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VillagerDataType
extends Type<VillagerData> {
    public VillagerDataType() {
        super(VillagerData.class);
    }

    @Override
    public VillagerData read(ByteBuf byteBuf) throws Exception {
        return new VillagerData(Type.VAR_INT.readPrimitive(byteBuf), Type.VAR_INT.readPrimitive(byteBuf), Type.VAR_INT.readPrimitive(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf, VillagerData villagerData) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.type());
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.profession());
        Type.VAR_INT.writePrimitive(byteBuf, villagerData.level());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (VillagerData)object);
    }
}

