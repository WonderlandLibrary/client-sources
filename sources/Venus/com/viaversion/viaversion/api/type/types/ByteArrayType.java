/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteArrayType
extends Type<byte[]> {
    private final int length;

    public ByteArrayType(int n) {
        super(byte[].class);
        this.length = n;
    }

    public ByteArrayType() {
        super(byte[].class);
        this.length = -1;
    }

    @Override
    public void write(ByteBuf byteBuf, byte[] byArray) throws Exception {
        if (this.length != -1) {
            Preconditions.checkArgument(this.length == byArray.length, "Length does not match expected length");
        } else {
            Type.VAR_INT.writePrimitive(byteBuf, byArray.length);
        }
        byteBuf.writeBytes(byArray);
    }

    @Override
    public byte[] read(ByteBuf byteBuf) throws Exception {
        int n = this.length == -1 ? Type.VAR_INT.readPrimitive(byteBuf) : this.length;
        Preconditions.checkArgument(byteBuf.isReadable(n), "Length is fewer than readable bytes");
        byte[] byArray = new byte[n];
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

    public static final class OptionalByteArrayType
    extends OptionalType<byte[]> {
        public OptionalByteArrayType() {
            super(Type.BYTE_ARRAY_PRIMITIVE);
        }

        public OptionalByteArrayType(int n) {
            super(new ByteArrayType(n));
        }
    }
}

