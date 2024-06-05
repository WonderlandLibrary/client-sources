package net;

import java.util.concurrent.Callable;
import net.minecraft.client.multiplayer.WorldClient;

class azQ implements Callable {
   private static final String b = "CL_00000885";
   final WorldClient a;

   azQ(WorldClient var1) {
      this.a = var1;
   }

   public String a() {
      return WorldClient.access$200(this.a).player.getClientBrand();
   }
}
