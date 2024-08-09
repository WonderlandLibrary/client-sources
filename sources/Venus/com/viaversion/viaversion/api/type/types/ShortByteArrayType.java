/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ShortByteArrayType
extends Type<byte[]> {
    public ShortByteArrayType() {
        super(byte[].class);
    }

    @Override
    public void write(ByteBuf byteBuf, byte[] byArray) throws Exception {
        byteBuf.writeShort(byArray.length);
        byteBuf.writeBytes(byArray);
    }

    @Override
    public byte[] read(ByteBuf byteBuf) throws Exception {
        byte[] byArray = new byte[byteBuf.readShort()];
        byteBuf.readBytes(byArray);
        return byArray;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (byte[])object);
    }
}

