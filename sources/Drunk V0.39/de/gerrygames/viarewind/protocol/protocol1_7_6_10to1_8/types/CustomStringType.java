/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class CustomStringType
extends PartialType<String[], Integer> {
    public CustomStringType(Integer param) {
        super(param, String[].class);
    }

    @Override
    public String[] read(ByteBuf buffer, Integer size) throws Exception {
        if (buffer.readableBytes() < size / 4) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        String[] array = new String[size.intValue()];
        int i = 0;
        while (i < size) {
            array[i] = (String)Type.STRING.read(buffer);
            ++i;
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, Integer size, String[] strings) throws Exception {
        String[] stringArray = strings;
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String s = stringArray[n2];
            Type.STRING.write(buffer, s);
            ++n2;
        }
    }
}

