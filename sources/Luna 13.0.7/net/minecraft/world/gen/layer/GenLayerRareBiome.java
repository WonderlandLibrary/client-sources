package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerRareBiome
  extends GenLayer
{
  private static final String __OBFID = "CL_00000562";
  
  public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_)
  {
    super(p_i45478_1_);
    this.parent = p_i45478_3_;
  }
  
  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int[] var5 = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
    int[] var6 = IntCache.getIntCache(areaWidth * areaHeight);
    for (int var7 = 0; var7 < areaHeight; var7++) {
      for (int var8 = 0; var8 < areaWidth; var8++)
      {
        initChunkSeed(var8 + areaX, var7 + areaY);
        int var9 = var5[(var8 + 1 + (var7 + 1) * (areaWidth + 2))];
        if (nextInt(57) == 0)
        {
          if (var9 == BiomeGenBase.plains.biomeID) {
            var6[(var8 + var7 * areaWidth)] = (BiomeGenBase.plains.biomeID + 128);
          } else {
            var6[(var8 + var7 * areaWidth)] = var9;
          }
        }
        else {
          var6[(var8 + var7 * areaWidth)] = var9;
        }
      }
    }
    return var6;
  }
}
