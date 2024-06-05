package net;

import java.util.List;
import net.ayk;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleHandler;
import viaversion.viaversion.api.PacketWrapper;

public class bgs {
   public static boolean a(ParticleMapping$ParticleHandler var0) {
      return var0.isBlockHandler();
   }

   public static int[] a(ParticleMapping$ParticleHandler var0, ayk var1, PacketWrapper var2) {
      return var0.a(var1, var2);
   }

   public static int[] a(ParticleMapping$ParticleHandler var0, ayk var1, List var2) {
      return var0.a(var1, var2);
   }
}
