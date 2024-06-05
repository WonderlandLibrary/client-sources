package com.viaversion.viaversion.util;

import com.viaversion.viaversion.util.Config;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.af_;

public class PipelineUtil {
   private static Method DECODE_METHOD;
   private static Method ENCODE_METHOD;
   private static Method MTM_DECODE;

   public static List callDecode(ByteToMessageDecoder var0, ChannelHandlerContext var1, Object var2) throws InvocationTargetException {
      ArrayList var3 = new ArrayList();

      try {
         DECODE_METHOD.invoke(var0, new Object[]{var1, var2, var3});
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return var3;
   }

   public static void callEncode(MessageToByteEncoder var0, ChannelHandlerContext var1, Object var2, ByteBuf var3) throws InvocationTargetException {
      try {
         ENCODE_METHOD.invoke(var0, new Object[]{var1, var2, var3});
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

   }

   public static List callDecode(MessageToMessageDecoder var0, ChannelHandlerContext var1, Object var2) throws InvocationTargetException {
      ArrayList var3 = new ArrayList();

      try {
         MTM_DECODE.invoke(var0, new Object[]{var1, var2, var3});
      } catch (IllegalAccessException var5) {
         var5.printStackTrace();
      }

      return var3;
   }

   public static boolean containsCause(Throwable var0, Class var1) {
      String var2 = Config.c();
      if(var1.isAssignableFrom(var0.getClass())) {
         return true;
      } else {
         var0 = var0.getCause();
         return false;
      }
   }

   public static ChannelHandlerContext getContextBefore(String var0, ChannelPipeline var1) {
      boolean var3 = false;
      Config.c();
      Iterator var4 = var1.names().iterator();
      if(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(var3) {
            return var1.context(var1.get(var5));
         }

         if(var5.equalsIgnoreCase(var0)) {
            var3 = true;
         }
      }

      return null;
   }

   public static ChannelHandlerContext getPreviousContext(String var0, ChannelPipeline var1) {
      Object var3 = null;
      Config.c();
      Iterator var4 = var1.toMap().keySet().iterator();
      if(var4.hasNext()) {
         String var5 = (String)var4.next();
         if(var5.equals(var0)) {
            return var1.context((String)var3);
         }
      }

      return null;
   }

   static {
      try {
         DECODE_METHOD = af_.a(ByteToMessageDecoder.class, "decode", new Class[]{ChannelHandlerContext.class, ByteBuf.class, List.class});
         DECODE_METHOD.setAccessible(true);
      } catch (NoSuchMethodException var11) {
         var11.printStackTrace();
      }

      try {
         ENCODE_METHOD = af_.a(MessageToByteEncoder.class, "encode", new Class[]{ChannelHandlerContext.class, Object.class, ByteBuf.class});
         ENCODE_METHOD.setAccessible(true);
      } catch (NoSuchMethodException var10) {
         var10.printStackTrace();
      }

      try {
         MTM_DECODE = af_.a(MessageToMessageDecoder.class, "decode", new Class[]{ChannelHandlerContext.class, Object.class, List.class});
         MTM_DECODE.setAccessible(true);
      } catch (NoSuchMethodException var9) {
         var9.printStackTrace();
      }

   }
}
