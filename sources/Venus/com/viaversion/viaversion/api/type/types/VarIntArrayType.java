/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VarIntArrayType
extends Type<int[]> {
    public VarIntArrayType() {
        super(int[].class);
    }

    @Override
    public int[] read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        Preconditions.checkArgument(byteBuf.isReadable(n));
        int[] nArray = new int[n];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = Type.VAR_INT.readPrimitive(byteBuf);
        }
        return nArray;
    }

    @Override
    public void write(ByteBuf byteBuf, int[] nArray) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, nArray.length);
        for (int n : nArray) {
            Type.VAR_INT.writePrimitive(byteBuf, n);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (int[])object);
    }
}

