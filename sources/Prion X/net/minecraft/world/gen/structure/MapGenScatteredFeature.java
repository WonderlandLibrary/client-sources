package net.minecraft.world.gen.structure;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MapGenScatteredFeature extends MapGenStructure
{
  private static final List biomelist = java.util.Arrays.asList(new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland });
  

  private List scatteredFeatureSpawnList;
  
  private int maxDistanceBetweenScatteredFeatures;
  
  private int minDistanceBetweenScatteredFeatures;
  
  private static final String __OBFID = "CL_00000471";
  

  public MapGenScatteredFeature()
  {
    scatteredFeatureSpawnList = com.google.common.collect.Lists.newArrayList();
    maxDistanceBetweenScatteredFeatures = 32;
    minDistanceBetweenScatteredFeatures = 8;
    scatteredFeatureSpawnList.add(new net.minecraft.world.biome.BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
  }
  
  public MapGenScatteredFeature(Map p_i2061_1_)
  {
    this();
    Iterator var2 = p_i2061_1_.entrySet().iterator();
    
    while (var2.hasNext())
    {
      Map.Entry var3 = (Map.Entry)var2.next();
      
      if (((String)var3.getKey()).equals("distance"))
      {
        maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), maxDistanceBetweenScatteredFeatures, minDistanceBetweenScatteredFeatures + 1);
      }
    }
  }
  
  public String getStructureName()
  {
    return "Temple";
  }
  
  protected boolean canSpawnStructureAtCoords(int p_75047_1_, int p_75047_2_)
  {
    int var3 = p_75047_1_;
    int var4 = p_75047_2_;
    
    if (p_75047_1_ < 0)
    {
      p_75047_1_ -= maxDistanceBetweenScatteredFeatures - 1;
    }
    
    if (p_75047_2_ < 0)
    {
      p_75047_2_ -= maxDistanceBetweenScatteredFeatures - 1;
    }
    
    int var5 = p_75047_1_ / maxDistanceBetweenScatteredFeatures;
    int var6 = p_75047_2_ / maxDistanceBetweenScatteredFeatures;
    Random var7 = worldObj.setRandomSeed(var5, var6, 14357617);
    var5 *= maxDistanceBetweenScatteredFeatures;
    var6 *= maxDistanceBetweenScatteredFeatures;
    var5 += var7.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);
    var6 += var7.nextInt(maxDistanceBetweenScatteredFeatures - minDistanceBetweenScatteredFeatures);
    
    if ((var3 == var5) && (var4 == var6))
    {
      BiomeGenBase var8 = worldObj.getWorldChunkManager().func_180631_a(new BlockPos(var3 * 16 + 8, 0, var4 * 16 + 8));
      
      if (var8 == null)
      {
        return false;
      }
      
      Iterator var9 = biomelist.iterator();
      
      while (var9.hasNext())
      {
        BiomeGenBase var10 = (BiomeGenBase)var9.next();
        
        if (var8 == var10)
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  protected StructureStart getStructureStart(int p_75049_1_, int p_75049_2_)
  {
    return new Start(worldObj, rand, p_75049_1_, p_75049_2_);
  }
  
  public boolean func_175798_a(BlockPos p_175798_1_)
  {
    StructureStart var2 = func_175797_c(p_175798_1_);
    
    if ((var2 != null) && ((var2 instanceof Start)) && (!components.isEmpty()))
    {
      StructureComponent var3 = (StructureComponent)components.getFirst();
      return var3 instanceof ComponentScatteredFeaturePieces.SwampHut;
    }
    

    return false;
  }
  




  public List getScatteredFeatureSpawnList()
  {
    return scatteredFeatureSpawnList;
  }
  
  public static class Start extends StructureStart
  {
    private static final String __OBFID = "CL_00000472";
    
    public Start() {}
    
    public Start(World worldIn, Random p_i2060_2_, int p_i2060_3_, int p_i2060_4_)
    {
      super(p_i2060_4_);
      BiomeGenBase var5 = worldIn.getBiomeGenForCoords(new BlockPos(p_i2060_3_ * 16 + 8, 0, p_i2060_4_ * 16 + 8));
      
      if ((var5 != BiomeGenBase.jungle) && (var5 != BiomeGenBase.jungleHills))
      {
        if (var5 == BiomeGenBase.swampland)
        {
          ComponentScatteredFeaturePieces.SwampHut var7 = new ComponentScatteredFeaturePieces.SwampHut(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
          components.add(var7);
        }
        else if ((var5 == BiomeGenBase.desert) || (var5 == BiomeGenBase.desertHills))
        {
          ComponentScatteredFeaturePieces.DesertPyramid var8 = new ComponentScatteredFeaturePieces.DesertPyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
          components.add(var8);
        }
      }
      else
      {
        ComponentScatteredFeaturePieces.JunglePyramid var6 = new ComponentScatteredFeaturePieces.JunglePyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
        components.add(var6);
      }
      
      updateBoundingBox();
    }
  }
}
