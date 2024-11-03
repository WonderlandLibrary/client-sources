package com.viaversion.viaversion.api.type;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface ByteBufWriter<T> {
   void write(ByteBuf var1, T var2) throws Exception;
}
