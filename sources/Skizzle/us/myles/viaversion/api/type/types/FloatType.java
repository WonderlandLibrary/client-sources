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

public class FloatType
extends Type<Float>
implements TypeConverter<Float> {
    public FloatType() {
        super(Float.class);
    }

    public float readPrimitive(ByteBuf buffer) {
        return buffer.readFloat();
    }

    public void writePrimitive(ByteBuf buffer, float object) {
        buffer.writeFloat(object);
    }

    @Override
    @Deprecated
    public Float read(ByteBuf buffer) {
        return Float.valueOf(buffer.readFloat());
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Float object) {
        buffer.writeFloat(object.floatValue());
    }

    @Override
    public Float from(Object o) {
        if (o instanceof Number) {
            return Float.valueOf(((Number)o).floatValue());
        }
        if (o instanceof Boolean) {
            return Float.valueOf((Boolean)o != false ? 1.0f : 0.0f);
        }
        return (Float)o;
    }
}

