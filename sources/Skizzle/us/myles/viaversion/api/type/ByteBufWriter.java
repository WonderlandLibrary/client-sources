/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type;

import io.netty.buffer.ByteBuf;

public interface ByteBufWriter<T> {
    public void write(ByteBuf var1, T var2) throws Exception;
}

