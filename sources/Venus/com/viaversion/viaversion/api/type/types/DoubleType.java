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
public class DoubleType
extends Type<Double>
implements TypeConverter<Double> {
    public DoubleType() {
        super(Double.class);
    }

    @Override
    @Deprecated
    public Double read(ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }

    public double readPrimitive(ByteBuf byteBuf) {
        return byteBuf.readDouble();
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Double d) {
        byteBuf.writeDouble(d);
    }

    public void writePrimitive(ByteBuf byteBuf, double d) {
        byteBuf.writeDouble(d);
    }

    @Override
    public Double from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        if (object instanceof Boolean) {
            return (Boolean)object != false ? 1.0 : 0.0;
        }
        return (Double)object;
    }

    @Override
    @Deprecated
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Double)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

