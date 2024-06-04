package optifine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;

public class CustomColormap implements CustomColors.IColorizer
{
  public String name = null;
  public String basePath = null;
  private int format = -1;
  private MatchBlock[] matchBlocks = null;
  private String source = null;
  private int color = -1;
  private int yVariance = 0;
  private int yOffset = 0;
  private int width = 0;
  private int height = 0;
  private int[] colors = null;
  private float[][] colorsRgb = null;
  private static final int FORMAT_UNKNOWN = -1;
  private static final int FORMAT_VANILLA = 0;
  private static final int FORMAT_GRID = 1;
  private static final int FORMAT_FIXED = 2;
  public static final String KEY_FORMAT = "format";
  public static final String KEY_BLOCKS = "blocks";
  public static final String KEY_SOURCE = "source";
  public static final String KEY_COLOR = "color";
  public static final String KEY_Y_VARIANCE = "yVariance";
  public static final String KEY_Y_OFFSET = "yOffset";
  
  public CustomColormap(Properties props, String path, int width, int height)
  {
    ConnectedParser cp = new ConnectedParser("Colormap");
    name = cp.parseName(path);
    basePath = cp.parseBasePath(path);
    format = parseFormat(props.getProperty("format"));
    matchBlocks = cp.parseMatchBlocks(props.getProperty("blocks"));
    source = parseTexture(props.getProperty("source"), path, basePath);
    color = ConnectedParser.parseColor(props.getProperty("color"), -1);
    yVariance = cp.parseInt(props.getProperty("yVariance"), 0);
    yOffset = cp.parseInt(props.getProperty("yOffset"), 0);
    this.width = width;
    this.height = height;
  }
  
  private int parseFormat(String str)
  {
    if (str == null)
    {
      return 0;
    }
    if (str.equals("vanilla"))
    {
      return 0;
    }
    if (str.equals("grid"))
    {
      return 1;
    }
    if (str.equals("fixed"))
    {
      return 2;
    }
    

    warn("Unknown format: " + str);
    return -1;
  }
  

  public boolean isValid(String path)
  {
    if ((format != 0) && (format != 1))
    {
      if (format != 2)
      {
        return false;
      }
      
      if (color < 0)
      {
        color = 16777215;
      }
    }
    else
    {
      if (source == null)
      {
        warn("Source not defined: " + path);
        return false;
      }
      
      readColors();
      
      if (colors == null)
      {
        return false;
      }
      
      if (color < 0)
      {
        if (format == 0)
        {
          color = getColor(127, 127);
        }
        
        if (format == 1)
        {
          color = getColorGrid(BiomeGenBase.plains, new BlockPos(0, 64, 0));
        }
      }
    }
    
    return true;
  }
  
  public boolean isValidMatchBlocks(String path)
  {
    if (matchBlocks == null)
    {
      matchBlocks = detectMatchBlocks();
      
      if (matchBlocks == null)
      {
        warn("Match blocks not defined: " + path);
        return false;
      }
    }
    
    return true;
  }
  
  private MatchBlock[] detectMatchBlocks()
  {
    Block block = Block.getBlockFromName(name);
    
    if (block != null)
    {
      return new MatchBlock[] { new MatchBlock(Block.getIdFromBlock(block)) };
    }
    

    Pattern p = Pattern.compile("^block([0-9]+).*$");
    Matcher m = p.matcher(name);
    
    if (m.matches())
    {
      String cp = m.group(1);
      int mbs = Config.parseInt(cp, -1);
      
      if (mbs >= 0)
      {
        return new MatchBlock[] { new MatchBlock(mbs) };
      }
    }
    
    ConnectedParser cp1 = new ConnectedParser("Colormap");
    MatchBlock[] mbs1 = cp1.parseMatchBlock(name);
    return mbs1 != null ? mbs1 : null;
  }
  

  private void readColors()
  {
    try
    {
      colors = null;
      
      if (source == null)
      {
        return;
      }
      
      String e = source + ".png";
      ResourceLocation loc = new ResourceLocation(e);
      InputStream is = Config.getResourceStream(loc);
      
      if (is == null)
      {
        return;
      }
      
      BufferedImage img = net.minecraft.client.renderer.texture.TextureUtil.func_177053_a(is);
      
      if (img == null)
      {
        return;
      }
      
      int imgWidth = img.getWidth();
      int imgHeight = img.getHeight();
      boolean widthOk = (width < 0) || (width == imgWidth);
      boolean heightOk = (height < 0) || (height == imgHeight);
      
      if ((!widthOk) || (!heightOk))
      {
        dbg("Non-standard palette size: " + imgWidth + "x" + imgHeight + ", should be: " + width + "x" + height + ", path: " + e);
      }
      
      width = imgWidth;
      height = imgHeight;
      
      if ((width <= 0) || (height <= 0))
      {
        warn("Invalid palette size: " + imgWidth + "x" + imgHeight + ", path: " + e);
        return;
      }
      
      colors = new int[imgWidth * imgHeight];
      img.getRGB(0, 0, imgWidth, imgHeight, colors, 0, imgWidth);
    }
    catch (IOException var9)
    {
      var9.printStackTrace();
    }
  }
  
  private static void dbg(String str)
  {
    Config.dbg("CustomColors: " + str);
  }
  
  private static void warn(String str)
  {
    Config.warn("CustomColors: " + str);
  }
  


  private static String parseTexture(String texStr, String path, String basePath)
  {
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
  
  public boolean matchesBlock(BlockStateBase blockState)
  {
    return Matches.block(blockState, matchBlocks);
  }
  
  public int getColorRandom()
  {
    if (format == 2)
    {
      return color;
    }
    

    int index = CustomColors.random.nextInt(colors.length);
    return colors[index];
  }
  

  public int getColor(int index)
  {
    index = Config.limit(index, 0, colors.length);
    return colors[index] & 0xFFFFFF;
  }
  
  public int getColor(int cx, int cy)
  {
    cx = Config.limit(cx, 0, width - 1);
    cy = Config.limit(cy, 0, height - 1);
    return colors[(cy * width + cx)] & 0xFFFFFF;
  }
  
  public float[][] getColorsRgb()
  {
    if (colorsRgb == null)
    {
      colorsRgb = toRgb(colors);
    }
    
    return colorsRgb;
  }
  
  public int getColor(IBlockAccess blockAccess, BlockPos blockPos)
  {
    BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
    return getColor(biome, blockPos);
  }
  
  public boolean isColorConstant()
  {
    return format == 2;
  }
  
  public int getColor(BiomeGenBase biome, BlockPos blockPos)
  {
    return format == 1 ? getColorGrid(biome, blockPos) : format == 0 ? getColorVanilla(biome, blockPos) : color;
  }
  
  public int getColorSmooth(IBlockAccess blockAccess, double x, double y, double z, int radius)
  {
    if (format == 2)
    {
      return color;
    }
    

    int x0 = MathHelper.floor_double(x);
    int y0 = MathHelper.floor_double(y);
    int z0 = MathHelper.floor_double(z);
    int sumRed = 0;
    int sumGreen = 0;
    int sumBlue = 0;
    int count = 0;
    BlockPosM blockPosM = new BlockPosM(0, 0, 0);
    



    for (int r = x0 - radius; r <= x0 + radius; r++)
    {
      for (int g = z0 - radius; g <= z0 + radius; g++)
      {
        blockPosM.setXyz(r, y0, g);
        int b = getColor(blockAccess, blockPosM);
        sumRed += (b >> 16 & 0xFF);
        sumGreen += (b >> 8 & 0xFF);
        sumBlue += (b & 0xFF);
        count++;
      }
    }
    
    r = sumRed / count;
    int g = sumGreen / count;
    int b = sumBlue / count;
    return r << 16 | g << 8 | b;
  }
  

  private int getColorVanilla(BiomeGenBase biome, BlockPos blockPos)
  {
    double temperature = MathHelper.clamp_float(biome.func_180626_a(blockPos), 0.0F, 1.0F);
    double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
    rainfall *= temperature;
    int cx = (int)((1.0D - temperature) * (width - 1));
    int cy = (int)((1.0D - rainfall) * (height - 1));
    return getColor(cx, cy);
  }
  
  private int getColorGrid(BiomeGenBase biome, BlockPos blockPos)
  {
    int cx = biomeID;
    int cy = blockPos.getY() - yOffset;
    
    if (yVariance > 0)
    {
      int seed = blockPos.getX() << 16 + blockPos.getZ();
      int rand = Config.intHash(seed);
      int range = yVariance * 2 + 1;
      int diff = (rand & 0xFF) % range - yVariance;
      cy += diff;
    }
    
    return getColor(cx, cy);
  }
  
  public int getLength()
  {
    return format == 2 ? 1 : colors.length;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
  
  private static float[][] toRgb(int[] cols)
  {
    float[][] colsRgb = new float[cols.length][3];
    
    for (int i = 0; i < cols.length; i++)
    {
      int col = cols[i];
      float rf = (col >> 16 & 0xFF) / 255.0F;
      float gf = (col >> 8 & 0xFF) / 255.0F;
      float bf = (col & 0xFF) / 255.0F;
      float[] colRgb = colsRgb[i];
      colRgb[0] = rf;
      colRgb[1] = gf;
      colRgb[2] = bf;
    }
    
    return colsRgb;
  }
  
  public void addMatchBlock(MatchBlock mb)
  {
    if (matchBlocks == null)
    {
      matchBlocks = new MatchBlock[0];
    }
    
    matchBlocks = ((MatchBlock[])Config.addObjectToArray(matchBlocks, mb));
  }
  
  public void addMatchBlock(int blockId, int metadata)
  {
    MatchBlock mb = getMatchBlock(blockId);
    
    if (mb != null)
    {
      if (metadata >= 0)
      {
        mb.addMetadata(metadata);
      }
      
    }
    else {
      addMatchBlock(new MatchBlock(blockId, metadata));
    }
  }
  
  private MatchBlock getMatchBlock(int blockId)
  {
    if (matchBlocks == null)
    {
      return null;
    }
    

    for (int i = 0; i < matchBlocks.length; i++)
    {
      MatchBlock mb = matchBlocks[i];
      
      if (mb.getBlockId() == blockId)
      {
        return mb;
      }
    }
    
    return null;
  }
  

  public int[] getMatchBlockIds()
  {
    if (matchBlocks == null)
    {
      return null;
    }
    

    HashSet setIds = new HashSet();
    
    for (int ints = 0; ints < matchBlocks.length; ints++)
    {
      MatchBlock ids = matchBlocks[ints];
      
      if (ids.getBlockId() >= 0)
      {
        setIds.add(Integer.valueOf(ids.getBlockId()));
      }
    }
    
    Integer[] var5 = (Integer[])setIds.toArray(new Integer[setIds.size()]);
    int[] var6 = new int[var5.length];
    
    for (int i = 0; i < var5.length; i++)
    {
      var6[i] = var5[i].intValue();
    }
    
    return var6;
  }
  

  public String toString()
  {
    return basePath + "/" + name + ", blocks: " + Config.arrayToString(matchBlocks) + ", source: " + source;
  }
}
