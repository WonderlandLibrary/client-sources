package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomMobsRule
{
  private ResourceLocation baseResLoc = null;
  private int[] skins = null;
  private ResourceLocation[] resourceLocations = null;
  private int[] weights = null;
  private BiomeGenBase[] biomes = null;
  private RangeListInt heights = null;
  public int[] sumWeights = null;
  public int sumAllWeights = 1;
  
  public RandomMobsRule(ResourceLocation baseResLoc, int[] skins, int[] weights, BiomeGenBase[] biomes, RangeListInt heights)
  {
    this.baseResLoc = baseResLoc;
    this.skins = skins;
    this.weights = weights;
    this.biomes = biomes;
    this.heights = heights;
  }
  
  public boolean isValid(String path)
  {
    resourceLocations = new ResourceLocation[skins.length];
    ResourceLocation locMcp = RandomMobs.getMcpatcherLocation(baseResLoc);
    
    if (locMcp == null)
    {
      Config.warn("Invalid path: " + baseResLoc.getResourcePath());
      return false;
    }
    




    for (int sum = 0; sum < resourceLocations.length; sum++)
    {
      int i = skins[sum];
      
      if (i <= 1)
      {
        resourceLocations[sum] = baseResLoc;
      }
      else
      {
        ResourceLocation i1 = RandomMobs.getLocationIndexed(locMcp, i);
        
        if (i1 == null)
        {
          Config.warn("Invalid path: " + baseResLoc.getResourcePath());
          return false;
        }
        
        if (!Config.hasResource(i1))
        {
          Config.warn("Texture not found: " + i1.getResourcePath());
          return false;
        }
        
        resourceLocations[sum] = i1;
      }
    }
    
    if (weights != null)
    {


      if (weights.length > resourceLocations.length)
      {
        Config.warn("More weights defined than skins, trimming weights: " + path);
        int[] var6 = new int[resourceLocations.length];
        System.arraycopy(weights, 0, var6, 0, var6.length);
        weights = var6;
      }
      
      if (weights.length < resourceLocations.length)
      {
        Config.warn("Less weights defined than skins, expanding weights: " + path);
        int[] var6 = new int[resourceLocations.length];
        System.arraycopy(weights, 0, var6, 0, weights.length);
        int i = MathUtils.getAverage(weights);
        
        for (int var7 = weights.length; var7 < var6.length; var7++)
        {
          var6[var7] = i;
        }
        
        weights = var6;
      }
      
      sumWeights = new int[weights.length];
      sum = 0;
      
      for (int i = 0; i < weights.length; i++)
      {
        if (weights[i] < 0)
        {
          Config.warn("Invalid weight: " + weights[i]);
          return false;
        }
        
        sum += weights[i];
        sumWeights[i] = sum;
      }
      
      sumAllWeights = sum;
      
      if (sumAllWeights <= 0)
      {
        Config.warn("Invalid sum of all weights: " + sum);
        sumAllWeights = 1;
      }
    }
    
    return true;
  }
  

  public boolean matches(EntityLiving el)
  {
    return Matches.biome(spawnBiome, biomes);
  }
  
  public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId)
  {
    int index = 0;
    
    if (weights == null)
    {
      index = randomId % resourceLocations.length;
    }
    else
    {
      int randWeight = randomId % sumAllWeights;
      
      for (int i = 0; i < sumWeights.length; i++)
      {
        if (sumWeights[i] > randWeight)
        {
          index = i;
          break;
        }
      }
    }
    
    return resourceLocations[index];
  }
}
