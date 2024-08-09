/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatType
extends Type<Float>
implements TypeConverter<Float> {
    public FloatType() {
        super(Float.class);
    }

    public float readPrimitive(ByteBuf byteBuf) {
        return byteBuf.readFloat();
    }

    public void writePrimitive(ByteBuf byteBuf, float f) {
        byteBuf.writeFloat(f);
    }

    @Override
    @Deprecated
    public Float read(ByteBuf byteBuf) {
        return Float.valueOf(byteBuf.readFloat());
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Float f) {
        byteBuf.writeFloat(f.floatValue());
    }

    @Override
    public Float from(Object object) {
        if (object instanceof Number) {
            return Float.valueOf(((Number)object).floatValue());
        }
        if (object instanceof Boolean) {
            return Float.valueOf((Boolean)object != false ? 1.0f : 0.0f);
        }
        return (Float)object;
    }

    @Override
    @Deprecated
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Float)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }

    public static final class OptionalFloatType
    extends OptionalType<Float> {
        public OptionalFloatType() {
            super(Type.FLOAT);
        }
    }
}

