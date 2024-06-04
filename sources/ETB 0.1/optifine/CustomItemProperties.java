package optifine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel.Builder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CustomItemProperties
{
  public String name = null;
  public String basePath = null;
  public int type = 1;
  public int[] items = null;
  public String texture = null;
  public Map<String, String> mapTextures = null;
  public RangeListInt damage = null;
  public boolean damagePercent = false;
  public int damageMask = 0;
  public RangeListInt stackSize = null;
  public RangeListInt enchantmentIds = null;
  public RangeListInt enchantmentLevels = null;
  public NbtTagValue[] nbtTagValues = null;
  public int blend = 1;
  public float speed = 0.0F;
  public float rotation = 0.0F;
  public int layer = 0;
  public float duration = 1.0F;
  public int weight = 0;
  public ResourceLocation textureLocation = null;
  public Map mapTextureLocations = null;
  public TextureAtlasSprite sprite = null;
  public Map mapSprites = null;
  public IBakedModel model = null;
  public Map<String, IBakedModel> mapModels = null;
  private int textureWidth = 0;
  private int textureHeight = 0;
  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_ITEM = 1;
  public static final int TYPE_ENCHANTMENT = 2;
  public static final int TYPE_ARMOR = 3;
  
  public CustomItemProperties(Properties props, String path)
  {
    name = parseName(path);
    basePath = parseBasePath(path);
    type = parseType(props.getProperty("type"));
    items = parseItems(props.getProperty("items"), props.getProperty("matchItems"));
    mapTextures = parseTextures(props, basePath);
    texture = parseTexture(props.getProperty("texture"), props.getProperty("tile"), props.getProperty("source"), path, basePath, type, mapTextures);
    String damageStr = props.getProperty("damage");
    
    if (damageStr != null)
    {
      damagePercent = damageStr.contains("%");
      damageStr.replace("%", "");
      damage = parseRangeListInt(damageStr);
      damageMask = parseInt(props.getProperty("damageMask"), 0);
    }
    
    stackSize = parseRangeListInt(props.getProperty("stackSize"));
    enchantmentIds = parseRangeListInt(props.getProperty("enchantmentIDs"));
    enchantmentLevels = parseRangeListInt(props.getProperty("enchantmentLevels"));
    nbtTagValues = parseNbtTagValues(props);
    blend = Blender.parseBlend(props.getProperty("blend"));
    speed = parseFloat(props.getProperty("speed"), 0.0F);
    rotation = parseFloat(props.getProperty("rotation"), 0.0F);
    layer = parseInt(props.getProperty("layer"), 0);
    weight = parseInt(props.getProperty("weight"), 0);
    duration = parseFloat(props.getProperty("duration"), 1.0F);
  }
  
  private static String parseName(String path)
  {
    String str = path;
    int pos = path.lastIndexOf('/');
    
    if (pos >= 0)
    {
      str = path.substring(pos + 1);
    }
    
    int pos2 = str.lastIndexOf('.');
    
    if (pos2 >= 0)
    {
      str = str.substring(0, pos2);
    }
    
    return str;
  }
  
  private static String parseBasePath(String path)
  {
    int pos = path.lastIndexOf('/');
    return pos < 0 ? "" : path.substring(0, pos);
  }
  
  private int parseType(String str)
  {
    if (str == null)
    {
      return 1;
    }
    if (str.equals("item"))
    {
      return 1;
    }
    if (str.equals("enchantment"))
    {
      return 2;
    }
    if (str.equals("armor"))
    {
      return 3;
    }
    

    Config.warn("Unknown method: " + str);
    return 0;
  }
  

  private int[] parseItems(String str, String str2)
  {
    if (str == null)
    {
      str = str2;
    }
    
    if (str == null)
    {
      return null;
    }
    

    str = str.trim();
    TreeSet setItemIds = new TreeSet();
    String[] tokens = Config.tokenize(str, " ");
    


    for (int integers = 0; integers < tokens.length; integers++)
    {
      String ints = tokens[integers];
      int i = Config.parseInt(ints, -1);
      
      if (i >= 0)
      {
        setItemIds.add(new Integer(i));

      }
      else
      {

        if (ints.contains("-"))
        {
          String[] item = Config.tokenize(ints, "-");
          
          if (item.length == 2)
          {
            int id = Config.parseInt(item[0], -1);
            int val2 = Config.parseInt(item[1], -1);
            
            if ((id >= 0) && (val2 >= 0))
            {
              int min = Math.min(id, val2);
              int max = Math.max(id, val2);
              int x = min;
              


              while (x <= max)
              {



                setItemIds.add(new Integer(x));
                x++;
              }
            }
          }
        }
        
        Item var16 = Item.getByNameOrId(ints);
        
        if (var16 == null)
        {
          Config.warn("Item not found: " + ints);
        }
        else
        {
          int id = Item.getIdFromItem(var16);
          
          if (id < 0)
          {
            Config.warn("Item not found: " + ints);
          }
          else
          {
            setItemIds.add(new Integer(id));
          }
        }
      }
    }
    
    Integer[] var14 = (Integer[])setItemIds.toArray(new Integer[setItemIds.size()]);
    int[] var15 = new int[var14.length];
    
    for (int i = 0; i < var15.length; i++)
    {
      var15[i] = var14[i].intValue();
    }
    
    return var15;
  }
  

  private static String parseTexture(String texStr, String texStr2, String texStr3, String path, String basePath, int type, Map<String, String> mapTexs)
  {
    if (texStr == null)
    {
      texStr = texStr2;
    }
    
    if (texStr == null)
    {
      texStr = texStr3;
    }
    


    if (texStr != null)
    {
      String str = ".png";
      
      if (texStr.endsWith(str))
      {
        texStr = texStr.substring(0, texStr.length() - str.length());
      }
      
      texStr = fixTextureName(texStr, basePath);
      return texStr;
    }
    if (type == 3)
    {
      return null;
    }
    

    if (mapTexs != null)
    {
      String str = (String)mapTexs.get("texture.bow_standby");
      
      if (str != null)
      {
        return str;
      }
    }
    
    String str = path;
    int pos = path.lastIndexOf('/');
    
    if (pos >= 0)
    {
      str = path.substring(pos + 1);
    }
    
    int pos2 = str.lastIndexOf('.');
    
    if (pos2 >= 0)
    {
      str = str.substring(0, pos2);
    }
    
    str = fixTextureName(str, basePath);
    return str;
  }
  

  private static Map parseTextures(Properties props, String basePath)
  {
    String prefix = "texture.";
    Map mapProps = getMatchingProperties(props, prefix);
    
    if (mapProps.size() <= 0)
    {
      return null;
    }
    

    Set keySet = mapProps.keySet();
    LinkedHashMap mapTex = new LinkedHashMap();
    Iterator it = keySet.iterator();
    
    while (it.hasNext())
    {
      String key = (String)it.next();
      String val = (String)mapProps.get(key);
      val = fixTextureName(val, basePath);
      mapTex.put(key, val);
    }
    
    return mapTex;
  }
  

  private static String fixTextureName(String iconName, String basePath)
  {
    iconName = TextureUtils.fixResourcePath(iconName, basePath);
    
    if ((!iconName.startsWith(basePath)) && (!iconName.startsWith("textures/")) && (!iconName.startsWith("mcpatcher/")))
    {
      iconName = basePath + "/" + iconName;
    }
    
    if (iconName.endsWith(".png"))
    {
      iconName = iconName.substring(0, iconName.length() - 4);
    }
    
    String pathBlocks = "textures/blocks/";
    
    if (iconName.startsWith(pathBlocks))
    {
      iconName = iconName.substring(pathBlocks.length());
    }
    
    if (iconName.startsWith("/"))
    {
      iconName = iconName.substring(1);
    }
    
    return iconName;
  }
  
  private int parseInt(String str, int defVal)
  {
    if (str == null)
    {
      return defVal;
    }
    

    str = str.trim();
    int val = Config.parseInt(str, Integer.MIN_VALUE);
    
    if (val == Integer.MIN_VALUE)
    {
      Config.warn("Invalid integer: " + str);
      return defVal;
    }
    

    return val;
  }
  


  private float parseFloat(String str, float defVal)
  {
    if (str == null)
    {
      return defVal;
    }
    

    str = str.trim();
    float val = Config.parseFloat(str, Float.MIN_VALUE);
    
    if (val == Float.MIN_VALUE)
    {
      Config.warn("Invalid float: " + str);
      return defVal;
    }
    

    return val;
  }
  


  private RangeListInt parseRangeListInt(String str)
  {
    if (str == null)
    {
      return null;
    }
    

    String[] tokens = Config.tokenize(str, " ");
    RangeListInt rangeList = new RangeListInt();
    
    for (int i = 0; i < tokens.length; i++)
    {
      String token = tokens[i];
      RangeInt range = parseRangeInt(token);
      
      if (range == null)
      {
        Config.warn("Invalid range list: " + str);
        return null;
      }
      
      rangeList.addRange(range);
    }
    
    return rangeList;
  }
  

  private RangeInt parseRangeInt(String str)
  {
    if (str == null)
    {
      return null;
    }
    

    str = str.trim();
    int countMinus = str.length() - str.replace("-", "").length();
    
    if (countMinus > 1)
    {
      Config.warn("Invalid range: " + str);
      return null;
    }
    

    String[] tokens = Config.tokenize(str, "- ");
    int[] vals = new int[tokens.length];
    

    for (int min = 0; min < tokens.length; min++)
    {
      String max = tokens[min];
      int val = Config.parseInt(max, -1);
      
      if (val < 0)
      {
        Config.warn("Invalid range: " + str);
        return null;
      }
      
      vals[min] = val;
    }
    
    if (vals.length == 1)
    {
      min = vals[0];
      
      if (str.startsWith("-"))
      {
        return new RangeInt(0, min);
      }
      if (str.endsWith("-"))
      {
        return new RangeInt(min, 255);
      }
      

      return new RangeInt(min, min);
    }
    
    if (vals.length == 2)
    {
      min = Math.min(vals[0], vals[1]);
      int var8 = Math.max(vals[0], vals[1]);
      return new RangeInt(min, var8);
    }
    

    Config.warn("Invalid range: " + str);
    return null;
  }
  



  private NbtTagValue[] parseNbtTagValues(Properties props)
  {
    String PREFIX_NBT = "nbt.";
    Map mapNbt = getMatchingProperties(props, PREFIX_NBT);
    
    if (mapNbt.size() <= 0)
    {
      return null;
    }
    

    ArrayList listNbts = new ArrayList();
    Set keySet = mapNbt.keySet();
    Iterator nbts = keySet.iterator();
    
    while (nbts.hasNext())
    {
      String key = (String)nbts.next();
      String val = (String)mapNbt.get(key);
      String id = key.substring(PREFIX_NBT.length());
      NbtTagValue nbt = new NbtTagValue(id, val);
      listNbts.add(nbt);
    }
    
    NbtTagValue[] nbts1 = (NbtTagValue[])listNbts.toArray(new NbtTagValue[listNbts.size()]);
    return nbts1;
  }
  

  private static Map getMatchingProperties(Properties props, String keyPrefix)
  {
    LinkedHashMap map = new LinkedHashMap();
    Set keySet = props.keySet();
    Iterator it = keySet.iterator();
    
    while (it.hasNext())
    {
      String key = (String)it.next();
      String val = props.getProperty(key);
      
      if (key.startsWith(keyPrefix))
      {
        map.put(key, val);
      }
    }
    
    return map;
  }
  
  public boolean isValid(String path)
  {
    if ((name != null) && (name.length() > 0))
    {
      if (basePath == null)
      {
        Config.warn("No base path found: " + path);
        return false;
      }
      if (type == 0)
      {
        Config.warn("No type defined: " + path);
        return false;
      }
      if (((type == 1) || (type == 3)) && (items == null))
      {
        Config.warn("No items defined: " + path);
        return false;
      }
      if ((texture == null) && (mapTextures == null))
      {
        Config.warn("No texture specified: " + path);
        return false;
      }
      if ((type == 2) && (enchantmentIds == null))
      {
        Config.warn("No enchantmentIDs specified: " + path);
        return false;
      }
      

      return true;
    }
    


    Config.warn("No name found: " + path);
    return false;
  }
  

  public void updateIcons(TextureMap textureMap)
  {
    if (texture != null)
    {
      textureLocation = getTextureLocation(texture);
      
      if (type == 1)
      {
        ResourceLocation keySet = getSpriteLocation(textureLocation);
        sprite = textureMap.func_174942_a(keySet);
      }
    }
    
    if (mapTextures != null)
    {
      mapTextureLocations = new HashMap();
      mapSprites = new HashMap();
      Set keySet1 = mapTextures.keySet();
      Iterator it = keySet1.iterator();
      
      while (it.hasNext())
      {
        String key = (String)it.next();
        String val = (String)mapTextures.get(key);
        ResourceLocation locTex = getTextureLocation(val);
        mapTextureLocations.put(key, locTex);
        
        if (type == 1)
        {
          ResourceLocation locSprite = getSpriteLocation(locTex);
          TextureAtlasSprite icon = textureMap.func_174942_a(locSprite);
          mapSprites.put(key, icon);
        }
      }
    }
  }
  
  private ResourceLocation getTextureLocation(String texName)
  {
    if (texName == null)
    {
      return null;
    }
    

    ResourceLocation resLoc = new ResourceLocation(texName);
    String domain = resLoc.getResourceDomain();
    String path = resLoc.getResourcePath();
    
    if (!path.contains("/"))
    {
      path = "textures/blocks/" + path;
    }
    
    String filePath = path + ".png";
    ResourceLocation locFile = new ResourceLocation(domain, filePath);
    boolean exists = Config.hasResource(locFile);
    
    if (!exists)
    {
      Config.warn("File not found: " + filePath);
    }
    
    return locFile;
  }
  

  private ResourceLocation getSpriteLocation(ResourceLocation resLoc)
  {
    String pathTex = resLoc.getResourcePath();
    pathTex = StrUtils.removePrefix(pathTex, "textures/");
    pathTex = StrUtils.removeSuffix(pathTex, ".png");
    ResourceLocation locTex = new ResourceLocation(resLoc.getResourceDomain(), pathTex);
    return locTex;
  }
  
  public void updateModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator)
  {
    String[] textures = getModelTextures();
    boolean useTint = isUseTint();
    model = makeBakedModel(textureMap, itemModelGenerator, textures, useTint);
    
    if ((type == 1) && (mapTextures != null))
    {
      Set keySet = mapTextures.keySet();
      Iterator it = keySet.iterator();
      
      while (it.hasNext())
      {
        String key = (String)it.next();
        String tex = (String)mapTextures.get(key);
        String path = StrUtils.removePrefix(key, "texture.");
        
        if ((path.startsWith("bow")) || (path.startsWith("fishing_rod")))
        {
          String[] texNames = { tex };
          IBakedModel modelTex = makeBakedModel(textureMap, itemModelGenerator, texNames, useTint);
          
          if (mapModels == null)
          {
            mapModels = new HashMap();
          }
          
          mapModels.put(path, modelTex);
        }
      }
    }
  }
  
  private boolean isUseTint()
  {
    return true;
  }
  
  private static IBakedModel makeBakedModel(TextureMap textureMap, ItemModelGenerator itemModelGenerator, String[] textures, boolean useTint)
  {
    ModelBlock modelBlockBase = makeModelBlock(textures);
    ModelBlock modelBlock = itemModelGenerator.func_178392_a(textureMap, modelBlockBase);
    IBakedModel model = bakeModel(textureMap, modelBlock, useTint);
    return model;
  }
  
  private String[] getModelTextures()
  {
    if ((this.type == 1) && (items.length == 1))
    {
      Item item = Item.getItemById(items[0]);
      


      if ((item == net.minecraft.init.Items.potionitem) && (damage != null) && (damage.getCountRanges() > 0))
      {
        RangeInt itemArmor1 = damage.getRange(0);
        int material1 = itemArmor1.getMin();
        boolean type1 = (material1 & 0x4000) != 0;
        String key = getMapTexture(mapTextures, "texture.potion_overlay", "items/potion_overlay");
        String texMain = null;
        
        if (type1)
        {
          texMain = getMapTexture(mapTextures, "texture.potion_bottle_splash", "items/potion_bottle_splash");
        }
        else
        {
          texMain = getMapTexture(mapTextures, "texture.potion_bottle_drinkable", "items/potion_bottle_drinkable");
        }
        
        return new String[] { key, texMain };
      }
      
      if ((item instanceof ItemArmor))
      {
        ItemArmor itemArmor = (ItemArmor)item;
        
        if (itemArmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER)
        {
          String material = "leather";
          String type = "helmet";
          
          if (armorType == 0)
          {
            type = "helmet";
          }
          
          if (armorType == 1)
          {
            type = "chestplate";
          }
          
          if (armorType == 2)
          {
            type = "leggings";
          }
          
          if (armorType == 3)
          {
            type = "boots";
          }
          
          String key = material + "_" + type;
          String texMain = getMapTexture(mapTextures, "texture." + key, "items/" + key);
          String texOverlay = getMapTexture(mapTextures, "texture." + key + "_overlay", "items/" + key + "_overlay");
          return new String[] { texMain, texOverlay };
        }
      }
    }
    
    return new String[] { texture };
  }
  
  private String getMapTexture(Map<String, String> map, String key, String def)
  {
    if (map == null)
    {
      return def;
    }
    

    String str = (String)map.get(key);
    return str == null ? def : str;
  }
  

  private static ModelBlock makeModelBlock(String[] modelTextures)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("{\"parent\": \"builtin/generated\",\"textures\": {");
    
    for (int modelStr = 0; modelStr < modelTextures.length; modelStr++)
    {
      String model = modelTextures[modelStr];
      
      if (modelStr > 0)
      {
        sb.append(", ");
      }
      
      sb.append("\"layer" + modelStr + "\": \"" + model + "\"");
    }
    
    sb.append("}}");
    String var4 = sb.toString();
    ModelBlock var5 = ModelBlock.deserialize(var4);
    return var5;
  }
  
  private static IBakedModel bakeModel(TextureMap textureMap, ModelBlock modelBlockIn, boolean useTint)
  {
    ModelRotation modelRotationIn = ModelRotation.X0_Y0;
    boolean uvLocked = false;
    TextureAtlasSprite var4 = textureMap.getSpriteSafe(modelBlockIn.resolveTextureName("particle"));
    SimpleBakedModel.Builder var5 = new SimpleBakedModel.Builder(modelBlockIn).func_177646_a(var4);
    Iterator var6 = modelBlockIn.getElements().iterator();
    Iterator var8;
    for (; var6.hasNext(); 
        



        var8.hasNext())
    {
      BlockPart var7 = (BlockPart)var6.next();
      var8 = field_178240_c.keySet().iterator();
      
      continue;
      
      EnumFacing var9 = (EnumFacing)var8.next();
      BlockPartFace var10 = (BlockPartFace)field_178240_c.get(var9);
      
      if (!useTint)
      {
        var10 = new BlockPartFace(field_178244_b, -1, field_178242_d, field_178243_e);
      }
      
      TextureAtlasSprite var11 = textureMap.getSpriteSafe(modelBlockIn.resolveTextureName(field_178242_d));
      BakedQuad quad = makeBakedQuad(var7, var10, var11, var9, modelRotationIn, uvLocked);
      
      if (field_178244_b == null)
      {
        var5.func_177648_a(quad);
      }
      else
      {
        var5.func_177650_a(modelRotationIn.func_177523_a(field_178244_b), quad);
      }
    }
    

    return var5.func_177645_b();
  }
  
  private static BakedQuad makeBakedQuad(BlockPart blockPart, BlockPartFace blockPartFace, TextureAtlasSprite textureAtlasSprite, EnumFacing enumFacing, ModelRotation modelRotation, boolean uvLocked)
  {
    FaceBakery faceBakery = new FaceBakery();
    return faceBakery.func_178414_a(field_178241_a, field_178239_b, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, field_178237_d, uvLocked, field_178238_e);
  }
  
  public String toString()
  {
    return basePath + "/" + name + ", type: " + type + ", items: [" + Config.arrayToString(items) + "], textture: " + texture;
  }
  
  public float getTextureWidth(TextureManager textureManager)
  {
    if (textureWidth <= 0)
    {
      if (textureLocation != null)
      {
        ITextureObject tex = textureManager.getTexture(textureLocation);
        int texId = tex.getGlTextureId();
        int prevTexId = GlStateManager.getBoundTexture();
        GlStateManager.func_179144_i(texId);
        textureWidth = GL11.glGetTexLevelParameteri(3553, 0, 4096);
        GlStateManager.func_179144_i(prevTexId);
      }
      
      if (textureWidth <= 0)
      {
        textureWidth = 16;
      }
    }
    
    return textureWidth;
  }
  
  public float getTextureHeight(TextureManager textureManager)
  {
    if (textureHeight <= 0)
    {
      if (textureLocation != null)
      {
        ITextureObject tex = textureManager.getTexture(textureLocation);
        int texId = tex.getGlTextureId();
        int prevTexId = GlStateManager.getBoundTexture();
        GlStateManager.func_179144_i(texId);
        textureHeight = GL11.glGetTexLevelParameteri(3553, 0, 4097);
        GlStateManager.func_179144_i(prevTexId);
      }
      
      if (textureHeight <= 0)
      {
        textureHeight = 16;
      }
    }
    
    return textureHeight;
  }
  
  public IBakedModel getModel(ModelResourceLocation modelLocation)
  {
    if ((modelLocation != null) && (mapTextures != null))
    {
      String modelPath = modelLocation.getResourcePath();
      
      if (mapModels != null)
      {
        IBakedModel customModel = (IBakedModel)mapModels.get(modelPath);
        
        if (customModel != null)
        {
          return customModel;
        }
      }
    }
    
    return model;
  }
}
