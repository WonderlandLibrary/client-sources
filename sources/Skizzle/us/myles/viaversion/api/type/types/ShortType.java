/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.TypeConverter;

public class ShortType
extends Type<Short>
implements TypeConverter<Short> {
    public ShortType() {
        super(Short.class);
    }

    public short readPrimitive(ByteBuf buffer) {
        return buffer.readShort();
    }

    public void writePrimitive(ByteBuf buffer, short object) {
        buffer.writeShort((int)object);
    }

    @Override
    @Deprecated
    public Short read(ByteBuf buffer) {
        return buffer.readShort();
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Short object) {
        buffer.writeShort((int)object.shortValue());
    }

    @Override
    public Short from(Object o) {
        if (o instanceof Number) {
            return ((Number)o).shortValue();
        }
        if (o instanceof Boolean) {
            return (Boolean)o != false ? (short)1 : 0;
        }
        return (Short)o;
    }
}

