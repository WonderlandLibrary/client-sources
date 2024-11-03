package com.viaversion.viaversion.classgenerator.generated;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public interface HandlerSupplier {
   MessageToMessageEncoder<ByteBuf> newEncodeHandler(UserConnection var1);

   MessageToMessageDecoder<ByteBuf> newDecodeHandler(UserConnection var1);

   public static final class DefaultHandlerSupplier implements HandlerSupplier {
      @Override
      public MessageToMessageEncoder<ByteBuf> newEncodeHandler(UserConnection connection) {
         return new BukkitEncodeHandler(connection);
      }

      @Override
      public MessageToMessageDecoder<ByteBuf> newDecodeHandler(UserConnection connection) {
         return new BukkitDecodeHandler(connection);
      }
   }
}
