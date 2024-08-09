/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RemainingBytesType
extends Type<byte[]> {
    public RemainingBytesType() {
        super(byte[].class);
    }

    @Override
    public byte[] read(ByteBuf byteBuf) {
        byte[] byArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byArray);
        return byArray;
    }

    @Override
    public void write(ByteBuf byteBuf, byte[] byArray) {
        byteBuf.writeBytes(byArray);
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

