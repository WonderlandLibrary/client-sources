package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Map;
import java.util.function.Function;
import net.cA;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.base.ProtocolInfo;

public class a8d {
   private static int b;

   public static cA b(UserConnection var0, Class var1) {
      return var0.b(var1);
   }

   public static void a(UserConnection var0, cA var1) {
      var0.a(var1);
   }

   public static boolean a(UserConnection var0, Class var1) {
      return var0.has(var1);
   }

   public static ProtocolInfo g(UserConnection var0) {
      return var0.getProtocolInfo();
   }

   public static Channel a(UserConnection var0) {
      return var0.getChannel();
   }

   public static boolean b(UserConnection var0) {
      return var0.isClientSide();
   }

   public static long f(UserConnection var0) {
      return var0.getPacketsPerSecond();
   }

   public static boolean e(UserConnection var0) {
      return var0.checkIncomingPacket();
   }

   public static boolean d(UserConnection var0) {
      return var0.shouldTransformPacket();
   }

   public static void a(UserConnection var0, ByteBuf var1, Function var2) {
      var0.transformIncoming(var1, var2);
   }

   public static void a(UserConnection var0, ByteBuf var1) {
      var0.sendRawPacket(var1);
   }

   public static void b(UserConnection var0, ByteBuf var1, boolean var2) {
      var0.sendRawPacketToServerClientSide(var1, var2);
   }

   public static void a(UserConnection var0, ByteBuf var1, boolean var2) {
      var0.sendRawPacket(var1, var2);
   }

   public static ChannelFuture b(UserConnection var0, ByteBuf var1) {
      return var0.sendRawPacketFuture(var1);
   }

   public static void c(UserConnection var0, ByteBuf var1, boolean var2) {
      var0.sendRawPacketToServer(var1, var2);
   }

   public static boolean h(UserConnection var0) {
      return var0.o();
   }

   public static void a(UserConnection var0, boolean var1) {
      var0.setActive(var1);
   }

   public static void a(UserConnection var0, ProtocolInfo var1) {
      var0.setProtocolInfo(var1);
   }

   public static Map c(UserConnection var0) {
      return var0.getStoredObjects();
   }

   public static boolean i(UserConnection var0) {
      return var0.checkOutgoingPacket();
   }

   public static void b(UserConnection var0, ByteBuf var1, Function var2) {
      var0.transformOutgoing(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 38;
   }

   static {
      if(a() != 0) {
         b(53);
      }

   }
}
