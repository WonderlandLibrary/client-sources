package net;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;

public class aau {
   public static BlockPos c(S35PacketUpdateTileEntity var0) {
      return var0.getPos();
   }

   public static int a(S35PacketUpdateTileEntity var0) {
      return var0.getTileEntityType();
   }

   public static NBTTagCompound b(S35PacketUpdateTileEntity var0) {
      return var0.getNbtCompound();
   }
}
