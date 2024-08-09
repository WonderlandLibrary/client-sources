/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VectorType
extends Type<Vector> {
    public VectorType() {
        super(Vector.class);
    }

    @Override
    public Vector read(ByteBuf byteBuf) throws Exception {
        int n = Type.INT.read(byteBuf);
        int n2 = Type.INT.read(byteBuf);
        int n3 = Type.INT.read(byteBuf);
        return new Vector(n, n2, n3);
    }

    @Override
    public void write(ByteBuf byteBuf, Vector vector) throws Exception {
        Type.INT.write(byteBuf, vector.blockX());
        Type.INT.write(byteBuf, vector.blockY());
        Type.INT.write(byteBuf, vector.blockZ());
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Vector)object);
    }
}

