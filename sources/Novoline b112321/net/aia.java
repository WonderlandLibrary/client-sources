package net;

import java.util.concurrent.Callable;
import net.minecraft.server.integrated.IntegratedServer;

class aia implements Callable {
   private static final String a = "CL_00001130";
   final IntegratedServer b;

   aia(IntegratedServer var1) {
      this.b = var1;
   }

   public String a() {
      return "Integrated Server (map_client.txt)";
   }
}
