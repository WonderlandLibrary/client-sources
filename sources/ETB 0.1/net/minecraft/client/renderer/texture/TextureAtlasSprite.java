package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.TextureUtils;
import shadersmod.client.ShadersTex;

public class TextureAtlasSprite
{
  private final String iconName;
  protected List framesTextureData = Lists.newArrayList();
  protected int[][] field_176605_b;
  private AnimationMetadataSection animationMetadata;
  protected boolean rotated;
  protected int originX;
  protected int originY;
  protected int width;
  protected int height;
  private float minU;
  private float maxU;
  private float minV;
  private float maxV;
  protected int frameCounter;
  protected int tickCounter;
  private static String field_176607_p = "builtin/clock";
  private static String field_176606_q = "builtin/compass";
  private static final String __OBFID = "CL_00001062";
  private int indexInMap = -1;
  public float baseU;
  public float baseV;
  public int sheetWidth;
  public int sheetHeight;
  public int glSpriteTextureId = -1;
  public TextureAtlasSprite spriteSingle = null;
  public boolean isSpriteSingle = false;
  public int mipmapLevels = 0;
  
  private TextureAtlasSprite(TextureAtlasSprite parent)
  {
    iconName = iconName;
    isSpriteSingle = true;
  }
  
  protected TextureAtlasSprite(String p_i1282_1_)
  {
    iconName = p_i1282_1_;
    
    if (Config.isMultiTexture())
    {
      spriteSingle = new TextureAtlasSprite(this);
    }
  }
  
  protected static TextureAtlasSprite func_176604_a(ResourceLocation p_176604_0_)
  {
    String var1 = p_176604_0_.toString();
    return field_176606_q.equals(var1) ? new TextureCompass(var1) : field_176607_p.equals(var1) ? new TextureClock(var1) : new TextureAtlasSprite(var1);
  }
  
  public static void func_176602_a(String p_176602_0_)
  {
    field_176607_p = p_176602_0_;
  }
  
  public static void func_176603_b(String p_176603_0_)
  {
    field_176606_q = p_176603_0_;
  }
  
  public void initSprite(int p_110971_1_, int p_110971_2_, int p_110971_3_, int p_110971_4_, boolean p_110971_5_)
  {
    originX = p_110971_3_;
    originY = p_110971_4_;
    rotated = p_110971_5_;
    float var6 = (float)(0.009999999776482582D / p_110971_1_);
    float var7 = (float)(0.009999999776482582D / p_110971_2_);
    minU = (p_110971_3_ / (float)p_110971_1_ + var6);
    maxU = ((p_110971_3_ + width) / (float)p_110971_1_ - var6);
    minV = (p_110971_4_ / p_110971_2_ + var7);
    maxV = ((p_110971_4_ + height) / p_110971_2_ - var7);
    baseU = Math.min(minU, maxU);
    baseV = Math.min(minV, maxV);
    
    if (spriteSingle != null)
    {
      spriteSingle.initSprite(width, height, 0, 0, false);
    }
  }
  
  public void copyFrom(TextureAtlasSprite p_94217_1_)
  {
    originX = originX;
    originY = originY;
    width = width;
    height = height;
    rotated = rotated;
    minU = minU;
    maxU = maxU;
    minV = minV;
    maxV = maxV;
    
    if (spriteSingle != null)
    {
      spriteSingle.initSprite(width, height, 0, 0, false);
    }
  }
  



  public int getOriginX()
  {
    return originX;
  }
  



  public int getOriginY()
  {
    return originY;
  }
  



  public int getIconWidth()
  {
    return width;
  }
  



  public int getIconHeight()
  {
    return height;
  }
  



  public float getMinU()
  {
    return minU;
  }
  



  public float getMaxU()
  {
    return maxU;
  }
  



  public float getInterpolatedU(double p_94214_1_)
  {
    float var3 = maxU - minU;
    return minU + var3 * (float)p_94214_1_ / 16.0F;
  }
  



  public float getMinV()
  {
    return minV;
  }
  



  public float getMaxV()
  {
    return maxV;
  }
  



  public float getInterpolatedV(double p_94207_1_)
  {
    float var3 = maxV - minV;
    return minV + var3 * ((float)p_94207_1_ / 16.0F);
  }
  
  public String getIconName()
  {
    return iconName;
  }
  
  public void updateAnimation()
  {
    tickCounter += 1;
    
    if (tickCounter >= animationMetadata.getFrameTimeSingle(frameCounter))
    {
      int var1 = animationMetadata.getFrameIndex(frameCounter);
      int var2 = animationMetadata.getFrameCount() == 0 ? framesTextureData.size() : animationMetadata.getFrameCount();
      frameCounter = ((frameCounter + 1) % var2);
      tickCounter = 0;
      int var3 = animationMetadata.getFrameIndex(frameCounter);
      boolean texBlur = false;
      boolean texClamp = isSpriteSingle;
      
      if ((var1 != var3) && (var3 >= 0) && (var3 < framesTextureData.size()))
      {
        if (Config.isShaders())
        {
          ShadersTex.uploadTexSub((int[][])framesTextureData.get(var3), width, height, originX, originY, texBlur, texClamp);
        }
        else
        {
          TextureUtil.uploadTextureMipmap((int[][])framesTextureData.get(var3), width, height, originX, originY, texBlur, texClamp);
        }
      }
    }
    else if (animationMetadata.func_177219_e())
    {
      func_180599_n();
    }
  }
  
  private void func_180599_n()
  {
    double var1 = 1.0D - tickCounter / animationMetadata.getFrameTimeSingle(frameCounter);
    int var3 = animationMetadata.getFrameIndex(frameCounter);
    int var4 = animationMetadata.getFrameCount() == 0 ? framesTextureData.size() : animationMetadata.getFrameCount();
    int var5 = animationMetadata.getFrameIndex((frameCounter + 1) % var4);
    
    if ((var3 != var5) && (var5 >= 0) && (var5 < framesTextureData.size()))
    {
      int[][] var6 = (int[][])framesTextureData.get(var3);
      int[][] var7 = (int[][])framesTextureData.get(var5);
      
      if ((field_176605_b == null) || (field_176605_b.length != var6.length))
      {
        field_176605_b = new int[var6.length][];
      }
      
      for (int var8 = 0; var8 < var6.length; var8++)
      {
        if (field_176605_b[var8] == null)
        {
          field_176605_b[var8] = new int[var6[var8].length];
        }
        
        if ((var8 < var7.length) && (var7[var8].length == var6[var8].length))
        {
          for (int var9 = 0; var9 < var6[var8].length; var9++)
          {
            int var10 = var6[var8][var9];
            int var11 = var7[var8][var9];
            int var12 = (int)(((var10 & 0xFF0000) >> 16) * var1 + ((var11 & 0xFF0000) >> 16) * (1.0D - var1));
            int var13 = (int)(((var10 & 0xFF00) >> 8) * var1 + ((var11 & 0xFF00) >> 8) * (1.0D - var1));
            int var14 = (int)((var10 & 0xFF) * var1 + (var11 & 0xFF) * (1.0D - var1));
            field_176605_b[var8][var9] = (var10 & 0xFF000000 | var12 << 16 | var13 << 8 | var14);
          }
        }
      }
      
      TextureUtil.uploadTextureMipmap(field_176605_b, width, height, originX, originY, false, false);
    }
  }
  
  public int[][] getFrameTextureData(int p_147965_1_)
  {
    return (int[][])framesTextureData.get(p_147965_1_);
  }
  
  public int getFrameCount()
  {
    return framesTextureData.size();
  }
  
  public void setIconWidth(int p_110966_1_)
  {
    width = p_110966_1_;
    
    if (spriteSingle != null)
    {
      spriteSingle.setIconWidth(width);
    }
  }
  
  public void setIconHeight(int p_110969_1_)
  {
    height = p_110969_1_;
    
    if (spriteSingle != null)
    {
      spriteSingle.setIconHeight(height);
    }
  }
  
  public void func_180598_a(BufferedImage[] p_180598_1_, AnimationMetadataSection p_180598_2_) throws IOException
  {
    resetSprite();
    int var3 = p_180598_1_[0].getWidth();
    int var4 = p_180598_1_[0].getHeight();
    width = var3;
    height = var4;
    int[][] var5 = new int[p_180598_1_.length][];
    

    for (int var6 = 0; var6 < p_180598_1_.length; var6++)
    {
      BufferedImage i = p_180598_1_[var6];
      
      if (i != null)
      {
        if ((var6 > 0) && ((i.getWidth() != var3 >> var6) || (i.getHeight() != var4 >> var6)))
        {
          throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] { Integer.valueOf(var6), Integer.valueOf(i.getWidth()), Integer.valueOf(i.getHeight()), Integer.valueOf(var3 >> var6), Integer.valueOf(var4 >> var6) }));
        }
        
        var5[var6] = new int[i.getWidth() * i.getHeight()];
        i.getRGB(0, 0, i.getWidth(), i.getHeight(), var5[var6], 0, i.getWidth());
      }
    }
    



    if (p_180598_2_ == null)
    {
      if (var4 != var3)
      {
        throw new RuntimeException("broken aspect ratio and not an animation");
      }
      
      framesTextureData.add(var5);
    }
    else
    {
      var6 = var4 / var3;
      int var11 = var3;
      int datas = var3;
      height = width;
      
      if (p_180598_2_.getFrameCount() > 0)
      {
        Iterator data = p_180598_2_.getFrameIndexSet().iterator();
        
        while (data.hasNext())
        {
          int di = ((Integer)data.next()).intValue();
          
          if (di >= var6)
          {
            throw new RuntimeException("invalid frameindex " + di);
          }
          
          allocateFrameTextureData(di);
          framesTextureData.set(di, getFrameTextureData(var5, var11, datas, di));
        }
        
        animationMetadata = p_180598_2_;
      }
      else
      {
        ArrayList var13 = Lists.newArrayList();
        
        for (int di = 0; di < var6; di++)
        {
          framesTextureData.add(getFrameTextureData(var5, var11, datas, di));
          var13.add(new AnimationFrame(di, -1));
        }
        
        animationMetadata = new AnimationMetadataSection(var13, width, height, p_180598_2_.getFrameTime(), p_180598_2_.func_177219_e());
      }
    }
    
    for (int var11 = 0; var11 < framesTextureData.size(); var11++)
    {
      int[][] var12 = (int[][])framesTextureData.get(var11);
      
      if ((var12 != null) && (!iconName.startsWith("minecraft:blocks/leaves_")))
      {
        for (int di = 0; di < var12.length; di++)
        {
          int[] var14 = var12[di];
          fixTransparentColor(var14);
        }
      }
    }
    
    if (spriteSingle != null)
    {
      spriteSingle.func_180598_a(p_180598_1_, p_180598_2_);
    }
  }
  
  public void generateMipmaps(int p_147963_1_)
  {
    ArrayList var2 = Lists.newArrayList();
    
    for (int var3 = 0; var3 < framesTextureData.size(); var3++)
    {
      final int[][] var4 = (int[][])framesTextureData.get(var3);
      
      if (var4 != null)
      {
        try
        {
          var2.add(TextureUtil.generateMipmapData(p_147963_1_, width, var4));
        }
        catch (Throwable var8)
        {
          CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
          CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
          var7.addCrashSection("Frame index", Integer.valueOf(var3));
          var7.addCrashSectionCallable("Frame sizes", new Callable()
          {
            private static final String __OBFID = "CL_00001063";
            
            public String call() {
              StringBuilder var1 = new StringBuilder();
              int[][] var2 = var4;
              int var3 = var2.length;
              
              for (int var4x = 0; var4x < var3; var4x++)
              {
                int[] var5 = var2[var4x];
                
                if (var1.length() > 0)
                {
                  var1.append(", ");
                }
                
                var1.append(var5 == null ? "null" : Integer.valueOf(var5.length));
              }
              
              return var1.toString();
            }
          });
          throw new net.minecraft.util.ReportedException(var6);
        }
      }
    }
    
    setFramesTextureData(var2);
    
    if (spriteSingle != null)
    {
      spriteSingle.generateMipmaps(p_147963_1_);
    }
  }
  
  private void allocateFrameTextureData(int p_130099_1_)
  {
    if (framesTextureData.size() <= p_130099_1_)
    {
      for (int var2 = framesTextureData.size(); var2 <= p_130099_1_; var2++)
      {
        framesTextureData.add(null);
      }
    }
    
    if (spriteSingle != null)
    {
      spriteSingle.allocateFrameTextureData(p_130099_1_);
    }
  }
  
  private static int[][] getFrameTextureData(int[][] p_147962_0_, int p_147962_1_, int p_147962_2_, int p_147962_3_)
  {
    int[][] var4 = new int[p_147962_0_.length][];
    
    for (int var5 = 0; var5 < p_147962_0_.length; var5++)
    {
      int[] var6 = p_147962_0_[var5];
      
      if (var6 != null)
      {
        var4[var5] = new int[(p_147962_1_ >> var5) * (p_147962_2_ >> var5)];
        System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
      }
    }
    
    return var4;
  }
  
  public void clearFramesTextureData()
  {
    framesTextureData.clear();
    
    if (spriteSingle != null)
    {
      spriteSingle.clearFramesTextureData();
    }
  }
  
  public boolean hasAnimationMetadata()
  {
    return animationMetadata != null;
  }
  
  public void setFramesTextureData(List p_110968_1_)
  {
    framesTextureData = p_110968_1_;
    
    if (spriteSingle != null)
    {
      spriteSingle.setFramesTextureData(p_110968_1_);
    }
  }
  
  private void resetSprite()
  {
    animationMetadata = null;
    setFramesTextureData(Lists.newArrayList());
    frameCounter = 0;
    tickCounter = 0;
    
    if (spriteSingle != null)
    {
      spriteSingle.resetSprite();
    }
  }
  
  public String toString()
  {
    return "TextureAtlasSprite{name='" + iconName + '\'' + ", frameCount=" + framesTextureData.size() + ", rotated=" + rotated + ", x=" + originX + ", y=" + originY + ", height=" + height + ", width=" + width + ", u0=" + minU + ", u1=" + maxU + ", v0=" + minV + ", v1=" + maxV + '}';
  }
  
  public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
  {
    return false;
  }
  
  public boolean load(IResourceManager manager, ResourceLocation location)
  {
    return true;
  }
  
  public int getIndexInMap()
  {
    return indexInMap;
  }
  
  public void setIndexInMap(int indexInMap)
  {
    this.indexInMap = indexInMap;
  }
  
  private void fixTransparentColor(int[] data)
  {
    if (data != null)
    {
      long redSum = 0L;
      long greenSum = 0L;
      long blueSum = 0L;
      long count = 0L;
      






      for (int redAvg = 0; redAvg < data.length; redAvg++)
      {
        int greenAvg = data[redAvg];
        int blueAvg = greenAvg >> 24 & 0xFF;
        
        if (blueAvg >= 16)
        {
          int colAvg = greenAvg >> 16 & 0xFF;
          int i = greenAvg >> 8 & 0xFF;
          int col = greenAvg & 0xFF;
          redSum += colAvg;
          greenSum += i;
          blueSum += col;
          count += 1L;
        }
      }
      
      if (count > 0L)
      {
        redAvg = (int)(redSum / count);
        int greenAvg = (int)(greenSum / count);
        int blueAvg = (int)(blueSum / count);
        int colAvg = redAvg << 16 | greenAvg << 8 | blueAvg;
        
        for (int i = 0; i < data.length; i++)
        {
          int col = data[i];
          int alpha = col >> 24 & 0xFF;
          
          if (alpha <= 16)
          {
            data[i] = colAvg;
          }
        }
      }
    }
  }
  
  public double getSpriteU16(float atlasU)
  {
    float dU = maxU - minU;
    return (atlasU - minU) / dU * 16.0F;
  }
  
  public double getSpriteV16(float atlasV)
  {
    float dV = maxV - minV;
    return (atlasV - minV) / dV * 16.0F;
  }
  
  public void bindSpriteTexture()
  {
    if (glSpriteTextureId < 0)
    {
      glSpriteTextureId = TextureUtil.glGenTextures();
      TextureUtil.func_180600_a(glSpriteTextureId, mipmapLevels, width, height);
      TextureUtils.applyAnisotropicLevel();
    }
    
    TextureUtils.bindTexture(glSpriteTextureId);
  }
  
  public void deleteSpriteTexture()
  {
    if (glSpriteTextureId >= 0)
    {
      TextureUtil.deleteTexture(glSpriteTextureId);
      glSpriteTextureId = -1;
    }
  }
  
  public float toSingleU(float u)
  {
    u -= baseU;
    float ku = sheetWidth / width;
    u *= ku;
    return u;
  }
  
  public float toSingleV(float v)
  {
    v -= baseV;
    float kv = sheetHeight / height;
    v *= kv;
    return v;
  }
}
