package net;

import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.world.storage.MapData;

public class j0 {
   public static int a(S34PacketMaps var0) {
      return var0.getMapId();
   }

   public static void a(S34PacketMaps var0, MapData var1) {
      var0.setMapdataTo(var1);
   }
}
