/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.PreloadedTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomGuis;
import net.optifine.EmissiveTextures;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager
implements IFutureReloadListener,
ITickable,
AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ResourceLocation RESOURCE_LOCATION_EMPTY = new ResourceLocation("");
    private final Map<ResourceLocation, Texture> mapTextureObjects = Maps.newHashMap();
    private final Set<ITickable> listTickables = Sets.newHashSet();
    private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
    private final IResourceManager resourceManager;
    private Texture boundTexture;
    private ResourceLocation boundTextureLocation;

    public TextureManager(IResourceManager iResourceManager) {
        this.resourceManager = iResourceManager;
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$bindTexture$0(resourceLocation));
        } else {
            this.bindTextureRaw(resourceLocation);
        }
    }

    private void bindTextureRaw(ResourceLocation resourceLocation) {
        Texture texture;
        if (Config.isCustomGuis()) {
            resourceLocation = CustomGuis.getTextureLocation(resourceLocation);
        }
        if ((texture = this.mapTextureObjects.get(resourceLocation)) == null) {
            texture = new SimpleTexture(resourceLocation);
            this.loadTexture(resourceLocation, texture);
        }
        if (Config.isShaders()) {
            ShadersTex.bindTexture(texture);
        } else {
            texture.bindTexture();
        }
        this.boundTexture = texture;
        this.boundTextureLocation = resourceLocation;
    }

    public void loadTexture(ResourceLocation resourceLocation, Texture texture) {
        Texture texture2 = this.mapTextureObjects.put(resourceLocation, texture = this.func_230183_b_(resourceLocation, texture));
        if (texture2 != texture) {
            if (texture2 != null && texture2 != MissingTextureSprite.getDynamicTexture()) {
                this.listTickables.remove(texture2);
                this.func_243505_b(resourceLocation, texture2);
            }
            if (texture instanceof ITickable) {
                this.listTickables.add((ITickable)((Object)texture));
            }
        }
    }

    private void func_243505_b(ResourceLocation resourceLocation, Texture texture) {
        if (texture != MissingTextureSprite.getDynamicTexture()) {
            try {
                texture.close();
            } catch (Exception exception) {
                LOGGER.warn("Failed to close texture {}", (Object)resourceLocation, (Object)exception);
            }
        }
        texture.deleteGlTexture();
    }

    private Texture func_230183_b_(ResourceLocation resourceLocation, Texture texture) {
        try {
            texture.loadTexture(this.resourceManager);
            return texture;
        } catch (IOException iOException) {
            if (resourceLocation != RESOURCE_LOCATION_EMPTY) {
                LOGGER.warn("Failed to load texture: {}", (Object)resourceLocation, (Object)iOException);
            }
            return MissingTextureSprite.getDynamicTexture();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Registering texture");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Resource location being registered");
            crashReportCategory.addDetail("Resource location", resourceLocation);
            crashReportCategory.addDetail("Texture object class", () -> TextureManager.lambda$func_230183_b_$1(texture));
            throw new ReportedException(crashReport);
        }
    }

    @Nullable
    public Texture getTexture(ResourceLocation resourceLocation) {
        return this.mapTextureObjects.get(resourceLocation);
    }

    public ResourceLocation getDynamicTextureLocation(String string, DynamicTexture dynamicTexture) {
        Integer n = this.mapTextureCounters.get(string);
        n = n == null ? Integer.valueOf(1) : Integer.valueOf(n + 1);
        this.mapTextureCounters.put(string, n);
        ResourceLocation resourceLocation = new ResourceLocation(String.format("dynamic/%s_%d", string, n));
        this.loadTexture(resourceLocation, dynamicTexture);
        return resourceLocation;
    }

    public CompletableFuture<Void> loadAsync(ResourceLocation resourceLocation, Executor executor) {
        if (!this.mapTextureObjects.containsKey(resourceLocation)) {
            PreloadedTexture preloadedTexture = new PreloadedTexture(this.resourceManager, resourceLocation, executor);
            this.mapTextureObjects.put(resourceLocation, preloadedTexture);
            return preloadedTexture.getCompletableFuture().thenRunAsync(() -> this.lambda$loadAsync$2(resourceLocation, preloadedTexture), TextureManager::execute);
        }
        return CompletableFuture.completedFuture(null);
    }

    private static void execute(Runnable runnable) {
        Minecraft.getInstance().execute(() -> TextureManager.lambda$execute$3(runnable));
    }

    @Override
    public void tick() {
        for (ITickable iTickable : this.listTickables) {
            iTickable.tick();
        }
    }

    public void deleteTexture(ResourceLocation resourceLocation) {
        Texture texture = this.getTexture(resourceLocation);
        if (texture != null) {
            this.mapTextureObjects.remove(resourceLocation);
            TextureUtil.releaseTextureId(texture.getGlTextureId());
        }
    }

    @Override
    public void close() {
        this.mapTextureObjects.forEach(this::func_243505_b);
        this.mapTextureObjects.clear();
        this.listTickables.clear();
        this.mapTextureCounters.clear();
    }

    @Override
    public CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        Config.dbg("*** Reloading textures ***");
        Config.log("Resource packs: " + Config.getResourcePackNames());
        Iterator<ResourceLocation> iterator2 = this.mapTextureObjects.keySet().iterator();
        while (iterator2.hasNext()) {
            ResourceLocation resourceLocation = iterator2.next();
            String string = resourceLocation.getPath();
            if (!string.startsWith("optifine/") && !EmissiveTextures.isEmissive(resourceLocation)) continue;
            Texture texture = this.mapTextureObjects.get(resourceLocation);
            if (texture instanceof Texture) {
                texture.deleteGlTexture();
            }
            iterator2.remove();
        }
        EmissiveTextures.update();
        return ((CompletableFuture)CompletableFuture.allOf(MainMenuScreen.loadAsync(this, executor), this.loadAsync(Widget.WIDGETS_LOCATION, executor)).thenCompose(iStage::markCompleteAwaitingOthers)).thenAcceptAsync(arg_0 -> this.lambda$reload$4(iResourceManager, executor2, arg_0), TextureManager::lambda$reload$5);
    }

    public Texture getBoundTexture() {
        return this.boundTexture;
    }

    public ResourceLocation getBoundTextureLocation() {
        return this.boundTextureLocation;
    }

    private static void lambda$reload$5(Runnable runnable) {
        RenderSystem.recordRenderCall(runnable::run);
    }

    private void lambda$reload$4(IResourceManager iResourceManager, Executor executor, Void void_) {
        MissingTextureSprite.getDynamicTexture();
        RealmsMainScreen.func_227932_a_(this.resourceManager);
        HashSet<Map.Entry<ResourceLocation, Texture>> hashSet = new HashSet<Map.Entry<ResourceLocation, Texture>>(this.mapTextureObjects.entrySet());
        Iterator iterator2 = hashSet.iterator();
        while (iterator2.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator2.next();
            ResourceLocation resourceLocation = (ResourceLocation)entry.getKey();
            Texture texture = (Texture)entry.getValue();
            if (texture == MissingTextureSprite.getDynamicTexture() && !resourceLocation.equals(MissingTextureSprite.getLocation())) {
                iterator2.remove();
                continue;
            }
            texture.loadTexture(this, iResourceManager, resourceLocation, executor);
        }
    }

    private static void lambda$execute$3(Runnable runnable) {
        RenderSystem.recordRenderCall(runnable::run);
    }

    private void lambda$loadAsync$2(ResourceLocation resourceLocation, PreloadedTexture preloadedTexture) {
        this.loadTexture(resourceLocation, preloadedTexture);
    }

    private static String lambda$func_230183_b_$1(Texture texture) throws Exception {
        return texture.getClass().getName();
    }

    private void lambda$bindTexture$0(ResourceLocation resourceLocation) {
        this.bindTextureRaw(resourceLocation);
    }
}

