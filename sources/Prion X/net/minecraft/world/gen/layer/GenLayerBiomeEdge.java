package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;

public class GenLayerBiomeEdge extends GenLayer
{
  private static final String __OBFID = "CL_00000554";
  
  public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_)
  {
    super(p_i45475_1_);
    parent = p_i45475_3_;
  }
  




  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int[] var5 = parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
    int[] var6 = IntCache.getIntCache(areaWidth * areaHeight);
    
    for (int var7 = 0; var7 < areaHeight; var7++)
    {
      for (int var8 = 0; var8 < areaWidth; var8++)
      {
        initChunkSeed(var8 + areaX, var7 + areaY);
        int var9 = var5[(var8 + 1 + (var7 + 1) * (areaWidth + 2))];
        
        if ((!replaceBiomeEdgeIfNecessary(var5, var6, var8, var7, areaWidth, var9, extremeHillsbiomeID, extremeHillsEdgebiomeID)) && (!replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, mesaPlateau_FbiomeID, mesabiomeID)) && (!replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, mesaPlateaubiomeID, mesabiomeID)) && (!replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, megaTaigabiomeID, taigabiomeID)))
        {





          if (var9 == desertbiomeID)
          {
            int var10 = var5[(var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2))];
            int var11 = var5[(var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2))];
            int var12 = var5[(var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2))];
            int var13 = var5[(var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2))];
            
            if ((var10 != icePlainsbiomeID) && (var11 != icePlainsbiomeID) && (var12 != icePlainsbiomeID) && (var13 != icePlainsbiomeID))
            {
              var6[(var8 + var7 * areaWidth)] = var9;
            }
            else
            {
              var6[(var8 + var7 * areaWidth)] = extremeHillsPlusbiomeID;
            }
          }
          else if (var9 == swamplandbiomeID)
          {
            int var10 = var5[(var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2))];
            int var11 = var5[(var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2))];
            int var12 = var5[(var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2))];
            int var13 = var5[(var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2))];
            
            if ((var10 != desertbiomeID) && (var11 != desertbiomeID) && (var12 != desertbiomeID) && (var13 != desertbiomeID) && (var10 != coldTaigabiomeID) && (var11 != coldTaigabiomeID) && (var12 != coldTaigabiomeID) && (var13 != coldTaigabiomeID) && (var10 != icePlainsbiomeID) && (var11 != icePlainsbiomeID) && (var12 != icePlainsbiomeID) && (var13 != icePlainsbiomeID))
            {
              if ((var10 != junglebiomeID) && (var13 != junglebiomeID) && (var11 != junglebiomeID) && (var12 != junglebiomeID))
              {
                var6[(var8 + var7 * areaWidth)] = var9;
              }
              else
              {
                var6[(var8 + var7 * areaWidth)] = jungleEdgebiomeID;
              }
              
            }
            else {
              var6[(var8 + var7 * areaWidth)] = plainsbiomeID;
            }
          }
          else
          {
            var6[(var8 + var7 * areaWidth)] = var9;
          }
        }
      }
    }
    
    return var6;
  }
  



  private boolean replaceBiomeEdgeIfNecessary(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_)
  {
    if (!biomesEqualOrMesaPlateau(p_151636_6_, p_151636_7_))
    {
      return false;
    }
    

    int var9 = p_151636_1_[(p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2))];
    int var10 = p_151636_1_[(p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2))];
    int var11 = p_151636_1_[(p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2))];
    int var12 = p_151636_1_[(p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2))];
    
    if ((canBiomesBeNeighbors(var9, p_151636_7_)) && (canBiomesBeNeighbors(var10, p_151636_7_)) && (canBiomesBeNeighbors(var11, p_151636_7_)) && (canBiomesBeNeighbors(var12, p_151636_7_)))
    {
      p_151636_2_[(p_151636_3_ + p_151636_4_ * p_151636_5_)] = p_151636_6_;
    }
    else
    {
      p_151636_2_[(p_151636_3_ + p_151636_4_ * p_151636_5_)] = p_151636_8_;
    }
    
    return true;
  }
  




  private boolean replaceBiomeEdge(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_)
  {
    if (p_151635_6_ != p_151635_7_)
    {
      return false;
    }
    

    int var9 = p_151635_1_[(p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2))];
    int var10 = p_151635_1_[(p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2))];
    int var11 = p_151635_1_[(p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2))];
    int var12 = p_151635_1_[(p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2))];
    
    if ((biomesEqualOrMesaPlateau(var9, p_151635_7_)) && (biomesEqualOrMesaPlateau(var10, p_151635_7_)) && (biomesEqualOrMesaPlateau(var11, p_151635_7_)) && (biomesEqualOrMesaPlateau(var12, p_151635_7_)))
    {
      p_151635_2_[(p_151635_3_ + p_151635_4_ * p_151635_5_)] = p_151635_6_;
    }
    else
    {
      p_151635_2_[(p_151635_3_ + p_151635_4_ * p_151635_5_)] = p_151635_8_;
    }
    
    return true;
  }
  





  private boolean canBiomesBeNeighbors(int p_151634_1_, int p_151634_2_)
  {
    if (biomesEqualOrMesaPlateau(p_151634_1_, p_151634_2_))
    {
      return true;
    }
    

    BiomeGenBase var3 = BiomeGenBase.getBiome(p_151634_1_);
    BiomeGenBase var4 = BiomeGenBase.getBiome(p_151634_2_);
    
    if ((var3 != null) && (var4 != null))
    {
      net.minecraft.world.biome.BiomeGenBase.TempCategory var5 = var3.getTempCategory();
      net.minecraft.world.biome.BiomeGenBase.TempCategory var6 = var4.getTempCategory();
      return (var5 == var6) || (var5 == net.minecraft.world.biome.BiomeGenBase.TempCategory.MEDIUM) || (var6 == net.minecraft.world.biome.BiomeGenBase.TempCategory.MEDIUM);
    }
    

    return false;
  }
}
