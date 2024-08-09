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
public class UnsignedByteType
extends Type<Short>
implements TypeConverter<Short> {
    public UnsignedByteType() {
        super("Unsigned Byte", Short.class);
    }

    @Override
    public Short read(ByteBuf byteBuf) {
        return byteBuf.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf byteBuf, Short s) {
        byteBuf.writeByte(s.shortValue());
    }

    @Override
    public Short from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).shortValue();
        }
        if (object instanceof Boolean) {
            return (Boolean)object != false ? (short)1 : 0;
        }
        return (Short)object;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Short)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

