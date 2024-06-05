package net;

import java.util.List;
import net.acE;
import net.aci;
import net.ayk;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleHandler;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.type.Type;

final class Aq implements ParticleMapping$ParticleHandler {
   public int[] a(ayk var1, PacketWrapper var2) throws Exception {
      aci.b();
      float var4 = ((Float)var2.read(Type.FLOAT)).floatValue();
      float var5 = ((Float)var2.read(Type.FLOAT)).floatValue();
      float var6 = ((Float)var2.read(Type.FLOAT)).floatValue();
      float var7 = ((Float)var2.read(Type.FLOAT)).floatValue();
      var2.set(Type.FLOAT, 3, Float.valueOf(var4));
      var2.set(Type.FLOAT, 4, Float.valueOf(var5));
      var2.set(Type.FLOAT, 5, Float.valueOf(var6));
      var2.set(Type.FLOAT, 6, Float.valueOf(var7));
      var2.set(Type.INT, 1, Integer.valueOf(0));
      if(acE.b() == null) {
         aci.b(new String[3]);
      }

      return null;
   }

   public int[] a(ayk var1, List var2) {
      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
