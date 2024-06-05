package net;

import viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;

public class ah2 {
   private static int[] b;

   public static String a(int var0) {
      return SoundRemapper.oldNameFromId(var0);
   }

   public static String a(String var0) {
      return SoundRemapper.getOldName(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
