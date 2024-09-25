/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;

public class VarIntArrayType
extends Type<int[]> {
    public VarIntArrayType() {
        super(int[].class);
    }

    @Override
    public int[] read(ByteBuf buffer) throws Exception {
        int length = Type.VAR_INT.readPrimitive(buffer);
        Preconditions.checkArgument((boolean)buffer.isReadable(length));
        int[] array = new int[length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = Type.VAR_INT.readPrimitive(buffer);
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, int[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (int i : object) {
            Type.VAR_INT.writePrimitive(buffer, i);
        }
    }
}

