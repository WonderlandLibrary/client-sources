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
public class BooleanType
extends Type<Boolean>
implements TypeConverter<Boolean> {
    public BooleanType() {
        super(Boolean.class);
    }

    @Override
    public Boolean read(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    @Override
    public void write(ByteBuf byteBuf, Boolean bl) {
        byteBuf.writeBoolean(bl);
    }

    @Override
    public Boolean from(Object object) {
        if (object instanceof Number) {
            return ((Number)object).intValue() == 1;
        }
        return (Boolean)object;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Boolean)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

