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
public class VarIntType
extends Type<Integer>
implements TypeConverter<Integer> {
    private static final int CONTINUE_BIT = 128;
    private static final int VALUE_BITS = 127;
    private static final int MULTI_BYTE_BITS = -128;
    private static final int MAX_BYTES = 5;

    public VarIntType() {
        super("VarInt", Integer.class);
    }

    public int readPrimitive(ByteBuf byteBuf) {
        byte by;
        int n = 0;
        int n2 = 0;
        do {
            by = byteBuf.readByte();
            n |= (by & 0x7F) << n2++ * 7;
            if (n2 <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((by & 0x80) == 128);
        return n;
    }

    public void writePrimitive(ByteBuf byteBuf, int n) {
        while ((n & 0xFFFFFF80) != 0) {
            byteBuf.writeByte(n & 0x7F | 0x80);
            n >>>= 7;
        }
        byteBuf.writeByte(n);
    }

    @Override
    @Deprecated
    public Integer read(ByteBuf byteBuf) {
        return this.readPrimitive(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Integer n) {
        this.writePrimitive(byteBuf, n);
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
    @Deprecated
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    @Deprecated
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Integer)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

