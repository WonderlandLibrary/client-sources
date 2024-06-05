package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.util.List;
import net.a66;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.packets.Direction;

public class h_ {
   private static int b;

   public static Object b(PacketWrapper var0, Type var1, int var2) {
      return var0.get(var1, var2);
   }

   public static void a(PacketWrapper var0, Type var1, int var2, Object var3) {
      var0.set(var1, var2, var3);
   }

   public static void e(PacketWrapper var0) {
      var0.cancel();
   }

   public static UserConnection b(PacketWrapper var0) {
      return var0.user();
   }

   public static PacketWrapper a(PacketWrapper var0, int var1) {
      return var0.create(var1);
   }

   public static void a(PacketWrapper var0, Type var1, Object var2) {
      var0.write(var1, var2);
   }

   public static void b(PacketWrapper var0, Class var1) {
      var0.send(var1);
   }

   public static Object b(PacketWrapper var0, Type var1) {
      return var0.read(var1);
   }

   public static Object a(PacketWrapper var0, Type var1) {
      return var0.passthrough(var1);
   }

   public static void b(PacketWrapper var0, Class var1, boolean var2, boolean var3) {
      var0.send(var1, var2, var3);
   }

   public static void a(PacketWrapper var0, ByteBuf var1) {
      var0.writeToBuffer(var1);
   }

   public static void b(PacketWrapper var0, int var1) {
      var0.setId(var1);
   }

   public static void a(PacketWrapper var0, Class var1) {
      var0.sendToServer(var1);
   }

   public static boolean d(PacketWrapper var0) {
      return var0.isCancelled();
   }

   public static void a(PacketWrapper var0) {
      var0.clearInputBuffer();
   }

   public static int f(PacketWrapper var0) {
      return var0.getId();
   }

   public static void a(PacketWrapper var0, Class var1, boolean var2) {
      var0.send(var1, var2);
   }

   public static void g(PacketWrapper var0) {
      var0.clearPacket();
   }

   public static PacketWrapper a(PacketWrapper var0, int var1, ValueCreator var2) {
      return var0.create(var1, var2);
   }

   public static void a(PacketWrapper var0, Class var1, boolean var2, boolean var3) {
      var0.sendToServer(var1, var2, var3);
   }

   public static boolean a(PacketWrapper var0, Type var1, int var2) {
      return var0.isReadable(var1, var2);
   }

   public static boolean c(PacketWrapper var0, Type var1, int var2) {
      return var0.is(var1, var2);
   }

   public static void c(PacketWrapper var0) {
      var0.resetReader();
   }

   public static ChannelFuture c(PacketWrapper var0, Class var1) {
      return var0.sendFuture(var1);
   }

   public static PacketWrapper a(PacketWrapper var0, Direction var1, a66 var2, int var3, List var4, boolean var5) {
      return var0.a(var1, var2, var3, var4, var5);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 115;
   }

   static {
      if(c() == 0) {
         b(4);
      }

   }
}
