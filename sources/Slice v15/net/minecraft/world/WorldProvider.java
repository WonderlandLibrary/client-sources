package net.minecraft.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderDebug;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.storage.WorldInfo;

public abstract class WorldProvider
{
  public static final float[] moonPhaseFactors = { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
  

  protected World worldObj;
  

  private WorldType terrainType;
  

  private String generatorSettings;
  

  protected WorldChunkManager worldChunkMgr;
  

  protected boolean isHellWorld;
  

  protected boolean hasNoSky;
  

  protected final float[] lightBrightnessTable = new float[16];
  

  protected int dimensionId;
  

  private final float[] colorsSunriseSunset = new float[4];
  
  private static final String __OBFID = "CL_00000386";
  
  public WorldProvider() {}
  
  public final void registerWorld(World worldIn)
  {
    worldObj = worldIn;
    terrainType = worldIn.getWorldInfo().getTerrainType();
    generatorSettings = worldIn.getWorldInfo().getGeneratorOptions();
    registerWorldChunkManager();
    generateLightBrightnessTable();
  }
  



  protected void generateLightBrightnessTable()
  {
    float var1 = 0.0F;
    
    for (int var2 = 0; var2 <= 15; var2++)
    {
      float var3 = 1.0F - var2 / 15.0F;
      lightBrightnessTable[var2] = ((1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1);
    }
  }
  



  protected void registerWorldChunkManager()
  {
    WorldType var1 = worldObj.getWorldInfo().getTerrainType();
    
    if (var1 == WorldType.FLAT)
    {
      FlatGeneratorInfo var2 = FlatGeneratorInfo.createFlatGeneratorFromString(worldObj.getWorldInfo().getGeneratorOptions());
      worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.getBiomeFromBiomeList(var2.getBiome(), BiomeGenBase.field_180279_ad), 0.5F);
    }
    else if (var1 == WorldType.DEBUG_WORLD)
    {
      worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0F);
    }
    else
    {
      worldChunkMgr = new WorldChunkManager(worldObj);
    }
  }
  



  public IChunkProvider createChunkGenerator()
  {
    return terrainType == WorldType.CUSTOMIZED ? new ChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), generatorSettings) : terrainType == WorldType.DEBUG_WORLD ? new ChunkProviderDebug(worldObj) : terrainType == WorldType.FLAT ? new ChunkProviderFlat(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), generatorSettings) : new ChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), generatorSettings);
  }
  



  public boolean canCoordinateBeSpawn(int x, int z)
  {
    return worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)) == net.minecraft.init.Blocks.grass;
  }
  



  public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
  {
    int var4 = (int)(p_76563_1_ % 24000L);
    float var5 = (var4 + p_76563_3_) / 24000.0F - 0.25F;
    
    if (var5 < 0.0F)
    {
      var5 += 1.0F;
    }
    
    if (var5 > 1.0F)
    {
      var5 -= 1.0F;
    }
    
    float var6 = var5;
    var5 = 1.0F - (float)((Math.cos(var5 * 3.141592653589793D) + 1.0D) / 2.0D);
    var5 = var6 + (var5 - var6) / 3.0F;
    return var5;
  }
  
  public int getMoonPhase(long p_76559_1_)
  {
    return (int)(p_76559_1_ / 24000L % 8L + 8L) % 8;
  }
  



  public boolean isSurfaceWorld()
  {
    return true;
  }
  



  public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_)
  {
    float var3 = 0.4F;
    float var4 = MathHelper.cos(p_76560_1_ * 3.1415927F * 2.0F) - 0.0F;
    float var5 = -0.0F;
    
    if ((var4 >= var5 - var3) && (var4 <= var5 + var3))
    {
      float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
      float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * 3.1415927F)) * 0.99F;
      var7 *= var7;
      colorsSunriseSunset[0] = (var6 * 0.3F + 0.7F);
      colorsSunriseSunset[1] = (var6 * var6 * 0.7F + 0.2F);
      colorsSunriseSunset[2] = (var6 * var6 * 0.0F + 0.2F);
      colorsSunriseSunset[3] = var7;
      return colorsSunriseSunset;
    }
    

    return null;
  }
  




  public Vec3 getFogColor(float p_76562_1_, float p_76562_2_)
  {
    float var3 = MathHelper.cos(p_76562_1_ * 3.1415927F * 2.0F) * 2.0F + 0.5F;
    var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
    float var4 = 0.7529412F;
    float var5 = 0.84705883F;
    float var6 = 1.0F;
    var4 *= (var3 * 0.94F + 0.06F);
    var5 *= (var3 * 0.94F + 0.06F);
    var6 *= (var3 * 0.91F + 0.09F);
    return new Vec3(var4, var5, var6);
  }
  



  public boolean canRespawnHere()
  {
    return true;
  }
  
  public static WorldProvider getProviderForDimension(int dimension)
  {
    return dimension == 1 ? new WorldProviderEnd() : dimension == 0 ? new WorldProviderSurface() : dimension == -1 ? new WorldProviderHell() : null;
  }
  



  public float getCloudHeight()
  {
    return 128.0F;
  }
  
  public boolean isSkyColored()
  {
    return true;
  }
  
  public BlockPos func_177496_h()
  {
    return null;
  }
  
  public int getAverageGroundLevel()
  {
    return terrainType == WorldType.FLAT ? 4 : 64;
  }
  





  public double getVoidFogYFactor()
  {
    return terrainType == WorldType.FLAT ? 1.0D : 0.03125D;
  }
  



  public boolean doesXZShowFog(int p_76568_1_, int p_76568_2_)
  {
    return false;
  }
  

  public abstract String getDimensionName();
  

  public abstract String getInternalNameSuffix();
  

  public WorldChunkManager getWorldChunkManager()
  {
    return worldChunkMgr;
  }
  
  public boolean func_177500_n()
  {
    return isHellWorld;
  }
  
  public boolean getHasNoSky()
  {
    return hasNoSky;
  }
  
  public float[] getLightBrightnessTable()
  {
    return lightBrightnessTable;
  }
  
  public int getDimensionId()
  {
    return dimensionId;
  }
  
  public WorldBorder getWorldBorder()
  {
    return new WorldBorder();
  }
}
