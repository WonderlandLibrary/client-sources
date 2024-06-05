package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import optifine.*;
import shadersmod.client.*;
import java.io.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class TextureManager implements ITickable, IResourceManagerReloadListener
{
    private static final Logger logger;
    private final Map mapTextureObjects;
    private final List listTickables;
    private final Map mapTextureCounters;
    private IResourceManager theResourceManager;
    
    public TextureManager(final IResourceManager p_i1284_1_) {
        this.mapTextureObjects = Maps.newHashMap();
        this.listTickables = Lists.newArrayList();
        this.mapTextureCounters = Maps.newHashMap();
        this.theResourceManager = p_i1284_1_;
    }
    
    public void bindTexture(ResourceLocation resource) {
        if (Config.isRandomMobs()) {
            resource = RandomMobs.getTextureLocation(resource);
        }
        Object var2 = this.mapTextureObjects.get(resource);
        if (var2 == null) {
            var2 = new SimpleTexture(resource);
            this.loadTexture(resource, (ITextureObject)var2);
        }
        if (Config.isShaders()) {
            ShadersTex.bindTexture((ITextureObject)var2);
        }
        else {
            TextureUtil.bindTexture(((ITextureObject)var2).getGlTextureId());
        }
    }
    
    public boolean loadTickableTexture(final ResourceLocation p_110580_1_, final ITickableTextureObject p_110580_2_) {
        if (this.loadTexture(p_110580_1_, p_110580_2_)) {
            this.listTickables.add(p_110580_2_);
            return true;
        }
        return false;
    }
    
    public boolean loadTexture(final ResourceLocation p_110579_1_, final ITextureObject p_110579_2_) {
        boolean var3 = true;
        Object p_110579_2_2 = p_110579_2_;
        try {
            p_110579_2_.loadTexture(this.theResourceManager);
        }
        catch (IOException var4) {
            TextureManager.logger.warn("Failed to load texture: " + p_110579_1_, (Throwable)var4);
            p_110579_2_2 = TextureUtil.missingTexture;
            this.mapTextureObjects.put(p_110579_1_, p_110579_2_2);
            var3 = false;
        }
        catch (Throwable var6) {
            final CrashReport var5 = CrashReport.makeCrashReport(var6, "Registering texture");
            final CrashReportCategory var7 = var5.makeCategory("Resource location being registered");
            var7.addCrashSection("Resource location", p_110579_1_);
            var7.addCrashSectionCallable("Texture object class", new Callable() {
                @Override
                public String call() {
                    return p_110579_2_.getClass().getName();
                }
            });
            throw new ReportedException(var5);
        }
        this.mapTextureObjects.put(p_110579_1_, p_110579_2_2);
        return var3;
    }
    
    public ITextureObject getTexture(final ResourceLocation p_110581_1_) {
        return this.mapTextureObjects.get(p_110581_1_);
    }
    
    public ResourceLocation getDynamicTextureLocation(final String p_110578_1_, DynamicTexture p_110578_2_) {
        if (p_110578_1_.equals("logo")) {
            p_110578_2_ = Config.getMojangLogoTexture(p_110578_2_);
        }
        Integer var3 = this.mapTextureCounters.get(p_110578_1_);
        if (var3 == null) {
            var3 = 1;
        }
        else {
            ++var3;
        }
        this.mapTextureCounters.put(p_110578_1_, var3);
        final ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", p_110578_1_, var3));
        this.loadTexture(var4, p_110578_2_);
        return var4;
    }
    
    @Override
    public void tick() {
        for (final ITickable var2 : this.listTickables) {
            var2.tick();
        }
    }
    
    public void deleteTexture(final ResourceLocation p_147645_1_) {
        final ITextureObject var2 = this.getTexture(p_147645_1_);
        if (var2 != null) {
            TextureUtil.deleteTexture(var2.getGlTextureId());
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        Config.dbg("*** Reloading textures ***");
        Config.log("Resource packs: " + Config.getResourcePackNames());
        final Iterator it = this.mapTextureObjects.keySet().iterator();
        while (it.hasNext()) {
            final ResourceLocation var2 = it.next();
            if (var2.getResourcePath().startsWith("mcpatcher/")) {
                final ITextureObject var3 = this.mapTextureObjects.get(var2);
                if (var3 instanceof AbstractTexture) {
                    final AbstractTexture at = (AbstractTexture)var3;
                    at.deleteGlTexture();
                }
                it.remove();
            }
        }
        for (final Map.Entry var5 : this.mapTextureObjects.entrySet()) {
            this.loadTexture(var5.getKey(), var5.getValue());
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
