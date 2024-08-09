/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArrayType
extends Type<long[]> {
    public LongArrayType() {
        super(long[].class);
    }

    @Override
    public long[] read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        long[] lArray = new long[n];
        for (int i = 0; i < lArray.length; ++i) {
            lArray[i] = byteBuf.readLong();
        }
        return lArray;
    }

    @Override
    public void write(ByteBuf byteBuf, long[] lArray) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, lArray.length);
        for (long l : lArray) {
            byteBuf.writeLong(l);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (long[])object);
    }
}

