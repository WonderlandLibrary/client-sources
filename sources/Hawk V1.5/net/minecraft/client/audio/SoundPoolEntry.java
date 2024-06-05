package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class SoundPoolEntry {
   private double field_148655_c;
   private static final String __OBFID = "CL_00001140";
   private final boolean field_148654_b;
   private double field_148653_d;
   private final ResourceLocation field_148656_a;

   public void setVolume(double var1) {
      this.field_148653_d = var1;
   }

   public double getPitch() {
      return this.field_148655_c;
   }

   public SoundPoolEntry(ResourceLocation var1, double var2, double var4, boolean var6) {
      this.field_148656_a = var1;
      this.field_148655_c = var2;
      this.field_148653_d = var4;
      this.field_148654_b = var6;
   }

   public boolean isStreamingSound() {
      return this.field_148654_b;
   }

   public void setPitch(double var1) {
      this.field_148655_c = var1;
   }

   public double getVolume() {
      return this.field_148653_d;
   }

   public ResourceLocation getSoundPoolEntryLocation() {
      return this.field_148656_a;
   }

   public SoundPoolEntry(SoundPoolEntry var1) {
      this.field_148656_a = var1.field_148656_a;
      this.field_148655_c = var1.field_148655_c;
      this.field_148653_d = var1.field_148653_d;
      this.field_148654_b = var1.field_148654_b;
   }
}
