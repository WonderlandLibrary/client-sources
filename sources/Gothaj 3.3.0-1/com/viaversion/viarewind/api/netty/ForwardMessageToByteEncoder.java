package com.viaversion.viarewind.api.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ForwardMessageToByteEncoder extends MessageToByteEncoder<ByteBuf> {
   protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
      out.writeBytes(msg);
   }
}
