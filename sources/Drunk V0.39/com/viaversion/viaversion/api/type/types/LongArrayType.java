/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class LongArrayType
extends Type<long[]> {
    public LongArrayType() {
        super(long[].class);
    }

    @Override
    public long[] read(ByteBuf buffer) throws Exception {
        int length = Type.VAR_INT.readPrimitive(buffer);
        long[] array = new long[length];
        int i = 0;
        while (i < array.length) {
            array[i] = Type.LONG.readPrimitive(buffer);
            ++i;
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, long[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        long[] lArray = object;
        int n = lArray.length;
        int n2 = 0;
        while (n2 < n) {
            long l = lArray[n2];
            Type.LONG.writePrimitive(buffer, l);
            ++n2;
        }
    }
}

