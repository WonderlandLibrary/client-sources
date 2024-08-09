/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OptionalVarIntType
extends Type<Integer> {
    public OptionalVarIntType() {
        super(Integer.class);
    }

    @Override
    public Integer read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        return n == 0 ? null : Integer.valueOf(n - 1);
    }

    @Override
    public void write(ByteBuf byteBuf, Integer n) throws Exception {
        if (n == null) {
            Type.VAR_INT.writePrimitive(byteBuf, 0);
        } else {
            Type.VAR_INT.writePrimitive(byteBuf, n + 1);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Integer)object);
    }
}

