package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.Map;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class TextureMap extends AbstractTexture implements ITickableTextureObject
{
    private static final Logger Ø;
    public static final ResourceLocation_1975012498 Ó;
    public static final ResourceLocation_1975012498 à;
    private final List áŒŠÆ;
    private final Map áˆºÑ¢Õ;
    private final Map ÂµÈ;
    private final String á;
    private final IIconCreator ˆÏ­;
    private int £á;
    private final TextureAtlasSprite Å;
    private static final String £à = "CL_00001058";
    
    static {
        Ø = LogManager.getLogger();
        Ó = new ResourceLocation_1975012498("missingno");
        à = new ResourceLocation_1975012498("textures/atlas/blocks.png");
    }
    
    public TextureMap(final String p_i46099_1_) {
        this(p_i46099_1_, null);
    }
    
    public TextureMap(final String p_i46100_1_, final IIconCreator p_i46100_2_) {
        this.áŒŠÆ = Lists.newArrayList();
        this.áˆºÑ¢Õ = Maps.newHashMap();
        this.ÂµÈ = Maps.newHashMap();
        this.Å = new TextureAtlasSprite("missingno");
        this.á = p_i46100_1_;
        this.ˆÏ­ = p_i46100_2_;
    }
    
    private void Ø() {
        final int[] var1 = TextureUtil.Â;
        this.Å.Â(16);
        this.Å.Ý(16);
        final int[][] var2 = new int[this.£á + 1][];
        var2[0] = var1;
        this.Å.HorizonCode_Horizon_È(Lists.newArrayList((Object[])new int[][][] { var2 }));
        this.Å.Âµá€(0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
        if (this.ˆÏ­ != null) {
            this.HorizonCode_Horizon_È(p_110551_1_, this.ˆÏ­);
        }
    }
    
    public void HorizonCode_Horizon_È(final IResourceManager p_174943_1_, final IIconCreator p_174943_2_) {
        this.áˆºÑ¢Õ.clear();
        p_174943_2_.HorizonCode_Horizon_È(this);
        this.Ø();
        this.Âµá€();
        ConnectedTextures.HorizonCode_Horizon_È(this);
        this.Â(p_174943_1_);
    }
    
    public void Â(final IResourceManager p_110571_1_) {
        final int var2 = Minecraft.Ñ¢á();
        final Stitcher var3 = new Stitcher(var2, var2, true, 0, this.£á);
        this.ÂµÈ.clear();
        this.áŒŠÆ.clear();
        int var4 = Integer.MAX_VALUE;
        Reflector.HorizonCode_Horizon_È(Reflector.Õ, this);
        int var5 = 1 << this.£á;
        for (final Map.Entry var7 : this.áˆºÑ¢Õ.entrySet()) {
            final TextureAtlasSprite var8 = var7.getValue();
            final ResourceLocation_1975012498 var9 = new ResourceLocation_1975012498(var8.áŒŠÆ());
            final ResourceLocation_1975012498 var10 = this.HorizonCode_Horizon_È(var9, 0);
            if (!var8.HorizonCode_Horizon_È(p_110571_1_, var9)) {
                try {
                    final IResource var11 = p_110571_1_.HorizonCode_Horizon_È(var10);
                    final BufferedImage[] var12 = new BufferedImage[1 + this.£á];
                    var12[0] = TextureUtil.HorizonCode_Horizon_È(var11.Â());
                    final TextureMetadataSection var13 = (TextureMetadataSection)var11.HorizonCode_Horizon_È("texture");
                    if (var13 != null) {
                        final List var14 = var13.Ý();
                        if (!var14.isEmpty()) {
                            final int var15 = var12[0].getWidth();
                            final int var16 = var12[0].getHeight();
                            if (MathHelper.Â(var15) != var15 || MathHelper.Â(var16) != var16) {
                                throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                            }
                        }
                        final Iterator var17 = var14.iterator();
                        while (var17.hasNext()) {
                            final int var16 = var17.next();
                            if (var16 > 0 && var16 < var12.length - 1 && var12[var16] == null) {
                                final ResourceLocation_1975012498 var18 = this.HorizonCode_Horizon_È(var9, var16);
                                try {
                                    var12[var16] = TextureUtil.HorizonCode_Horizon_È(p_110571_1_.HorizonCode_Horizon_È(var18).Â());
                                }
                                catch (IOException var19) {
                                    TextureMap.Ø.error("Unable to load miplevel {} from: {}", new Object[] { var16, var18, var19 });
                                }
                            }
                        }
                    }
                    final AnimationMetadataSection var20 = (AnimationMetadataSection)var11.HorizonCode_Horizon_È("animation");
                    var8.HorizonCode_Horizon_È(var12, var20);
                }
                catch (RuntimeException var21) {
                    TextureMap.Ø.error("Unable to parse metadata from " + var10, (Throwable)var21);
                    continue;
                }
                catch (IOException var22) {
                    TextureMap.Ø.error("Using missing texture, unable to load " + var10 + ", " + var22.getClass().getName());
                    continue;
                }
                var4 = Math.min(var4, Math.min(var8.Ý(), var8.Ø­áŒŠá()));
                final int var23 = Math.min(Integer.lowestOneBit(var8.Ý()), Integer.lowestOneBit(var8.Ø­áŒŠá()));
                if (var23 < var5) {
                    TextureMap.Ø.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { var10, var8.Ý(), var8.Ø­áŒŠá(), MathHelper.Ý(var5), MathHelper.Ý(var23) });
                    var5 = var23;
                }
                var3.HorizonCode_Horizon_È(var8);
            }
            else {
                if (var8.Â(p_110571_1_, var9)) {
                    continue;
                }
                var4 = Math.min(var4, Math.min(var8.Ý(), var8.Ø­áŒŠá()));
                var3.HorizonCode_Horizon_È(var8);
            }
        }
        final int var24 = Math.min(var4, var5);
        int var25 = MathHelper.Ý(var24);
        if (var25 < 0) {
            var25 = 0;
        }
        if (var25 < this.£á) {
            TextureMap.Ø.info("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.á, this.£á, var25, var24 });
            this.£á = var25;
        }
        for (final TextureAtlasSprite var27 : this.áˆºÑ¢Õ.values()) {
            try {
                var27.Ø­áŒŠá(this.£á);
            }
            catch (Throwable var29) {
                final CrashReport var28 = CrashReport.HorizonCode_Horizon_È(var29, "Applying mipmap");
                final CrashReportCategory var30 = var28.HorizonCode_Horizon_È("Sprite being mipmapped");
                var30.HorizonCode_Horizon_È("Sprite name", new Callable() {
                    private static final String Â = "CL_00001059";
                    
                    public String HorizonCode_Horizon_È() {
                        return var27.áŒŠÆ();
                    }
                });
                var30.HorizonCode_Horizon_È("Sprite size", new Callable() {
                    private static final String Â = "CL_00001060";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(var27.Ý()) + " x " + var27.Ø­áŒŠá();
                    }
                });
                var30.HorizonCode_Horizon_È("Sprite frames", new Callable() {
                    private static final String Â = "CL_00001061";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(var27.ÂµÈ()) + " frames";
                    }
                });
                var30.HorizonCode_Horizon_È("Mipmap levels", this.£á);
                throw new ReportedException(var28);
            }
        }
        this.Å.Ø­áŒŠá(this.£á);
        var3.HorizonCode_Horizon_È(this.Å);
        try {
            var3.Ý();
        }
        catch (StitcherException var31) {
            throw var31;
        }
        TextureMap.Ø.info("Created: {}x{} {}-atlas", new Object[] { var3.HorizonCode_Horizon_È(), var3.Â(), this.á });
        TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), this.£á, var3.HorizonCode_Horizon_È(), var3.Â());
        final HashMap var32 = Maps.newHashMap(this.áˆºÑ¢Õ);
        for (final TextureAtlasSprite var34 : var3.Ø­áŒŠá()) {
            final String var35 = var34.áŒŠÆ();
            var32.remove(var35);
            this.ÂµÈ.put(var35, var34);
            try {
                TextureUtil.HorizonCode_Horizon_È(var34.HorizonCode_Horizon_È(0), var34.Ý(), var34.Ø­áŒŠá(), var34.HorizonCode_Horizon_È(), var34.Â(), false, false);
            }
            catch (Throwable var37) {
                final CrashReport var36 = CrashReport.HorizonCode_Horizon_È(var37, "Stitching texture atlas");
                final CrashReportCategory var38 = var36.HorizonCode_Horizon_È("Texture being stitched together");
                var38.HorizonCode_Horizon_È("Atlas path", this.á);
                var38.HorizonCode_Horizon_È("Sprite", var34);
                throw new ReportedException(var36);
            }
            if (var34.ˆÏ­()) {
                this.áŒŠÆ.add(var34);
            }
        }
        for (final TextureAtlasSprite var34 : var32.values()) {
            var34.HorizonCode_Horizon_È(this.Å);
        }
        TextureUtil.HorizonCode_Horizon_È(this.á.replaceAll("/", "_"), this.HorizonCode_Horizon_È(), this.£á, var3.HorizonCode_Horizon_È(), var3.Â());
        Reflector.HorizonCode_Horizon_È(Reflector.à¢, this);
    }
    
    private ResourceLocation_1975012498 HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_147634_1_, final int p_147634_2_) {
        return this.Â(p_147634_1_) ? ((p_147634_2_ == 0) ? new ResourceLocation_1975012498(p_147634_1_.Ý(), String.valueOf(p_147634_1_.Â()) + ".png") : new ResourceLocation_1975012498(p_147634_1_.Ý(), String.valueOf(p_147634_1_.Â()) + "mipmap" + p_147634_2_ + ".png")) : ((p_147634_2_ == 0) ? new ResourceLocation_1975012498(p_147634_1_.Ý(), String.format("%s/%s%s", this.á, p_147634_1_.Â(), ".png")) : new ResourceLocation_1975012498(p_147634_1_.Ý(), String.format("%s/mipmaps/%s.%d%s", this.á, p_147634_1_.Â(), p_147634_2_, ".png")));
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È(final String p_110572_1_) {
        TextureAtlasSprite var2 = this.ÂµÈ.get(p_110572_1_);
        if (var2 == null) {
            var2 = this.Å;
        }
        return var2;
    }
    
    public void Ý() {
        TextureUtil.Â(this.HorizonCode_Horizon_È());
        for (final TextureAtlasSprite var2 : this.áŒŠÆ) {
            if (this.HorizonCode_Horizon_È(var2)) {
                var2.áˆºÑ¢Õ();
            }
        }
    }
    
    public TextureAtlasSprite HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_174942_1_) {
        if (p_174942_1_ == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        TextureAtlasSprite var2 = this.áˆºÑ¢Õ.get(p_174942_1_.toString());
        if (var2 == null && Reflector.Ó.Â()) {
            var2 = (TextureAtlasSprite)Reflector.Ó(Reflector.Ó, p_174942_1_);
        }
        if (var2 == null) {
            var2 = TextureAtlasSprite.HorizonCode_Horizon_È(p_174942_1_);
            this.áˆºÑ¢Õ.put(p_174942_1_.toString(), var2);
            if (var2 instanceof TextureAtlasSprite && var2.£á() < 0) {
                var2.Âµá€(this.áˆºÑ¢Õ.size());
            }
        }
        return var2;
    }
    
    @Override
    public void Â() {
        this.Ý();
    }
    
    public void HorizonCode_Horizon_È(final int p_147633_1_) {
        this.£á = p_147633_1_;
    }
    
    public TextureAtlasSprite Ó() {
        return this.Å;
    }
    
    public TextureAtlasSprite Â(final String name) {
        final ResourceLocation_1975012498 loc = new ResourceLocation_1975012498(name);
        return this.áˆºÑ¢Õ.get(loc.toString());
    }
    
    public boolean HorizonCode_Horizon_È(final String name, final TextureAtlasSprite entry) {
        if (!this.áˆºÑ¢Õ.containsKey(name)) {
            this.áˆºÑ¢Õ.put(name, entry);
            if (entry.£á() < 0) {
                entry.Âµá€(this.áˆºÑ¢Õ.size());
            }
            return true;
        }
        return false;
    }
    
    private boolean Â(final ResourceLocation_1975012498 loc) {
        final String path = loc.Â();
        return this.Ø­áŒŠá(path);
    }
    
    private boolean Ø­áŒŠá(final String resPath) {
        final String path = resPath.toLowerCase();
        return path.startsWith("mcpatcher/") || path.startsWith("optifine/");
    }
    
    public TextureAtlasSprite Ý(final String name) {
        final ResourceLocation_1975012498 loc = new ResourceLocation_1975012498(name);
        return this.áˆºÑ¢Õ.get(loc.toString());
    }
    
    private boolean HorizonCode_Horizon_È(final TextureAtlasSprite ts) {
        return (ts != TextureUtils.Ø­á && ts != TextureUtils.ˆÉ) ? ((ts != TextureUtils.Ï­Ï­Ï && ts != TextureUtils.£Â) ? ((ts != TextureUtils.ˆÐƒØ­à && ts != TextureUtils.£Õ) ? ((ts == TextureUtils.£Ó) ? Config.Çªà¢() : Config.Œà()) : Config.ˆá()) : Config.Ê()) : Config.Ñ¢á();
    }
    
    public int à() {
        return this.áˆºÑ¢Õ.size();
    }
}
