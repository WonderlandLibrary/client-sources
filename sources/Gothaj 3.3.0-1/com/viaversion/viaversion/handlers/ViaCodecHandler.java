package com.viaversion.viaversion.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface ViaCodecHandler {
   void transform(ByteBuf var1) throws Exception;

   void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception;
}
