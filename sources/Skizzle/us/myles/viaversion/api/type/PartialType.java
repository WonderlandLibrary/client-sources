/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;

public abstract class PartialType<T, X>
extends Type<T> {
    private final X param;

    public PartialType(X param, Class<T> type) {
        super(type);
        this.param = param;
    }

    public abstract T read(ByteBuf var1, X var2) throws Exception;

    public abstract void write(ByteBuf var1, X var2, T var3) throws Exception;

    @Override
    public T read(ByteBuf buffer) throws Exception {
        return this.read(buffer, this.param);
    }

    @Override
    public void write(ByteBuf buffer, T object) throws Exception {
        this.write(buffer, this.param, object);
    }
}

