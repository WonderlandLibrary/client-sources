package net;

import net.Aq;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$1;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$3;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleData;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleHandler;

public class aci {
   private static final ParticleMapping$ParticleData[] c;
   private static String[] b;

   public static ParticleMapping$ParticleData a(int var0) {
      return c[var0];
   }

   private static ParticleMapping$ParticleData b(int var0) {
      return new ParticleMapping$ParticleData(var0, (ParticleMapping$1)null);
   }

   private static ParticleMapping$ParticleData a(int var0, ParticleMapping$ParticleHandler var1) {
      return new ParticleMapping$ParticleData(var0, var1, (ParticleMapping$1)null);
   }

   static {
      ParticleMapping$1 var0 = new ParticleMapping$1();
      b((String[])null);
      c = new ParticleMapping$ParticleData[]{b(16), b(20), b(35), a(37, var0), b(4), b(29), b(9), b(44), b(42), b(19), b(18), a(30, new Aq()), b(13), b(41), b(10), b(25), b(43), b(15), b(2), b(1), a(46, var0), b(3), b(6), b(26), b(21), b(34), b(14), a(36, new ParticleMapping$3()), b(33), b(31), b(12), b(27), b(22), b(23), b(0), b(24), b(39), b(11), b(48), b(12), b(45), b(47), b(7), b(5), b(17), b(4), b(4), b(4), b(18), b(18)};
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }
}
