package net;

import java.util.List;
import net.minecraft.network.play.server.S0FPacketSpawnMob;

public class _1 {
   public static int a(S0FPacketSpawnMob var0) {
      return var0.getX();
   }

   public static int d(S0FPacketSpawnMob var0) {
      return var0.getY();
   }

   public static int f(S0FPacketSpawnMob var0) {
      return var0.getZ();
   }

   public static byte l(S0FPacketSpawnMob var0) {
      return var0.getYaw();
   }

   public static byte b(S0FPacketSpawnMob var0) {
      return var0.getPitch();
   }

   public static int k(S0FPacketSpawnMob var0) {
      return var0.getEntityType();
   }

   public static byte c(S0FPacketSpawnMob var0) {
      return var0.getHeadPitch();
   }

   public static int g(S0FPacketSpawnMob var0) {
      return var0.getEntityID();
   }

   public static int j(S0FPacketSpawnMob var0) {
      return var0.getVelocityX();
   }

   public static int e(S0FPacketSpawnMob var0) {
      return var0.getVelocityY();
   }

   public static int h(S0FPacketSpawnMob var0) {
      return var0.getVelocityZ();
   }

   public static List i(S0FPacketSpawnMob var0) {
      return var0.func_149027_c();
   }
}
