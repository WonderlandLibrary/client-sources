package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.src.ConnectedTextures;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorForge;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
    private static final Logger logger = LogManager.getLogger();
    public static final ResourceLocation field_174945_f = new ResourceLocation("missingno");
    public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
    private final List listAnimatedSprites;
    private final Map mapRegisteredSprites;
    private final Map mapUploadedSprites;
    private final String basePath;
    private final IIconCreator field_174946_m;
    private int mipmapLevels;
    private final TextureAtlasSprite missingImage;

    private static final boolean ENABLE_SKIP = Boolean.parseBoolean(System.getProperty("fml.skipFirstTextureLoad", "true"));
    private boolean skipFirst;


    public TextureMap(String p_i46099_1_)
    {
        this(p_i46099_1_, (IIconCreator)null);
    }

    public TextureMap(String p_i46099_1_, boolean skipFirst)
    {
        this(p_i46099_1_, (IIconCreator)null, skipFirst);
    }

    public TextureMap(String p_i46100_1_, IIconCreator p_i46100_2_)
    {
        this(p_i46100_1_, p_i46100_2_, false);
    }

    public TextureMap(String p_i46100_1_, IIconCreator p_i46100_2_, boolean skipFirst)
    {
        this.skipFirst = false;
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new TextureAtlasSprite("missingno");
        this.basePath = p_i46100_1_;
        this.field_174946_m = p_i46100_2_;
        this.skipFirst = skipFirst && ENABLE_SKIP;
    }

    private void initMissingImage()
    {
        int size = this.getMinSpriteSize();
        int[] var1 = this.getMissingImageData(size);
        this.missingImage.setIconWidth(size);
        this.missingImage.setIconHeight(size);
        int[][] var2 = new int[this.mipmapLevels + 1][];
        var2[0] = var1;
        this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][] {var2}));
        this.missingImage.setIndexInMap(0);
    }

    public void loadTexture(IResourceManager p_110551_1_) throws IOException
    {
        if (this.field_174946_m != null)
        {
            this.func_174943_a(p_110551_1_, this.field_174946_m);
        }
    }

    public void func_174943_a(IResourceManager p_174943_1_, IIconCreator p_174943_2_)
    {
        this.mapRegisteredSprites.clear();
        p_174943_2_.func_177059_a(this);

        if (this.mipmapLevels >= 4)
        {
            this.mipmapLevels = this.detectMaxMipmapLevel(this.mapRegisteredSprites, p_174943_1_);
            Config.log("Mipmap levels: " + this.mipmapLevels);
        }

        this.initMissingImage();
        this.deleteGlTexture();
        ConnectedTextures.updateIcons(this);
        this.loadTextureAtlas(p_174943_1_);
    }

    public void loadTextureAtlas(IResourceManager p_110571_1_)
    {
        Config.dbg("Multitexture: " + Config.isMultiTexture());

        if (Config.isMultiTexture())
        {
            Iterator var2 = this.mapUploadedSprites.values().iterator();

            while (var2.hasNext())
            {
                TextureAtlasSprite var3 = (TextureAtlasSprite)var2.next();
                var3.deleteSpriteTexture();
            }
        }

        int var21 = Minecraft.getGLMaximumTextureSize();
        Stitcher var22 = new Stitcher(var21, var21, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int var4 = Integer.MAX_VALUE;
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] {this});
        int minSpriteSize = this.getMinSpriteSize();
        int var5 = 1 << this.mipmapLevels;
        Iterator var6 = this.mapRegisteredSprites.entrySet().iterator();
        int sheetHeight;
        List listSprites1;

        while (var6.hasNext() && !this.skipFirst)
        {
            Entry var25 = (Entry)var6.next();
            TextureAtlasSprite var26 = (TextureAtlasSprite)var25.getValue();
            ResourceLocation var27 = new ResourceLocation(var26.getIconName());
            ResourceLocation var28 = this.completeResourceLocation(var27, 0);

            if (var26.hasCustomLoader(p_110571_1_, var27))
            {
                if (!var26.load(p_110571_1_, var27))
                {
                    var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
                    var22.addSprite(var26);
                }
            }
            else
            {
                try
                {
                    IResource var30 = p_110571_1_.getResource(var28);
                    BufferedImage[] var31 = new BufferedImage[1 + this.mipmapLevels];
                    var31[0] = TextureUtil.func_177053_a(var30.getInputStream());

                    if (this.mipmapLevels > 0 && var31 != null)
                    {
                        sheetHeight = var31[0].getWidth();
                        var31[0] = TextureUtils.scaleToPowerOfTwo(var31[0], minSpriteSize);
                        int listSprites = var31[0].getWidth();

                        if (!TextureUtils.isPowerOfTwo(sheetHeight))
                        {
                            Config.log("Scaled non power of 2: " + var26.getIconName() + ", " + sheetHeight + " -> " + listSprites);
                        }
                    }

                    TextureMetadataSection sheetHeight1 = (TextureMetadataSection)var30.getMetadata("texture");

                    if (sheetHeight1 != null)
                    {
                        listSprites1 = sheetHeight1.getListMipmaps();
                        int it;

                        if (!listSprites1.isEmpty())
                        {
                            int tas = var31[0].getWidth();
                            it = var31[0].getHeight();

                            if (MathHelper.roundUpToPowerOfTwo(tas) != tas || MathHelper.roundUpToPowerOfTwo(it) != it)
                            {
                                throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                            }
                        }

                        Iterator tas1 = listSprites1.iterator();

                        while (tas1.hasNext())
                        {
                            it = ((Integer)tas1.next()).intValue();

                            if (it > 0 && it < var31.length - 1 && var31[it] == null)
                            {
                                ResourceLocation ss = this.completeResourceLocation(var27, it);

                                try
                                {
                                    var31[it] = TextureUtil.func_177053_a(p_110571_1_.getResource(ss).getInputStream());
                                }
                                catch (IOException var251)
                                {
                                    logger.error("Unable to load miplevel {} from: {}", new Object[] {Integer.valueOf(it), ss, var251});
                                }
                            }
                        }
                    }

                    AnimationMetadataSection listSprites2 = (AnimationMetadataSection)var30.getMetadata("animation");
                    var26.func_180598_a(var31, listSprites2);
                }
                catch (RuntimeException var261)
                {
                    logger.error("Unable to parse metadata from " + var28, var261);
                    ReflectorForge.FMLClientHandler_trackBrokenTexture(var28, var261.getMessage());
                    continue;
                }
                catch (IOException var271)
                {
                    logger.error("Using missing texture, unable to load " + var28 + ", " + var271.getClass().getName());
                    ReflectorForge.FMLClientHandler_trackMissingTexture(var28);
                    continue;
                }

                var4 = Math.min(var4, Math.min(var26.getIconWidth(), var26.getIconHeight()));
                int var301 = Math.min(Integer.lowestOneBit(var26.getIconWidth()), Integer.lowestOneBit(var26.getIconHeight()));

                if (var301 < var5)
                {
                    logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] {var28, Integer.valueOf(var26.getIconWidth()), Integer.valueOf(var26.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(var5)), Integer.valueOf(MathHelper.calculateLogBaseTwo(var301))});
                    var5 = var301;
                }

                var22.addSprite(var26);
            }
        }

        int var252 = Math.min(var4, var5);
        int var262 = MathHelper.calculateLogBaseTwo(var252);

        if (var262 < 0)
        {
            var262 = 0;
        }

        if (var262 < this.mipmapLevels)
        {
            logger.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] {this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(var262), Integer.valueOf(var252)});
            this.mipmapLevels = var262;
        }

        Iterator var272 = this.mapRegisteredSprites.values().iterator();

        while (var272.hasNext() && !this.skipFirst)
        {
            final TextureAtlasSprite var281 = (TextureAtlasSprite)var272.next();

            try
            {
                var281.generateMipmaps(this.mipmapLevels);
            }
            catch (Throwable var24)
            {
                CrashReport var311 = CrashReport.makeCrashReport(var24, "Applying mipmap");
                CrashReportCategory sheetWidth = var311.makeCategory("Sprite being mipmapped");
                sheetWidth.addCrashSectionCallable("Sprite name", new Callable()
                {


                    public String call()
                    {
                        return var281.getIconName();
                    }
                });
                sheetWidth.addCrashSectionCallable("Sprite size", new Callable()
                {


                    public String call()
                    {
                        return var281.getIconWidth() + " x " + var281.getIconHeight();
                    }
                });
                sheetWidth.addCrashSectionCallable("Sprite frames", new Callable()
                {


                    public String call()
                    {
                        return var281.getFrameCount() + " frames";
                    }
                });
                sheetWidth.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
                throw new ReportedException(var311);
            }
        }

        this.missingImage.generateMipmaps(this.mipmapLevels);
        var22.addSprite(this.missingImage);
        this.skipFirst = false;

        try
        {
            var22.doStitch();
        }
        catch (StitcherException var23)
        {
            throw var23;
        }

        logger.info("Created: {}x{} {}-atlas", new Object[] {Integer.valueOf(var22.getCurrentWidth()), Integer.valueOf(var22.getCurrentHeight()), this.basePath});
        TextureUtil.func_180600_a(this.getGlTextureId(), this.mipmapLevels, var22.getCurrentWidth(), var22.getCurrentHeight());
        HashMap var282 = Maps.newHashMap(this.mapRegisteredSprites);
        Iterator var302 = var22.getStichSlots().iterator();
        TextureAtlasSprite var312;

        while (var302.hasNext())
        {
            var312 = (TextureAtlasSprite)var302.next();
            String sheetWidth1 = var312.getIconName();
            var282.remove(sheetWidth1);
            this.mapUploadedSprites.put(sheetWidth1, var312);

            try
            {
                TextureUtil.uploadTextureMipmap(var312.getFrameTextureData(0), var312.getIconWidth(), var312.getIconHeight(), var312.getOriginX(), var312.getOriginY(), false, false);
            }
            catch (Throwable throwable)
            {
                CrashReport listSprites3 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
                CrashReportCategory it1 = listSprites3.makeCategory("Texture being stitched together");
                it1.addCrashSection("Atlas path", this.basePath);
                it1.addCrashSection("Sprite", var312);
                throw new ReportedException(listSprites3);
            }

            if (var312.hasAnimationMetadata())
            {
                this.listAnimatedSprites.add(var312);
            }
        }

        var302 = var282.values().iterator();

        while (var302.hasNext())
        {
            var312 = (TextureAtlasSprite)var302.next();
            var312.copyFrom(this.missingImage);
        }

        if (Config.isMultiTexture())
        {
            int sheetWidth2 = var22.getCurrentWidth();
            sheetHeight = var22.getCurrentHeight();
            listSprites1 = var22.getStichSlots();
            Iterator it2 = listSprites1.iterator();

            while (it2.hasNext())
            {
                TextureAtlasSprite tas2 = (TextureAtlasSprite)it2.next();
                tas2.sheetWidth = sheetWidth2;
                tas2.sheetHeight = sheetHeight;
                tas2.mipmapLevels = this.mipmapLevels;
                TextureAtlasSprite ss1 = tas2.spriteSingle;

                if (ss1 != null)
                {
                    ss1.sheetWidth = sheetWidth2;
                    ss1.sheetHeight = sheetHeight;
                    ss1.mipmapLevels = this.mipmapLevels;
                    tas2.bindSpriteTexture();
                    boolean texBlur = false;
                    boolean texClamp = true;
                    TextureUtil.uploadTextureMipmap(ss1.getFrameTextureData(0), ss1.getIconWidth(), ss1.getIconHeight(), ss1.getOriginX(), ss1.getOriginY(), texBlur, texClamp);
                }
            }

            Config.getMinecraft().getTextureManager().bindTexture(locationBlocksTexture);
        }

        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] {this});

        if (Config.equals(System.getProperty("saveTextureMap"), "true"))
        {
            TextureUtil.func_177055_a(this.basePath.replaceAll("/", "_"), this.getGlTextureId(), this.mipmapLevels, var22.getCurrentWidth(), var22.getCurrentHeight());
        }
    }

    private ResourceLocation completeResourceLocation(ResourceLocation p_147634_1_, int p_147634_2_)
    {
        return this.isAbsoluteLocation(p_147634_1_) ? (p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + ".png") : new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + "mipmap" + p_147634_2_ + ".png")) : (p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] {this.basePath, p_147634_1_.getResourcePath(), ".png"})): new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] {this.basePath, p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png"})));
    }

    public TextureAtlasSprite getAtlasSprite(String p_110572_1_)
    {
        TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapUploadedSprites.get(p_110572_1_);

        if (var2 == null)
        {
            var2 = this.missingImage;
        }

        return var2;
    }

    public void updateAnimations()
    {
        TextureUtil.bindTexture(this.getGlTextureId());
        Iterator var1 = this.listAnimatedSprites.iterator();

        while (var1.hasNext())
        {
            TextureAtlasSprite it = (TextureAtlasSprite)var1.next();

            if (this.isTerrainAnimationActive(it))
            {
                it.updateAnimation();
            }
        }

        if (Config.isMultiTexture())
        {
            Iterator it1 = this.listAnimatedSprites.iterator();

            while (it1.hasNext())
            {
                TextureAtlasSprite ts = (TextureAtlasSprite)it1.next();

                if (this.isTerrainAnimationActive(ts))
                {
                    TextureAtlasSprite spriteSingle = ts.spriteSingle;

                    if (spriteSingle != null)
                    {
                        ts.bindSpriteTexture();
                        spriteSingle.updateAnimation();
                    }
                }
            }

            TextureUtil.bindTexture(this.getGlTextureId());
        }
    }

    public TextureAtlasSprite func_174942_a(ResourceLocation p_174942_1_)
    {
        if (p_174942_1_ == null)
        {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        else
        {
            TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapRegisteredSprites.get(p_174942_1_.toString());

            if (var2 == null && Reflector.ModLoader_getCustomAnimationLogic.exists())
            {
                var2 = (TextureAtlasSprite)Reflector.call(Reflector.ModLoader_getCustomAnimationLogic, new Object[] {p_174942_1_});
            }

            if (var2 == null)
            {
                var2 = TextureAtlasSprite.func_176604_a(p_174942_1_);
                this.mapRegisteredSprites.put(p_174942_1_.toString(), var2);

                if (var2 instanceof TextureAtlasSprite && var2.getIndexInMap() < 0)
                {
                    var2.setIndexInMap(this.mapRegisteredSprites.size());
                }
            }

            return var2;
        }
    }

    public void tick()
    {
        this.updateAnimations();
    }

    public void setMipmapLevels(int p_147633_1_)
    {
        this.mipmapLevels = p_147633_1_;
    }

    public TextureAtlasSprite func_174944_f()
    {
        return this.missingImage;
    }

    public TextureAtlasSprite getTextureExtry(String name)
    {
        ResourceLocation loc = new ResourceLocation(name);
        return (TextureAtlasSprite)this.mapRegisteredSprites.get(loc.toString());
    }

    public boolean setTextureEntry(String name, TextureAtlasSprite entry)
    {
        if (!this.mapRegisteredSprites.containsKey(name))
        {
            this.mapRegisteredSprites.put(name, entry);

            if (entry.getIndexInMap() < 0)
            {
                entry.setIndexInMap(this.mapRegisteredSprites.size());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isAbsoluteLocation(ResourceLocation loc)
    {
        String path = loc.getResourcePath();
        return this.isAbsoluteLocationPath(path);
    }

    private boolean isAbsoluteLocationPath(String resPath)
    {
        String path = resPath.toLowerCase();
        return path.startsWith("mcpatcher/") || path.startsWith("optifine/");
    }

    public TextureAtlasSprite getSpriteSafe(String name)
    {
        ResourceLocation loc = new ResourceLocation(name);
        return (TextureAtlasSprite)this.mapRegisteredSprites.get(loc.toString());
    }

    private boolean isTerrainAnimationActive(TextureAtlasSprite ts)
    {
        return ts != TextureUtils.iconWaterStill && ts != TextureUtils.iconWaterFlow ? (ts != TextureUtils.iconLavaStill && ts != TextureUtils.iconLavaFlow ? (ts != TextureUtils.iconFireLayer0 && ts != TextureUtils.iconFireLayer1 ? (ts == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedTerrain()) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
    }

    public int getCountRegisteredSprites()
    {
        return this.mapRegisteredSprites.size();
    }

    private int detectMaxMipmapLevel(Map mapSprites, IResourceManager rm)
    {
        int minSize = this.detectMinimumSpriteSize(mapSprites, rm, 20);

        if (minSize < 16)
        {
            minSize = 16;
        }

        minSize = MathHelper.roundUpToPowerOfTwo(minSize);

        if (minSize > 16)
        {
            Config.log("Sprite size: " + minSize);
        }

        int minLevel = MathHelper.calculateLogBaseTwo(minSize);

        if (minLevel < 4)
        {
            minLevel = 4;
        }

        return minLevel;
    }

    private int detectMinimumSpriteSize(Map mapSprites, IResourceManager rm, int percentScale)
    {
        HashMap mapSizeCounts = new HashMap();
        Set entrySetSprites = mapSprites.entrySet();
        Iterator countSprites = entrySetSprites.iterator();
        int count;

        while (countSprites.hasNext())
        {
            Entry setSizes = (Entry)countSprites.next();
            TextureAtlasSprite setSizesSorted = (TextureAtlasSprite)setSizes.getValue();
            ResourceLocation minSize = new ResourceLocation(setSizesSorted.getIconName());
            ResourceLocation countScale = this.completeResourceLocation(minSize, 0);

            if (!setSizesSorted.hasCustomLoader(rm, minSize))
            {
                try
                {
                    IResource countScaleMax = rm.getResource(countScale);

                    if (countScaleMax != null)
                    {
                        InputStream it = countScaleMax.getInputStream();

                        if (it != null)
                        {
                            Dimension size = TextureUtils.getImageSize(it, "png");

                            if (size != null)
                            {
                                count = size.width;
                                int width2 = MathHelper.roundUpToPowerOfTwo(count);

                                if (!mapSizeCounts.containsKey(Integer.valueOf(width2)))
                                {
                                    mapSizeCounts.put(Integer.valueOf(width2), Integer.valueOf(1));
                                }
                                else
                                {
                                    int count1 = ((Integer)mapSizeCounts.get(Integer.valueOf(width2))).intValue();
                                    mapSizeCounts.put(Integer.valueOf(width2), Integer.valueOf(count1 + 1));
                                }
                            }
                        }
                    }
                }
                catch (Exception var17)
                {
                    ;
                }
            }
        }

        int countSprites1 = 0;
        Set setSizes1 = mapSizeCounts.keySet();
        TreeSet setSizesSorted1 = new TreeSet(setSizes1);
        int countScale1;
        int countScaleMax1;

        for (Iterator minSize1 = setSizesSorted1.iterator(); minSize1.hasNext(); countSprites1 += countScaleMax1)
        {
            countScale1 = ((Integer)minSize1.next()).intValue();
            countScaleMax1 = ((Integer)mapSizeCounts.get(Integer.valueOf(countScale1))).intValue();
        }

        int minSize2 = 16;
        countScale1 = 0;
        countScaleMax1 = countSprites1 * percentScale / 100;
        Iterator it1 = setSizesSorted1.iterator();

        do
        {
            if (!it1.hasNext())
            {
                return minSize2;
            }

            int size1 = ((Integer)it1.next()).intValue();
            count = ((Integer)mapSizeCounts.get(Integer.valueOf(size1))).intValue();
            countScale1 += count;

            if (size1 > minSize2)
            {
                minSize2 = size1;
            }
        }
        while (countScale1 <= countScaleMax1);

        return minSize2;
    }

    private int getMinSpriteSize()
    {
        int minSize = 1 << this.mipmapLevels;

        if (minSize < 16)
        {
            minSize = 16;
        }

        return minSize;
    }

    private int[] getMissingImageData(int size)
    {
        BufferedImage bi = new BufferedImage(16, 16, 2);
        bi.setRGB(0, 0, 16, 16, TextureUtil.missingTextureData, 0, 16);
        BufferedImage bi2 = TextureUtils.scaleToPowerOfTwo(bi, size);
        int[] data = new int[size * size];
        bi2.getRGB(0, 0, size, size, data, 0, size);
        return data;
    }
}
