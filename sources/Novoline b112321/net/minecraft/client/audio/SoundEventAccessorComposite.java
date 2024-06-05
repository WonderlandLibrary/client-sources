package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

public class SoundEventAccessorComposite implements ISoundEventAccessor {
   private final List soundPool = Lists.newArrayList();
   private final Random rnd = new Random();
   private final ResourceLocation soundLocation;
   private final SoundCategory category;
   private double eventPitch;
   private double eventVolume;

   public SoundEventAccessorComposite(ResourceLocation var1, double var2, double var4, SoundCategory var6) {
      this.soundLocation = var1;
      this.eventVolume = var4;
      this.eventPitch = var2;
      this.category = var6;
   }

   public int getWeight() {
      int var1 = 0;

      for(ISoundEventAccessor var3 : this.soundPool) {
         var1 += var3.getWeight();
      }

      return var1;
   }

   public SoundPoolEntry cloneEntry() {
      int var1 = this.getWeight();
      if(!this.soundPool.isEmpty()) {
         int var2 = this.rnd.nextInt(var1);
         Iterator var3 = this.soundPool.iterator();
         if(var3.hasNext()) {
            ISoundEventAccessor var4 = (ISoundEventAccessor)var3.next();
            var2 = var2 - var4.getWeight();
            SoundPoolEntry var5 = (SoundPoolEntry)var4.cloneEntry();
            var5.setPitch(var5.getPitch() * this.eventPitch);
            var5.setVolume(var5.getVolume() * this.eventVolume);
            return var5;
         }
      }

      return SoundHandler.missing_sound;
   }

   public void addSoundToEventPool(ISoundEventAccessor var1) {
      this.soundPool.add(var1);
   }

   public ResourceLocation getSoundEventLocation() {
      return this.soundLocation;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }
}
