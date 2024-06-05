package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor {
   private static final String __OBFID = "CL_00001153";
   private final SoundPoolEntry entry;
   private final int weight;

   public SoundPoolEntry cloneEntry() {
      return new SoundPoolEntry(this.entry);
   }

   public Object cloneEntry() {
      return this.cloneEntry();
   }

   SoundEventAccessor(SoundPoolEntry var1, int var2) {
      this.entry = var1;
      this.weight = var2;
   }

   public int getWeight() {
      return this.weight;
   }

   public Object cloneEntry1() {
      return this.cloneEntry();
   }
}
