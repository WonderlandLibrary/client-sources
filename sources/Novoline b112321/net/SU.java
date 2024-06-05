package net;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SU {
   private static String b;

   public static void a(SoundHandler var0, ISound var1) {
      var0.playSound(var1);
   }

   public static void e(SoundHandler var0) {
      var0.resumeSounds();
   }

   public static void a(SoundHandler var0) {
      var0.unloadSounds();
   }

   public static void a(SoundHandler var0, EntityPlayer var1, float var2) {
      var0.setListener(var1, var2);
   }

   public static void b(SoundHandler var0) {
      var0.pauseSounds();
   }

   public static void d(SoundHandler var0) {
      var0.update();
   }

   public static void c(SoundHandler var0) {
      var0.stopSounds();
   }

   public static void a(SoundHandler var0, ISound var1, int var2) {
      var0.playDelayedSound(var1, var2);
   }

   public static SoundEventAccessorComposite a(SoundHandler var0, ResourceLocation var1) {
      return var0.getSound(var1);
   }

   public static void b(SoundHandler var0, ISound var1) {
      var0.stopSound(var1);
   }

   public static boolean c(SoundHandler var0, ISound var1) {
      return var0.isSoundPlaying(var1);
   }

   public static SoundEventAccessorComposite a(SoundHandler var0, SoundCategory[] var1) {
      return var0.getRandomSoundFromCategories(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("TmYgTc");
      }

   }
}
