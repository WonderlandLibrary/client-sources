package net;

import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemLogger;

public class q_ {
   public static void a(Class var0) {
      SoundSystemConfig.addLibrary(var0);
   }

   public static void a(String var0, Class var1) {
      SoundSystemConfig.setCodec(var0, var1);
   }

   public static void a(SoundSystemLogger var0) {
      SoundSystemConfig.setLogger(var0);
   }
}
