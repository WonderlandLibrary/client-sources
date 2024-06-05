package net.minecraft.client.audio;

import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundPoolEntry;

public class SoundEventAccessor implements ISoundEventAccessor {
   private final SoundPoolEntry entry;
   private final int weight;

   SoundEventAccessor(SoundPoolEntry var1, int var2) {
      this.entry = var1;
      this.weight = var2;
   }

   public int getWeight() {
      return this.weight;
   }

   public SoundPoolEntry cloneEntry() {
      return new SoundPoolEntry(this.entry);
   }
}
