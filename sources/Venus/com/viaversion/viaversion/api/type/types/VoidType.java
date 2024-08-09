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
public class VoidType
extends Type<Void>
implements TypeConverter<Void> {
    public VoidType() {
        super(Void.class);
    }

    @Override
    public Void read(ByteBuf byteBuf) {
        return null;
    }

    @Override
    public void write(ByteBuf byteBuf, Void void_) {
    }

    @Override
    public Void from(Object object) {
        return null;
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Void)object);
    }

    @Override
    public Object from(Object object) {
        return this.from(object);
    }
}

