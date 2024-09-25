/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;

public class RemainingBytesType
extends Type<byte[]> {
    public RemainingBytesType() {
        super(byte[].class);
    }

    @Override
    public byte[] read(ByteBuf buffer) {
        byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        return array;
    }

    @Override
    public void write(ByteBuf buffer, byte[] object) {
        buffer.writeBytes(object);
    }
}

