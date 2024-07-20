/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.NBTType;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;

public class NamelessNBTType
extends Type<CompoundTag> {
    public NamelessNBTType() {
        super(CompoundTag.class);
    }

    @Override
    public CompoundTag read(ByteBuf buffer) throws Exception {
        return NBTType.read(buffer, false);
    }

    @Override
    public void write(ByteBuf buffer, CompoundTag tag) throws Exception {
        NBTType.write(buffer, tag, null);
    }
}

