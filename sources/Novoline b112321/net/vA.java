package net;

import cc.novoline.utils.music.WAVMusicPlayer;

public class vA {
   private static String[] b;

   public static void a(WAVMusicPlayer var0) {
      var0.playLooping();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
