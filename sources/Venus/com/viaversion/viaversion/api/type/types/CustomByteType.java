/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.PartialType;
import io.netty.buffer.ByteBuf;

public class CustomByteType
extends PartialType<byte[], Integer> {
    public CustomByteType(Integer n) {
        super(n, byte[].class);
    }

    @Override
    public byte[] read(ByteBuf byteBuf, Integer n) throws Exception {
        if (byteBuf.readableBytes() < n) {
            throw new RuntimeException("Readable bytes does not match expected!");
        }
        byte[] byArray = new byte[n.intValue()];
        byteBuf.readBytes(byArray);
        return byArray;
    }

    @Override
    public void write(ByteBuf byteBuf, Integer n, byte[] byArray) throws Exception {
        byteBuf.writeBytes(byArray);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object, Object object2) throws Exception {
        this.write(byteBuf, (Integer)object, (byte[])object2);
    }

    @Override
    public Object read(ByteBuf byteBuf, Object object) throws Exception {
        return this.read(byteBuf, (Integer)object);
    }
}

