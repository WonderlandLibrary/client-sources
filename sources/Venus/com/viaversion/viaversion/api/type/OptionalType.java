/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class OptionalType<T>
extends Type<T> {
    private final Type<T> type;

    protected OptionalType(Type<T> type) {
        super(type.getOutputClass());
        this.type = type;
    }

    @Override
    public @Nullable T read(ByteBuf byteBuf) throws Exception {
        return byteBuf.readBoolean() ? (T)this.type.read(byteBuf) : null;
    }

    @Override
    public void write(ByteBuf byteBuf, @Nullable T t) throws Exception {
        if (t == null) {
            byteBuf.writeBoolean(true);
        } else {
            byteBuf.writeBoolean(false);
            this.type.write(byteBuf, t);
        }
    }
}

