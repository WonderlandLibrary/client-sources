package net;

import net.minecraft.network.play.server.S0EPacketSpawnObject;

public class ad9 {
   public static void e(S0EPacketSpawnObject var0, int var1) {
      var0.setSpeedX(var1);
   }

   public static void d(S0EPacketSpawnObject var0, int var1) {
      var0.setSpeedY(var1);
   }

   public static void f(S0EPacketSpawnObject var0, int var1) {
      var0.setSpeedZ(var1);
   }

   public static void c(S0EPacketSpawnObject var0, int var1) {
      var0.setX(var1);
   }

   public static void a(S0EPacketSpawnObject var0, int var1) {
      var0.setY(var1);
   }

   public static void g(S0EPacketSpawnObject var0, int var1) {
      var0.setZ(var1);
   }

   public static int k(S0EPacketSpawnObject var0) {
      return var0.getX();
   }

   public static int e(S0EPacketSpawnObject var0) {
      return var0.getY();
   }

   public static int a(S0EPacketSpawnObject var0) {
      return var0.getZ();
   }

   public static int j(S0EPacketSpawnObject var0) {
      return var0.getType();
   }

   public static int i(S0EPacketSpawnObject var0) {
      return var0.func_149009_m();
   }

   public static void b(S0EPacketSpawnObject var0, int var1) {
      var0.func_149002_g(var1);
   }

   public static int h(S0EPacketSpawnObject var0) {
      return var0.getSpeedX();
   }

   public static int g(S0EPacketSpawnObject var0) {
      return var0.getSpeedY();
   }

   public static int d(S0EPacketSpawnObject var0) {
      return var0.getSpeedZ();
   }

   public static int c(S0EPacketSpawnObject var0) {
      return var0.getPitch();
   }

   public static int f(S0EPacketSpawnObject var0) {
      return var0.getYaw();
   }

   public static int b(S0EPacketSpawnObject var0) {
      return var0.getEntityID();
   }
}
