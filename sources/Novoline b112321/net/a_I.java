package net;

import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.util.BlockPos;

public class a_I {
   public static boolean a(S28PacketEffect var0) {
      return var0.isSoundServerwide();
   }

   public static int c(S28PacketEffect var0) {
      return var0.getSoundType();
   }

   public static BlockPos d(S28PacketEffect var0) {
      return var0.getSoundPos();
   }

   public static int b(S28PacketEffect var0) {
      return var0.getSoundData();
   }
}
