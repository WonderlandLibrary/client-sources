package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerHills extends GenLayer
{
  private static final Logger logger = ;
  private GenLayer field_151628_d;
  private static final String __OBFID = "CL_00000563";
  
  public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_)
  {
    super(p_i45479_1_);
    parent = p_i45479_3_;
    field_151628_d = p_i45479_4_;
  }
  




  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int[] var5 = parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
    int[] var6 = field_151628_d.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
    int[] var7 = IntCache.getIntCache(areaWidth * areaHeight);
    
    for (int var8 = 0; var8 < areaHeight; var8++)
    {
      for (int var9 = 0; var9 < areaWidth; var9++)
      {
        initChunkSeed(var9 + areaX, var8 + areaY);
        int var10 = var5[(var9 + 1 + (var8 + 1) * (areaWidth + 2))];
        int var11 = var6[(var9 + 1 + (var8 + 1) * (areaWidth + 2))];
        boolean var12 = (var11 - 2) % 29 == 0;
        
        if (var10 > 255)
        {
          logger.debug("old! " + var10);
        }
        
        if ((var10 != 0) && (var11 >= 2) && ((var11 - 2) % 29 == 1) && (var10 < 128))
        {
          if (BiomeGenBase.getBiome(var10 + 128) != null)
          {
            var7[(var9 + var8 * areaWidth)] = (var10 + 128);
          }
          else
          {
            var7[(var9 + var8 * areaWidth)] = var10;
          }
        }
        else if ((nextInt(3) != 0) && (!var12))
        {
          var7[(var9 + var8 * areaWidth)] = var10;
        }
        else
        {
          int var13 = var10;
          

          if (var10 == desertbiomeID)
          {
            var13 = desertHillsbiomeID;
          }
          else if (var10 == forestbiomeID)
          {
            var13 = forestHillsbiomeID;
          }
          else if (var10 == birchForestbiomeID)
          {
            var13 = birchForestHillsbiomeID;
          }
          else if (var10 == roofedForestbiomeID)
          {
            var13 = plainsbiomeID;
          }
          else if (var10 == taigabiomeID)
          {
            var13 = taigaHillsbiomeID;
          }
          else if (var10 == megaTaigabiomeID)
          {
            var13 = megaTaigaHillsbiomeID;
          }
          else if (var10 == coldTaigabiomeID)
          {
            var13 = coldTaigaHillsbiomeID;
          }
          else if (var10 == plainsbiomeID)
          {
            if (nextInt(3) == 0)
            {
              var13 = forestHillsbiomeID;
            }
            else
            {
              var13 = forestbiomeID;
            }
          }
          else if (var10 == icePlainsbiomeID)
          {
            var13 = iceMountainsbiomeID;
          }
          else if (var10 == junglebiomeID)
          {
            var13 = jungleHillsbiomeID;
          }
          else if (var10 == oceanbiomeID)
          {
            var13 = deepOceanbiomeID;
          }
          else if (var10 == extremeHillsbiomeID)
          {
            var13 = extremeHillsPlusbiomeID;
          }
          else if (var10 == savannabiomeID)
          {
            var13 = savannaPlateaubiomeID;
          }
          else if (biomesEqualOrMesaPlateau(var10, mesaPlateau_FbiomeID))
          {
            var13 = mesabiomeID;
          }
          else if ((var10 == deepOceanbiomeID) && (nextInt(3) == 0))
          {
            int var14 = nextInt(2);
            
            if (var14 == 0)
            {
              var13 = plainsbiomeID;
            }
            else
            {
              var13 = forestbiomeID;
            }
          }
          
          if ((var12) && (var13 != var10))
          {
            if (BiomeGenBase.getBiome(var13 + 128) != null)
            {
              var13 += 128;
            }
            else
            {
              var13 = var10;
            }
          }
          
          if (var13 == var10)
          {
            var7[(var9 + var8 * areaWidth)] = var10;
          }
          else
          {
            int var14 = var5[(var9 + 1 + (var8 + 1 - 1) * (areaWidth + 2))];
            int var15 = var5[(var9 + 1 + 1 + (var8 + 1) * (areaWidth + 2))];
            int var16 = var5[(var9 + 1 - 1 + (var8 + 1) * (areaWidth + 2))];
            int var17 = var5[(var9 + 1 + (var8 + 1 + 1) * (areaWidth + 2))];
            int var18 = 0;
            
            if (biomesEqualOrMesaPlateau(var14, var10))
            {
              var18++;
            }
            
            if (biomesEqualOrMesaPlateau(var15, var10))
            {
              var18++;
            }
            
            if (biomesEqualOrMesaPlateau(var16, var10))
            {
              var18++;
            }
            
            if (biomesEqualOrMesaPlateau(var17, var10))
            {
              var18++;
            }
            
            if (var18 >= 3)
            {
              var7[(var9 + var8 * areaWidth)] = var13;
            }
            else
            {
              var7[(var9 + var8 * areaWidth)] = var10;
            }
          }
        }
      }
    }
    
    return var7;
  }
}
