package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SoundEventAccessorComposite implements ISoundEventAccessor {
   private final ResourceLocation soundLocation;
   private final Random rnd = new Random();
   private double eventVolume;
   private static final String __OBFID = "CL_00001146";
   private double eventPitch;
   private final SoundCategory category;
   private final List soundPool = Lists.newArrayList();

   public Object cloneEntry() {
      return this.cloneEntry1();
   }

   public SoundEventAccessorComposite(ResourceLocation var1, double var2, double var4, SoundCategory var6) {
      this.soundLocation = var1;
      this.eventVolume = var4;
      this.eventPitch = var2;
      this.category = var6;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }

   public ResourceLocation getSoundEventLocation() {
      return this.soundLocation;
   }

   public void addSoundToEventPool(ISoundEventAccessor var1) {
      this.soundPool.add(var1);
   }

   public int getWeight() {
      int var1 = 0;

      ISoundEventAccessor var2;
      for(Iterator var3 = this.soundPool.iterator(); var3.hasNext(); var1 += var2.getWeight()) {
         var2 = (ISoundEventAccessor)var3.next();
      }

      return var1;
   }

   public SoundPoolEntry cloneEntry1() {
      int var1 = this.getWeight();
      if (!this.soundPool.isEmpty() && var1 != 0) {
         int var2 = this.rnd.nextInt(var1);
         Iterator var3 = this.soundPool.iterator();

         while(var3.hasNext()) {
            ISoundEventAccessor var4 = (ISoundEventAccessor)var3.next();
            var2 -= var4.getWeight();
            if (var2 < 0) {
               SoundPoolEntry var5 = (SoundPoolEntry)var4.cloneEntry();
               var5.setPitch(var5.getPitch() * this.eventPitch);
               var5.setVolume(var5.getVolume() * this.eventVolume);
               return var5;
            }
         }

         return SoundHandler.missing_sound;
      } else {
         return SoundHandler.missing_sound;
      }
   }
}
