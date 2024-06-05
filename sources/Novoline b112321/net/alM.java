package net;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.world.World;

public class alM {
   public static Entity a(S43PacketCamera var0, World var1) {
      return var0.getEntity(var1);
   }
}
