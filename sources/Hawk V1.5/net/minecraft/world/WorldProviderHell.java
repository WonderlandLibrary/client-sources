package net.minecraft.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

public class WorldProviderHell extends WorldProvider {
   private static final String __OBFID = "CL_00000387";

   public IChunkProvider createChunkGenerator() {
      return new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
   }

   public boolean doesXZShowFog(int var1, int var2) {
      return true;
   }

   public void registerWorldChunkManager() {
      this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F);
      this.isHellWorld = true;
      this.hasNoSky = true;
      this.dimensionId = -1;
   }

   public boolean isSurfaceWorld() {
      return false;
   }

   public boolean canCoordinateBeSpawn(int var1, int var2) {
      return false;
   }

   public boolean canRespawnHere() {
      return false;
   }

   public Vec3 getFogColor(float var1, float var2) {
      return new Vec3(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
   }

   public WorldBorder getWorldBorder() {
      return new WorldBorder(this) {
         final WorldProviderHell this$0;
         private static final String __OBFID = "CL_00002008";

         public double getCenterZ() {
            return super.getCenterZ() / 8.0D;
         }

         {
            this.this$0 = var1;
         }

         public double getCenterX() {
            return super.getCenterX() / 8.0D;
         }
      };
   }

   public String getDimensionName() {
      return "Nether";
   }

   public float calculateCelestialAngle(long var1, float var3) {
      return 0.5F;
   }

   protected void generateLightBrightnessTable() {
      float var1 = 0.1F;

      for(int var2 = 0; var2 <= 15; ++var2) {
         float var3 = 1.0F - (float)var2 / 15.0F;
         this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }

   }

   public String getInternalNameSuffix() {
      return "_nether";
   }
}
