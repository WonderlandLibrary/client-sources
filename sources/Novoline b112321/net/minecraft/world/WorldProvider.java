package net.minecraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;

public abstract class WorldProvider {
   public static final float[] moonPhaseFactors = new float[]{1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
   protected World worldObj;
   private WorldType terrainType;
   private String generatorSettings;
   protected WorldChunkManager worldChunkMgr;
   protected boolean isHellWorld;
   protected boolean hasNoSky;
   protected final float[] lightBrightnessTable = new float[16];
   protected int dimensionId;
   private final float[] colorsSunriseSunset = new float[4];

   public final void registerWorld(World var1) {
      this.worldObj = var1;
      this.terrainType = var1.getWorldInfo().getTerrainType();
      this.generatorSettings = var1.getWorldInfo().getGeneratorOptions();
      this.registerWorldChunkManager();
      this.generateLightBrightnessTable();
   }

   protected void generateLightBrightnessTable() {
      float var1 = 0.0F;

      for(int var2 = 0; var2 <= 15; ++var2) {
         float var3 = 1.0F - (float)var2 / 15.0F;
         this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
      }

   }

   protected void registerWorldChunkManager() {
      WorldType var1 = this.worldObj.getWorldInfo().getTerrainType();
      if(var1 == WorldType.FLAT) {
         FlatGeneratorInfo var2 = FlatGeneratorInfo.a(this.worldObj.getWorldInfo().getGeneratorOptions());
         this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.getBiomeFromBiomeList(var2.getBiome(), BiomeGenBase.field_180279_ad), 0.5F);
      } else if(var1 == WorldType.DEBUG_WORLD) {
         this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);
      } else {
         this.worldChunkMgr = new WorldChunkManager(this.worldObj);
      }

   }

   public IChunkProvider createChunkGenerator() {
      return (IChunkProvider)(this.terrainType == WorldType.FLAT?new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings):(this.terrainType == WorldType.DEBUG_WORLD?new ChunkProviderDebug(this.worldObj):(this.terrainType == WorldType.CUSTOMIZED?new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings):new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings))));
   }

   public boolean canCoordinateBeSpawn(int var1, int var2) {
      return this.worldObj.getGroundAboveSeaLevel(new BlockPos(var1, 0, var2)) == Blocks.grass;
   }

   public float calculateCelestialAngle(long var1, float var3) {
      int var4 = (int)(var1 % 24000L);
      float var5 = ((float)var4 + var3) / 24000.0F - 0.25F;
      if(var5 < 0.0F) {
         ++var5;
      }

      if(var5 > 1.0F) {
         --var5;
      }

      var5 = 1.0F - (float)(((double)MathHelper.cos((double)var5 * 3.141592653589793D) + 1.0D) / 2.0D);
      var5 = var5 + (var5 - var5) / 3.0F;
      return var5;
   }

   public int getMoonPhase(long var1) {
      return (int)(var1 / 24000L % 8L + 8L) % 8;
   }

   public boolean isSurfaceWorld() {
      return true;
   }

   public float[] calcSunriseSunsetColors(float var1, float var2) {
      float var3 = 0.4F;
      float var4 = MathHelper.cos(var1 * 3.1415927F * 2.0F) - 0.0F;
      float var5 = -0.0F;
      if(var4 >= var5 - var3 && var4 <= var5 + var3) {
         float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
         float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * 3.1415927F)) * 0.99F;
         var7 = var7 * var7;
         this.colorsSunriseSunset[0] = var6 * 0.3F + 0.7F;
         this.colorsSunriseSunset[1] = var6 * var6 * 0.7F + 0.2F;
         this.colorsSunriseSunset[2] = var6 * var6 * 0.0F + 0.2F;
         this.colorsSunriseSunset[3] = var7;
         return this.colorsSunriseSunset;
      } else {
         return null;
      }
   }

   public Vec3 getFogColor(float var1, float var2) {
      float var3 = MathHelper.cos(var1 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      float var4 = 0.7529412F;
      float var5 = 0.84705883F;
      float var6 = 1.0F;
      var4 = var4 * (var3 * 0.94F + 0.06F);
      var5 = var5 * (var3 * 0.94F + 0.06F);
      var6 = var6 * (var3 * 0.91F + 0.09F);
      return new Vec3((double)var4, (double)var5, (double)var6);
   }

   public boolean canRespawnHere() {
      return true;
   }

   public static WorldProvider getProviderForDimension(int var0) {
      return (WorldProvider)(var0 == -1?new WorldProviderHell():new WorldProviderSurface());
   }

   public float getCloudHeight() {
      return 128.0F;
   }

   public boolean isSkyColored() {
      return true;
   }

   public BlockPos getSpawnCoordinate() {
      return null;
   }

   public int getAverageGroundLevel() {
      return this.terrainType == WorldType.FLAT?4:this.worldObj.func_181545_F() + 1;
   }

   public double getVoidFogYFactor() {
      return this.terrainType == WorldType.FLAT?1.0D:0.03125D;
   }

   public boolean doesXZShowFog(int var1, int var2) {
      return false;
   }

   public abstract String getDimensionName();

   public abstract String getInternalNameSuffix();

   public WorldChunkManager getWorldChunkManager() {
      return this.worldChunkMgr;
   }

   public boolean doesWaterVaporize() {
      return this.isHellWorld;
   }

   public boolean getHasNoSky() {
      return this.hasNoSky;
   }

   public float[] getLightBrightnessTable() {
      return this.lightBrightnessTable;
   }

   public int getDimensionId() {
      return this.dimensionId;
   }

   public WorldBorder getWorldBorder() {
      return new WorldBorder();
   }
}
