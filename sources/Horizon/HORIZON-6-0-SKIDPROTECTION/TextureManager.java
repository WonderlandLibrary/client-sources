package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.io.IOException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger HorizonCode_Horizon_È;
    private final Map Â;
    private final List Ý;
    private final Map Ø­áŒŠá;
    private IResourceManager Âµá€;
    private static final String Ó = "CL_00001064";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public TextureManager(final IResourceManager p_i1284_1_) {
        this.Â = Maps.newHashMap();
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Âµá€ = p_i1284_1_;
    }
    
    public void HorizonCode_Horizon_È(ResourceLocation_1975012498 resource) {
        if (Config.áˆºÇŽØ()) {
            resource = RandomMobs.HorizonCode_Horizon_È(resource);
        }
        Object var2 = this.Â.get(resource);
        if (var2 == null) {
            var2 = new SimpleTexture(resource);
            this.HorizonCode_Horizon_È(resource, (ITextureObject)var2);
        }
        TextureUtil.Â(((ITextureObject)var2).HorizonCode_Horizon_È());
    }
    
    public boolean HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110580_1_, final ITickableTextureObject p_110580_2_) {
        if (this.HorizonCode_Horizon_È(p_110580_1_, (ITextureObject)p_110580_2_)) {
            this.Ý.add(p_110580_2_);
            return true;
        }
        return false;
    }
    
    public boolean HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_110579_1_, final ITextureObject p_110579_2_) {
        boolean var3 = true;
        Object p_110579_2_2 = p_110579_2_;
        try {
            p_110579_2_.HorizonCode_Horizon_È(this.Âµá€);
        }
        catch (IOException var4) {
            TextureManager.HorizonCode_Horizon_È.warn("Failed to load texture: " + p_110579_1_, (Throwable)var4);
            p_110579_2_2 = TextureUtil.HorizonCode_Horizon_È;
            this.Â.put(p_110579_1_, p_110579_2_2);
            var3 = false;
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.HorizonCode_Horizon_È(var6, "Registering texture");
            final CrashReportCategory var7 = var5.HorizonCode_Horizon_È("Resource location being registered");
            var7.HorizonCode_Horizon_È("Resource location", p_110579_1_);
            var7.HorizonCode_Horizon_È("Texture object class", new Callable() {
                private static final String Â = "CL_00001065";
                
                public String HorizonCode_Horizon_È() {
                    return p_110579_2_.getClass().getName();
                }
            });
            throw new ReportedException(var5);
        }
        this.Â.put(p_110579_1_, p_110579_2_2);
        return var3;
    }
    
    public ITextureObject Â(final ResourceLocation_1975012498 p_110581_1_) {
        return this.Â.get(p_110581_1_);
    }
    
    public ResourceLocation_1975012498 HorizonCode_Horizon_È(final String p_110578_1_, final DynamicTexture p_110578_2_) {
        Integer var3 = this.Ø­áŒŠá.get(p_110578_1_);
        if (var3 == null) {
            var3 = 1;
        }
        else {
            ++var3;
        }
        this.Ø­áŒŠá.put(p_110578_1_, var3);
        final ResourceLocation_1975012498 var4 = new ResourceLocation_1975012498(String.format("dynamic/%s_%d", p_110578_1_, var3));
        this.HorizonCode_Horizon_È(var4, p_110578_2_);
        return var4;
    }
    
    @Override
    public void Â() {
        for (final ITickable var2 : this.Ý) {
            var2.Â();
        }
    }
    
    public void Ý(final ResourceLocation_1975012498 p_147645_1_) {
        final ITextureObject var2 = this.Â(p_147645_1_);
        if (var2 != null) {
            TextureUtil.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager resourceManager) {
        Config.HorizonCode_Horizon_È("*** Reloading textures ***");
        Config.Ø­áŒŠá("Resource packs: " + Config.£ÂµÄ());
        final Iterator it = this.Â.keySet().iterator();
        while (it.hasNext()) {
            final ResourceLocation_1975012498 var2 = it.next();
            if (var2.Â().startsWith("mcpatcher/")) {
                final ITextureObject var3 = this.Â.get(var2);
                final int glTexId = var3.HorizonCode_Horizon_È();
                if (glTexId > 0) {
                    GL11.glDeleteTextures(glTexId);
                }
                it.remove();
            }
        }
        for (final Map.Entry var5 : this.Â.entrySet()) {
            this.HorizonCode_Horizon_È(var5.getKey(), var5.getValue());
        }
    }
}
