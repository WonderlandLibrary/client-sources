package net;

import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class j {
   public static BlockPos a(C12PacketUpdateSign var0) {
      return var0.getPosition();
   }

   public static IChatComponent[] b(C12PacketUpdateSign var0) {
      return var0.getLines();
   }
}
