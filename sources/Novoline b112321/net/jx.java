package net;

import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

public class jx {
   public static void a(SoundEventAccessorComposite var0, ISoundEventAccessor var1) {
      var0.addSoundToEventPool(var1);
   }

   public static SoundCategory a(SoundEventAccessorComposite var0) {
      return var0.getSoundCategory();
   }

   public static SoundPoolEntry c(SoundEventAccessorComposite var0) {
      return var0.cloneEntry();
   }

   public static ResourceLocation b(SoundEventAccessorComposite var0) {
      return var0.getSoundEventLocation();
   }
}
