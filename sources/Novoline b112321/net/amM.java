package net;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class amM {
   public static int c(C08PacketPlayerBlockPlacement var0) {
      return var0.getPlacedBlockDirection();
   }

   public static ItemStack b(C08PacketPlayerBlockPlacement var0) {
      return var0.getStack();
   }

   public static BlockPos d(C08PacketPlayerBlockPlacement var0) {
      return var0.getPosition();
   }

   public static float f(C08PacketPlayerBlockPlacement var0) {
      return var0.getPlacedBlockOffsetX();
   }

   public static float a(C08PacketPlayerBlockPlacement var0) {
      return var0.getPlacedBlockOffsetY();
   }

   public static float e(C08PacketPlayerBlockPlacement var0) {
      return var0.getPlacedBlockOffsetZ();
   }
}
