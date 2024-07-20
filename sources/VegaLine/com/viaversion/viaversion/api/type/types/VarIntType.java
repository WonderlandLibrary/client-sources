/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

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

    public int readPrimitive(ByteBuf buffer) {
        byte in;
        int value = 0;
        int bytes = 0;
        do {
            in = buffer.readByte();
            value |= (in & 0x7F) << bytes++ * 7;
            if (bytes <= 5) continue;
            throw new RuntimeException("VarInt too big");
        } while ((in & 0x80) == 128);
        return value;
    }

    public void writePrimitive(ByteBuf buffer, int value) {
        while ((value & 0xFFFFFF80) != 0) {
            buffer.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
        buffer.writeByte(value);
    }

    @Override
    @Deprecated
    public Integer read(ByteBuf buffer) {
        return this.readPrimitive(buffer);
    }

    @Override
    @Deprecated
    public void write(ByteBuf buffer, Integer object) {
        this.writePrimitive(buffer, object);
    }

    @Override
    public Integer from(Object o) {
        if (o instanceof Number) {
            return ((Number)o).intValue();
        }
        if (o instanceof Boolean) {
            return (Boolean)o != false ? 1 : 0;
        }
        return (Integer)o;
    }
}

