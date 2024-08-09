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
public class LongType
extends Type<Long>
implements TypeConverter<Long> {
    public LongType() {
        super(Long.class);
    }

    @Override
    @Deprecated
    public Long read(ByteBuf byteBuf) {
        return byteBuf.readLong();
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Long l) {
        byteBuf.writeLong(l);
    }

    @Override
    public Long from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        if (object instanceof Boolean) {
            return (Boolean)object != false ? 1L : 0L;
        }
        return (Long)object;
    }

    public long readPrimitive(ByteBuf byteBuf) {
        return byteBuf.readLong();
    }

    public void writePrimitive(ByteBuf byteBuf, long l) {
        byteBuf.writeLong(l);
    }

    @Override
    @Deprecated
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Long)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

