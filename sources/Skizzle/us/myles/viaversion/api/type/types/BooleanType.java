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

public class BooleanType
extends Type<Boolean>
implements TypeConverter<Boolean> {
    public BooleanType() {
        super(Boolean.class);
    }

    @Override
    public Boolean read(ByteBuf buffer) {
        return buffer.readBoolean();
    }

    @Override
    public void write(ByteBuf buffer, Boolean object) {
        buffer.writeBoolean(object.booleanValue());
    }

    @Override
    public Boolean from(Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue() == 1;
        }
        return (Boolean)o;
    }
}

