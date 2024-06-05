package net.minecraft.world.biome;

public class BiomeGenBase$Height {
   public float rootHeight;
   public float variation;

   public BiomeGenBase$Height(float var1, float var2) {
      this.rootHeight = var1;
      this.variation = var2;
   }

   public BiomeGenBase$Height attenuate() {
      return new BiomeGenBase$Height(this.rootHeight * 0.8F, this.variation * 0.6F);
   }
}
