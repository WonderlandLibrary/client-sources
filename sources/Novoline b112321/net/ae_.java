package net;

import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$Plane;

public class ae_ {
   public static boolean b(EnumFacing$Axis var0) {
      return var0.isHorizontal();
   }

   public static boolean c(EnumFacing$Axis var0) {
      return var0.isVertical();
   }

   public static EnumFacing$Axis a(String var0) {
      return EnumFacing$Axis.a(var0);
   }

   public static EnumFacing$Plane a(EnumFacing$Axis var0) {
      return var0.getPlane();
   }
}
