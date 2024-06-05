package net;

import viaversion.viabackwards.protocol.protocol1_10to1_11.storage.ChestedHorseStorage;

public class Vb {
   public static boolean b(ChestedHorseStorage var0) {
      return var0.isChested();
   }

   public static int a(ChestedHorseStorage var0) {
      return var0.getLiamaStrength();
   }

   public static void a(ChestedHorseStorage var0, int var1) {
      var0.setLiamaStrength(var1);
   }

   public static void c(ChestedHorseStorage var0, int var1) {
      var0.setLiamaCarpetColor(var1);
   }

   public static void b(ChestedHorseStorage var0, int var1) {
      var0.setLiamaVariant(var1);
   }

   public static void a(ChestedHorseStorage var0, boolean var1) {
      var0.setChested(var1);
   }
}
