package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.src.Reflector;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
  private static final Logger logger = ;
  public static final ResourceLocation field_174945_f = new ResourceLocation("missingno");
  public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
  private final List listAnimatedSprites;
  private final Map mapRegisteredSprites;
  private final Map mapUploadedSprites;
  private final String basePath;
  private final IIconCreator field_174946_m;
  private int mipmapLevels;
  private final TextureAtlasSprite missingImage;
  private static final String __OBFID = "CL_00001058";
  
  public TextureMap(String p_i46099_1_)
  {
    this(p_i46099_1_, null);
  }
  
  public TextureMap(String p_i46100_1_, IIconCreator p_i46100_2_)
  {
    listAnimatedSprites = Lists.newArrayList();
    mapRegisteredSprites = Maps.newHashMap();
    mapUploadedSprites = Maps.newHashMap();
    missingImage = new TextureAtlasSprite("missingno");
    basePath = p_i46100_1_;
    field_174946_m = p_i46100_2_;
  }
  
  private void initMissingImage()
  {
    int[] var1 = TextureUtil.missingTextureData;
    missingImage.setIconWidth(16);
    missingImage.setIconHeight(16);
    int[][] var2 = new int[mipmapLevels + 1][];
    var2[0] = var1;
    missingImage.setFramesTextureData(Lists.newArrayList(new int[][][] { var2 }));
    missingImage.setIndexInMap(0);
  }
  
  public void loadTexture(IResourceManager p_110551_1_) throws IOException
  {
    if (field_174946_m != null)
    {
      func_174943_a(p_110551_1_, field_174946_m);
    }
  }
  
  public void func_174943_a(IResourceManager p_174943_1_, IIconCreator p_174943_2_)
  {
    mapRegisteredSprites.clear();
    p_174943_2_.func_177059_a(this);
    initMissingImage();
    deleteGlTexture();
    net.minecraft.src.ConnectedTextures.updateIcons(this);
    loadTextureAtlas(p_174943_1_);
  }
  
  public void loadTextureAtlas(IResourceManager p_110571_1_)
  {
    int var2 = net.minecraft.client.Minecraft.getGLMaximumTextureSize();
    Stitcher var3 = new Stitcher(var2, var2, true, 0, mipmapLevels);
    mapUploadedSprites.clear();
    listAnimatedSprites.clear();
    int var4 = Integer.MAX_VALUE;
    Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
    int var5 = 1 << mipmapLevels;
    Iterator var6 = mapRegisteredSprites.entrySet().iterator();
    


    while (var6.hasNext())
    {
      Map.Entry var25 = (Map.Entry)var6.next();
      TextureAtlasSprite var26 = (TextureAtlasSprite)var25.getValue();
      ResourceLocation var27 = new ResourceLocation(var26.getIconName());
      ResourceLocation var28 = completeResourceLocation(var27, 0);
      
      if (!var26.hasCustomLoader(p_110571_1_, var27))
      {
        try
        {
          IResource var30 = p_110571_1_.getResource(var28);
          BufferedImage[] var31 = new BufferedImage[1 + mipmapLevels];
          var31[0] = TextureUtil.func_177053_a(var30.getInputStream());
          TextureMetadataSection var34 = (TextureMetadataSection)var30.getMetadata("texture");
          
          if (var34 != null)
          {
            List var19 = var34.getListMipmaps();
            

            if (!var19.isEmpty())
            {
              int var38 = var31[0].getWidth();
              int var36 = var31[0].getHeight();
              
              if ((MathHelper.roundUpToPowerOfTwo(var38) != var38) || (MathHelper.roundUpToPowerOfTwo(var36) != var36))
              {
                throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
              }
            }
            
            Iterator var381 = var19.iterator();
            
            while (var381.hasNext())
            {
              int var36 = ((Integer)var381.next()).intValue();
              
              if ((var36 > 0) && (var36 < var31.length - 1) && (var31[var36] == null))
              {
                ResourceLocation var17 = completeResourceLocation(var27, var36);
                
                try
                {
                  var31[var36] = TextureUtil.func_177053_a(p_110571_1_.getResource(var17).getInputStream());
                }
                catch (IOException var22)
                {
                  logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(var36), var17, var22 });
                }
              }
            }
          }
          
          AnimationMetadataSection var192 = (AnimationMetadataSection)var30.getMetadata("animation");
          var26.func_180598_a(var31, var192);
        }
        catch (RuntimeException var23)
        {
          logger.error("Unable to parse metadata from " + var28, var23);
          continue;
        }
        catch (IOException var24)
        {
          logger.error("Using missing texture, unable to load " + var28 + ", " + var24.getClass().getName());
          continue;
        }
        
        var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
        int var301 = Math.min(Integer.lowestOneBit(var26.getIconWidth()), Integer.lowestOneBit(var26.getIconHeight()));
        
        if (var301 < var5)
        {
          logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { var28, Integer.valueOf(var26.getIconWidth()), Integer.valueOf(var26.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(var5)), Integer.valueOf(MathHelper.calculateLogBaseTwo(var301)) });
          var5 = var301;
        }
        
        var3.addSprite(var26);
      }
      else if (!var26.load(p_110571_1_, var27))
      {
        var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
        var3.addSprite(var26);
      }
    }
    
    int var251 = Math.min(var4, var5);
    int var261 = MathHelper.calculateLogBaseTwo(var251);
    
    if (var261 < 0)
    {
      var261 = 0;
    }
    
    if (var261 < mipmapLevels)
    {
      logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { basePath, Integer.valueOf(mipmapLevels), Integer.valueOf(var261), Integer.valueOf(var251) });
      mipmapLevels = var261;
    }
    
    Iterator var271 = mapRegisteredSprites.values().iterator();
    
    while (var271.hasNext())
    {
      final TextureAtlasSprite var281 = (TextureAtlasSprite)var271.next();
      
      try
      {
        var281.generateMipmaps(mipmapLevels);
      }
      catch (Throwable var21)
      {
        CrashReport var311 = CrashReport.makeCrashReport(var21, "Applying mipmap");
        CrashReportCategory var341 = var311.makeCategory("Sprite being mipmapped");
        var341.addCrashSectionCallable("Sprite name", new Callable()
        {
          private static final String __OBFID = "CL_00001059";
          
          public String call() {
            return var281.getIconName();
          }
        });
        var341.addCrashSectionCallable("Sprite size", new Callable()
        {
          private static final String __OBFID = "CL_00001060";
          
          public String call() {
            return var281.getIconWidth() + " x " + var281.getIconHeight();
          }
        });
        var341.addCrashSectionCallable("Sprite frames", new Callable()
        {
          private static final String __OBFID = "CL_00001061";
          
          public String call() {
            return var281.getFrameCount() + " frames";
          }
        });
        var341.addCrashSection("Mipmap levels", Integer.valueOf(mipmapLevels));
        throw new ReportedException(var311);
      }
    }
    
    missingImage.generateMipmaps(mipmapLevels);
    var3.addSprite(missingImage);
    
    try
    {
      var3.doStitch();
    }
    catch (StitcherException var20)
    {
      throw var20;
    }
    
    logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(var3.getCurrentWidth()), Integer.valueOf(var3.getCurrentHeight()), basePath });
    TextureUtil.func_180600_a(getGlTextureId(), mipmapLevels, var3.getCurrentWidth(), var3.getCurrentHeight());
    HashMap var282 = Maps.newHashMap(mapRegisteredSprites);
    Iterator var302 = var3.getStichSlots().iterator();
    

    while (var302.hasNext())
    {
      TextureAtlasSprite var312 = (TextureAtlasSprite)var302.next();
      String var342 = var312.getIconName();
      var282.remove(var342);
      mapUploadedSprites.put(var342, var312);
      
      try
      {
        TextureUtil.uploadTextureMipmap(var312.getFrameTextureData(0), var312.getIconWidth(), var312.getIconHeight(), var312.getOriginX(), var312.getOriginY(), false, false);
      }
      catch (Throwable var191)
      {
        CrashReport var361 = CrashReport.makeCrashReport(var191, "Stitching texture atlas");
        CrashReportCategory var382 = var361.makeCategory("Texture being stitched together");
        var382.addCrashSection("Atlas path", basePath);
        var382.addCrashSection("Sprite", var312);
        throw new ReportedException(var361);
      }
      
      if (var312.hasAnimationMetadata())
      {
        listAnimatedSprites.add(var312);
      }
    }
    
    var302 = var282.values().iterator();
    
    while (var302.hasNext())
    {
      TextureAtlasSprite var312 = (TextureAtlasSprite)var302.next();
      var312.copyFrom(missingImage);
    }
    
    TextureUtil.func_177055_a(basePath.replaceAll("/", "_"), getGlTextureId(), mipmapLevels, var3.getCurrentWidth(), var3.getCurrentHeight());
    Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
  }
  


  private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_, int p_147634_2_)
  {
    return p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { basePath, p_147634_1_.getResourcePath(), ".png" })) : isAbsoluteLocation(p_147634_1_) ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + "mipmap" + p_147634_2_ + ".png") : p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + ".png") : new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { basePath, p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" }));
  }
  
  public TextureAtlasSprite getAtlasSprite(String p_110572_1_)
  {
    TextureAtlasSprite var2 = (TextureAtlasSprite)mapUploadedSprites.get(p_110572_1_);
    
    if (var2 == null)
    {
      var2 = missingImage;
    }
    
    return var2;
  }
  
  public void updateAnimations()
  {
    TextureUtil.bindTexture(getGlTextureId());
    Iterator var1 = listAnimatedSprites.iterator();
    
    while (var1.hasNext())
    {
      TextureAtlasSprite var2 = (TextureAtlasSprite)var1.next();
      
      if (isTerrainAnimationActive(var2))
      {
        var2.updateAnimation();
      }
    }
  }
  
  public TextureAtlasSprite func_174942_a(ResourceLocation p_174942_1_)
  {
    if (p_174942_1_ == null)
    {
      throw new IllegalArgumentException("Location cannot be null!");
    }
    

    TextureAtlasSprite var2 = (TextureAtlasSprite)mapRegisteredSprites.get(p_174942_1_.toString());
    
    if ((var2 == null) && (Reflector.ModLoader_getCustomAnimationLogic.exists()))
    {
      var2 = (TextureAtlasSprite)Reflector.call(Reflector.ModLoader_getCustomAnimationLogic, new Object[] { p_174942_1_ });
    }
    
    if (var2 == null)
    {
      var2 = TextureAtlasSprite.func_176604_a(p_174942_1_);
      mapRegisteredSprites.put(p_174942_1_.toString(), var2);
      
      if (((var2 instanceof TextureAtlasSprite)) && (var2.getIndexInMap() < 0))
      {
        var2.setIndexInMap(mapRegisteredSprites.size());
      }
    }
    
    return var2;
  }
  

  public void tick()
  {
    updateAnimations();
  }
  
  public void setMipmapLevels(int p_147633_1_)
  {
    mipmapLevels = p_147633_1_;
  }
  
  public TextureAtlasSprite func_174944_f()
  {
    return missingImage;
  }
  
  public TextureAtlasSprite getTextureExtry(String name)
  {
    ResourceLocation loc = new ResourceLocation(name);
    return (TextureAtlasSprite)mapRegisteredSprites.get(loc.toString());
  }
  
  public boolean setTextureEntry(String name, TextureAtlasSprite entry)
  {
    if (!mapRegisteredSprites.containsKey(name))
    {
      mapRegisteredSprites.put(name, entry);
      
      if (entry.getIndexInMap() < 0)
      {
        entry.setIndexInMap(mapRegisteredSprites.size());
      }
      
      return true;
    }
    

    return false;
  }
  

  private boolean isAbsoluteLocation(ResourceLocation loc)
  {
    String path = loc.getResourcePath();
    return isAbsoluteLocationPath(path);
  }
  
  private boolean isAbsoluteLocationPath(String resPath)
  {
    String path = resPath.toLowerCase();
    return (path.startsWith("mcpatcher/")) || (path.startsWith("optifine/"));
  }
  
  public TextureAtlasSprite getSpriteSafe(String name)
  {
    ResourceLocation loc = new ResourceLocation(name);
    return (TextureAtlasSprite)mapRegisteredSprites.get(loc.toString());
  }
  
  private boolean isTerrainAnimationActive(TextureAtlasSprite ts)
  {
    return (ts != TextureUtils.iconWaterStill) && (ts != TextureUtils.iconWaterFlow) ? Config.isAnimatedLava() : (ts != TextureUtils.iconLavaStill) && (ts != TextureUtils.iconLavaFlow) ? Config.isAnimatedFire() : (ts != TextureUtils.iconFireLayer0) && (ts != TextureUtils.iconFireLayer1) ? Config.isAnimatedTerrain() : ts == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedWater();
  }
  
  public int getCountRegisteredSprites()
  {
    return mapRegisteredSprites.size();
  }
}
