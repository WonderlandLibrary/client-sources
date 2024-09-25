/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type;

import io.netty.buffer.ByteBuf;

public interface ByteBufReader<T> {
    public T read(ByteBuf var1) throws Exception;
}

