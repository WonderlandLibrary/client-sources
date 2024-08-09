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
public class UnsignedShortType
extends Type<Integer>
implements TypeConverter<Integer> {
    public UnsignedShortType() {
        super(Integer.class);
    }

    @Override
    public Integer read(ByteBuf byteBuf) {
        return byteBuf.readUnsignedShort();
    }

    @Override
    public void write(ByteBuf byteBuf, Integer n) {
        byteBuf.writeShort(n);
    }

    @Override
    public Integer from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        if (object instanceof Boolean) {
            return (Boolean)object != false ? 1 : 0;
        }
        return (Integer)object;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Integer)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

