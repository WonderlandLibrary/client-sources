package net;

import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.SoundPackets1_12;

public class a_o {
   public static int b(SoundPackets1_12 var0, int var1) {
      return var0.handleSounds(var1);
   }

   public static boolean a(SoundPackets1_12 var0, int var1) {
      return var0.hasPitch(var1);
   }

   public static float c(SoundPackets1_12 var0, int var1) {
      return var0.handlePitch(var1);
   }

   public static void a(SoundPackets1_12 var0) {
      var0.f();
   }
}
