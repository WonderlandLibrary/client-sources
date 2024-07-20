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

    protected OptionalType(Type<T> type2) {
        super(type2.getOutputClass());
        this.type = type2;
    }

    @Override
    public @Nullable T read(ByteBuf buffer) throws Exception {
        return buffer.readBoolean() ? (T)this.type.read(buffer) : null;
    }

    @Override
    public void write(ByteBuf buffer, @Nullable T value) throws Exception {
        if (value == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            this.type.write(buffer, value);
        }
    }
}

