package net;

import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import viaversion.viaversion.api.PacketWrapper;

public class akX {
   public static double b(PlayerPositionStorage1_13 var0) {
      return var0.getX();
   }

   public static double c(PlayerPositionStorage1_13 var0) {
      return var0.getY();
   }

   public static double a(PlayerPositionStorage1_13 var0) {
      return var0.getZ();
   }

   public static void c(PlayerPositionStorage1_13 var0, double var1) {
      var0.setX(var1);
   }

   public static void a(PlayerPositionStorage1_13 var0, double var1) {
      var0.setY(var1);
   }

   public static void b(PlayerPositionStorage1_13 var0, double var1) {
      var0.setZ(var1);
   }

   public static void a(PlayerPositionStorage1_13 var0, PacketWrapper var1, boolean var2) {
      var0.setCoordinates(var1, var2);
   }
}
