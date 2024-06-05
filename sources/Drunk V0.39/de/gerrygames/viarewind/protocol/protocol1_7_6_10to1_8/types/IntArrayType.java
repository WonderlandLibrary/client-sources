/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class IntArrayType
extends Type<int[]> {
    public IntArrayType() {
        super(int[].class);
    }

    @Override
    public int[] read(ByteBuf byteBuf) throws Exception {
        byte size = byteBuf.readByte();
        int[] array = new int[size];
        byte i = 0;
        while (i < size) {
            array[i] = byteBuf.readInt();
            i = (byte)(i + 1);
        }
        return array;
    }

    @Override
    public void write(ByteBuf byteBuf, int[] array) throws Exception {
        byteBuf.writeByte(array.length);
        int[] nArray = array;
        int n = nArray.length;
        int n2 = 0;
        while (n2 < n) {
            int i = nArray[n2];
            byteBuf.writeInt(i);
            ++n2;
        }
    }
}

