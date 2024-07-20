/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class OptionalVarIntType
extends Type<Integer> {
    public OptionalVarIntType() {
        super(Integer.class);
    }

    @Override
    public Integer read(ByteBuf buffer) throws Exception {
        int value = Type.VAR_INT.readPrimitive(buffer);
        return value == 0 ? null : Integer.valueOf(value - 1);
    }

    @Override
    public void write(ByteBuf buffer, Integer object) throws Exception {
        if (object == null) {
            Type.VAR_INT.writePrimitive(buffer, 0);
        } else {
            Type.VAR_INT.writePrimitive(buffer, object + 1);
        }
    }
}

