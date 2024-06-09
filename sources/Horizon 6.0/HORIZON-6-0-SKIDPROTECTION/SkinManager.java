package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.google.common.collect.Maps;
import java.awt.image.BufferedImage;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import com.mojang.authlib.GameProfile;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.LoadingCache;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.util.concurrent.ExecutorService;

public class SkinManager
{
    private static final ExecutorService HorizonCode_Horizon_È;
    private final TextureManager Â;
    private final File Ý;
    private final MinecraftSessionService Ø­áŒŠá;
    private final LoadingCache Âµá€;
    private static final String Ó = "CL_00001830";
    
    static {
        HorizonCode_Horizon_È = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    }
    
    public SkinManager(final TextureManager textureManagerInstance, final File skinCacheDirectory, final MinecraftSessionService sessionService) {
        this.Â = textureManagerInstance;
        this.Ý = skinCacheDirectory;
        this.Ø­áŒŠá = sessionService;
        this.Âµá€ = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build((CacheLoader)new CacheLoader() {
            private static final String Â = "CL_00001829";
            
            public Map HorizonCode_Horizon_È(final GameProfile p_152786_1_) {
                return Minecraft.áŒŠà().Ï­à().getTextures(p_152786_1_, false);
            }
            
            public Object load(final Object p_load_1_) {
                return this.HorizonCode_Horizon_È((GameProfile)p_load_1_);
            }
        });
    }
    
    public ResourceLocation_1975012498 HorizonCode_Horizon_È(final MinecraftProfileTexture p_152792_1_, final MinecraftProfileTexture.Type p_152792_2_) {
        return this.HorizonCode_Horizon_È(p_152792_1_, p_152792_2_, null);
    }
    
    public ResourceLocation_1975012498 HorizonCode_Horizon_È(final MinecraftProfileTexture p_152789_1_, final MinecraftProfileTexture.Type p_152789_2_, final HorizonCode_Horizon_È p_152789_3_) {
        final ResourceLocation_1975012498 var4 = new ResourceLocation_1975012498("skins/" + p_152789_1_.getHash());
        final ITextureObject var5 = this.Â.Â(var4);
        if (var5 != null) {
            if (p_152789_3_ != null) {
                p_152789_3_.HorizonCode_Horizon_È(p_152789_2_, var4, p_152789_1_);
            }
        }
        else {
            final File var6 = new File(this.Ý, p_152789_1_.getHash().substring(0, 2));
            final File var7 = new File(var6, p_152789_1_.getHash());
            final ImageBufferDownload var8 = (p_152789_2_ == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null;
            final ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, p_152789_1_.getUrl(), DefaultPlayerSkin.HorizonCode_Horizon_È(), new IImageBuffer() {
                private static final String Â = "CL_00001828";
                
                @Override
                public BufferedImage HorizonCode_Horizon_È(BufferedImage p_78432_1_) {
                    if (var8 != null) {
                        p_78432_1_ = var8.HorizonCode_Horizon_È(p_78432_1_);
                    }
                    return p_78432_1_;
                }
                
                @Override
                public void HorizonCode_Horizon_È() {
                    if (var8 != null) {
                        var8.HorizonCode_Horizon_È();
                    }
                    if (p_152789_3_ != null) {
                        p_152789_3_.HorizonCode_Horizon_È(p_152789_2_, var4, p_152789_1_);
                    }
                }
            });
            this.Â.HorizonCode_Horizon_È(var4, var9);
        }
        return var4;
    }
    
    public void HorizonCode_Horizon_È(final GameProfile p_152790_1_, final HorizonCode_Horizon_È p_152790_2_, final boolean p_152790_3_) {
        SkinManager.HorizonCode_Horizon_È.submit(new Runnable() {
            private static final String Â = "CL_00001827";
            
            @Override
            public void run() {
                final HashMap var1 = Maps.newHashMap();
                try {
                    var1.putAll(SkinManager.this.Ø­áŒŠá.getTextures(p_152790_1_, p_152790_3_));
                }
                catch (InsecureTextureException ex) {}
                if (var1.isEmpty() && p_152790_1_.getId().equals(Minecraft.áŒŠà().Õ().Âµá€().getId())) {
                    var1.putAll(SkinManager.this.Ø­áŒŠá.getTextures(SkinManager.this.Ø­áŒŠá.fillProfileProperties(p_152790_1_, false), false));
                }
                Minecraft.áŒŠà().HorizonCode_Horizon_È(new Runnable() {
                    private static final String Â = "CL_00001826";
                    
                    @Override
                    public void run() {
                        if (var1.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                            SkinManager.this.HorizonCode_Horizon_È(var1.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, p_152790_2_);
                        }
                        if (var1.containsKey(MinecraftProfileTexture.Type.CAPE)) {
                            SkinManager.this.HorizonCode_Horizon_È(var1.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, p_152790_2_);
                        }
                    }
                });
            }
        });
    }
    
    public Map HorizonCode_Horizon_È(final GameProfile p_152788_1_) {
        return (Map)this.Âµá€.getUnchecked((Object)p_152788_1_);
    }
    
    public interface HorizonCode_Horizon_È
    {
        void HorizonCode_Horizon_È(final MinecraftProfileTexture.Type p0, final ResourceLocation_1975012498 p1, final MinecraftProfileTexture p2);
    }
}
