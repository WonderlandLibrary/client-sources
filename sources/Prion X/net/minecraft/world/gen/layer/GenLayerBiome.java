package net.minecraft.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public class GenLayerBiome extends GenLayer
{
  private BiomeGenBase[] field_151623_c;
  private BiomeGenBase[] field_151621_d;
  private BiomeGenBase[] field_151622_e;
  private BiomeGenBase[] field_151620_f;
  private final ChunkProviderSettings field_175973_g;
  private static final String __OBFID = "CL_00000555";
  
  public GenLayerBiome(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, String p_i45560_5_)
  {
    super(p_i45560_1_);
    field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains };
    field_151621_d = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland };
    field_151622_e = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains };
    field_151620_f = new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga };
    parent = p_i45560_3_;
    
    if (p_i45560_4_ == WorldType.DEFAULT_1_1)
    {
      field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
      field_175973_g = null;
    }
    else if (p_i45560_4_ == WorldType.CUSTOMIZED)
    {
      field_175973_g = net.minecraft.world.gen.ChunkProviderSettings.Factory.func_177865_a(p_i45560_5_).func_177864_b();
    }
    else
    {
      field_175973_g = null;
    }
  }
  




  public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
  {
    int[] var5 = parent.getInts(areaX, areaY, areaWidth, areaHeight);
    int[] var6 = IntCache.getIntCache(areaWidth * areaHeight);
    
    for (int var7 = 0; var7 < areaHeight; var7++)
    {
      for (int var8 = 0; var8 < areaWidth; var8++)
      {
        initChunkSeed(var8 + areaX, var7 + areaY);
        int var9 = var5[(var8 + var7 * areaWidth)];
        int var10 = (var9 & 0xF00) >> 8;
        var9 &= 0xF0FF;
        
        if ((field_175973_g != null) && (field_175973_g.field_177779_F >= 0))
        {
          var6[(var8 + var7 * areaWidth)] = field_175973_g.field_177779_F;
        }
        else if (isBiomeOceanic(var9))
        {
          var6[(var8 + var7 * areaWidth)] = var9;
        }
        else if (var9 == mushroomIslandbiomeID)
        {
          var6[(var8 + var7 * areaWidth)] = var9;
        }
        else if (var9 == 1)
        {
          if (var10 > 0)
          {
            if (nextInt(3) == 0)
            {
              var6[(var8 + var7 * areaWidth)] = mesaPlateaubiomeID;
            }
            else
            {
              var6[(var8 + var7 * areaWidth)] = mesaPlateau_FbiomeID;
            }
            
          }
          else {
            var6[(var8 + var7 * areaWidth)] = field_151623_c[nextInt(field_151623_c.length)].biomeID;
          }
        }
        else if (var9 == 2)
        {
          if (var10 > 0)
          {
            var6[(var8 + var7 * areaWidth)] = junglebiomeID;
          }
          else
          {
            var6[(var8 + var7 * areaWidth)] = field_151621_d[nextInt(field_151621_d.length)].biomeID;
          }
        }
        else if (var9 == 3)
        {
          if (var10 > 0)
          {
            var6[(var8 + var7 * areaWidth)] = megaTaigabiomeID;
          }
          else
          {
            var6[(var8 + var7 * areaWidth)] = field_151622_e[nextInt(field_151622_e.length)].biomeID;
          }
        }
        else if (var9 == 4)
        {
          var6[(var8 + var7 * areaWidth)] = field_151620_f[nextInt(field_151620_f.length)].biomeID;
        }
        else
        {
          var6[(var8 + var7 * areaWidth)] = mushroomIslandbiomeID;
        }
      }
    }
    
    return var6;
  }
}
