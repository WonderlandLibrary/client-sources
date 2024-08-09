/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ByteType
extends Type<Byte>
implements TypeConverter<Byte> {
    public ByteType() {
        super(Byte.class);
    }

    public byte readPrimitive(ByteBuf byteBuf) {
        return byteBuf.readByte();
    }

    public void writePrimitive(ByteBuf byteBuf, byte by) {
        byteBuf.writeByte(by);
    }

    @Override
    @Deprecated
    public Byte read(ByteBuf byteBuf) {
        return byteBuf.readByte();
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Byte by) {
        byteBuf.writeByte(by.byteValue());
    }

    @Override
    public Byte from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).byteValue();
        }
        if (object instanceof Boolean) {
            return (Boolean)object != false ? (byte)1 : 0;
        }
        return (Byte)object;
    }

    @Override
    @Deprecated
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Byte)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

