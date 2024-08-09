/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public abstract class PartialType<T, X>
extends Type<T> {
    private final X param;

    protected PartialType(X x, Class<T> clazz) {
        super(clazz);
        this.param = x;
    }

    protected PartialType(X x, String string, Class<T> clazz) {
        super(string, clazz);
        this.param = x;
    }

    public abstract T read(ByteBuf var1, X var2) throws Exception;

    public abstract void write(ByteBuf var1, X var2, T var3) throws Exception;

    @Override
    public final T read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf, this.param);
    }

    @Override
    public final void write(ByteBuf byteBuf, T t) throws Exception {
        this.write(byteBuf, this.param, t);
    }
}

