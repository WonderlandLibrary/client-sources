package net;

import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.client.renderer.EnumFaceDirection$VertexInformation;
import net.minecraft.util.EnumFacing;

public class uE {
   public static EnumFaceDirection a(EnumFacing var0) {
      return EnumFaceDirection.getFacing(var0);
   }

   public static EnumFaceDirection$VertexInformation a(EnumFaceDirection var0, int var1) {
      return var0.func_179025_a(var1);
   }
}
