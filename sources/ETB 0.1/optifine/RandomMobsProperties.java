package optifine;

import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomMobsProperties
{
  public String name = null;
  public String basePath = null;
  public ResourceLocation[] resourceLocations = null;
  public RandomMobsRule[] rules = null;
  
  public RandomMobsProperties(String path, ResourceLocation[] variants)
  {
    ConnectedParser cp = new ConnectedParser("RandomMobs");
    name = cp.parseName(path);
    basePath = cp.parseBasePath(path);
    resourceLocations = variants;
  }
  
  public RandomMobsProperties(Properties props, String path, ResourceLocation baseResLoc)
  {
    ConnectedParser cp = new ConnectedParser("RandomMobs");
    name = cp.parseName(path);
    basePath = cp.parseBasePath(path);
    rules = parseRules(props, baseResLoc, cp);
  }
  


  public ResourceLocation getTextureLocation(ResourceLocation loc, EntityLiving el)
  {
    if (rules != null)
    {
      for (int randomId = 0; randomId < rules.length; randomId++)
      {
        RandomMobsRule index = rules[randomId];
        
        if (index.matches(el))
        {
          return index.getTextureLocation(loc, randomMobsId);
        }
      }
    }
    
    if (resourceLocations != null)
    {
      int randomId = randomMobsId;
      int var5 = randomId % resourceLocations.length;
      return resourceLocations[var5];
    }
    

    return loc;
  }
  

  private RandomMobsRule[] parseRules(Properties props, ResourceLocation baseResLoc, ConnectedParser cp)
  {
    ArrayList list = new ArrayList();
    int count = props.size();
    
    for (int rules = 0; rules < count; rules++)
    {
      int index = rules + 1;
      String valSkins = props.getProperty("skins." + index);
      
      if (valSkins != null)
      {
        int[] skins = cp.parseIntList(valSkins);
        int[] weights = cp.parseIntList(props.getProperty("weights." + index));
        BiomeGenBase[] biomes = cp.parseBiomes(props.getProperty("biomes." + index));
        RangeListInt heights = cp.parseRangeListInt(props.getProperty("heights." + index));
        
        if (heights == null)
        {
          heights = parseMinMaxHeight(props, index);
        }
        
        RandomMobsRule rule = new RandomMobsRule(baseResLoc, skins, weights, biomes, heights);
        list.add(rule);
      }
    }
    
    RandomMobsRule[] var14 = (RandomMobsRule[])list.toArray(new RandomMobsRule[list.size()]);
    return var14;
  }
  
  private RangeListInt parseMinMaxHeight(Properties props, int index)
  {
    String minHeightStr = props.getProperty("minHeight." + index);
    String maxHeightStr = props.getProperty("maxHeight." + index);
    
    if ((minHeightStr == null) && (maxHeightStr == null))
    {
      return null;
    }
    

    int minHeight = 0;
    
    if (minHeightStr != null)
    {
      minHeight = Config.parseInt(minHeightStr, -1);
      
      if (minHeight < 0)
      {
        Config.warn("Invalid minHeight: " + minHeightStr);
        return null;
      }
    }
    
    int maxHeight = 256;
    
    if (maxHeightStr != null)
    {
      maxHeight = Config.parseInt(maxHeightStr, -1);
      
      if (maxHeight < 0)
      {
        Config.warn("Invalid maxHeight: " + maxHeightStr);
        return null;
      }
    }
    
    if (maxHeight < 0)
    {
      Config.warn("Invalid minHeight, maxHeight: " + minHeightStr + ", " + maxHeightStr);
      return null;
    }
    

    RangeListInt list = new RangeListInt();
    list.addRange(new RangeInt(minHeight, maxHeight));
    return list;
  }
  


  public boolean isValid(String path)
  {
    if ((resourceLocations == null) && (rules == null))
    {
      Config.warn("No skins specified: " + path);
      return false;
    }
    



    if (rules != null)
    {
      for (int i = 0; i < rules.length; i++)
      {
        RandomMobsRule loc = rules[i];
        
        if (!loc.isValid(path))
        {
          return false;
        }
      }
    }
    
    if (resourceLocations != null)
    {
      for (int i = 0; i < resourceLocations.length; i++)
      {
        ResourceLocation var4 = resourceLocations[i];
        
        if (!Config.hasResource(var4))
        {
          Config.warn("Texture not found: " + var4.getResourcePath());
          return false;
        }
      }
    }
    
    return true;
  }
}
