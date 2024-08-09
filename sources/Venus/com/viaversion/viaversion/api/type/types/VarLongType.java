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
public class VarLongType
extends Type<Long>
implements TypeConverter<Long> {
    public VarLongType() {
        super("VarLong", Long.class);
    }

    public long readPrimitive(ByteBuf byteBuf) {
        byte by;
        long l = 0L;
        int n = 0;
        do {
            by = byteBuf.readByte();
            l |= (long)(by & 0x7F) << n++ * 7;
            if (n <= 10) continue;
            throw new RuntimeException("VarLong too big");
        } while ((by & 0x80) == 128);
        return l;
    }

    public void writePrimitive(ByteBuf byteBuf, long l) {
        do {
            int n = (int)(l & 0x7FL);
            if ((l >>>= 7) != 0L) {
                n |= 0x80;
            }
            byteBuf.writeByte(n);
        } while (l != 0L);
    }

    @Override
    @Deprecated
    public Long read(ByteBuf byteBuf) {
        return this.readPrimitive(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Long l) {
        this.writePrimitive(byteBuf, l);
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

