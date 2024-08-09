/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class PreloadedTexture
extends SimpleTexture {
    @Nullable
    private CompletableFuture<SimpleTexture.TextureData> textureDataFuture;

    public PreloadedTexture(IResourceManager iResourceManager, ResourceLocation resourceLocation, Executor executor) {
        super(resourceLocation);
        this.textureDataFuture = CompletableFuture.supplyAsync(() -> PreloadedTexture.lambda$new$0(iResourceManager, resourceLocation), executor);
    }

    @Override
    protected SimpleTexture.TextureData getTextureData(IResourceManager iResourceManager) {
        if (this.textureDataFuture != null) {
            SimpleTexture.TextureData textureData = this.textureDataFuture.join();
            this.textureDataFuture = null;
            return textureData;
        }
        return SimpleTexture.TextureData.getTextureData(iResourceManager, this.textureLocation);
    }

    public CompletableFuture<Void> getCompletableFuture() {
        return this.textureDataFuture == null ? CompletableFuture.completedFuture(null) : this.textureDataFuture.thenApply(PreloadedTexture::lambda$getCompletableFuture$1);
    }

    @Override
    public void loadTexture(TextureManager textureManager, IResourceManager iResourceManager, ResourceLocation resourceLocation, Executor executor) {
        this.textureDataFuture = CompletableFuture.supplyAsync(() -> this.lambda$loadTexture$2(iResourceManager), Util.getServerExecutor());
        this.textureDataFuture.thenRunAsync(() -> this.lambda$loadTexture$3(textureManager), PreloadedTexture.getExecutor(executor));
    }

    private static Executor getExecutor(Executor executor) {
        return arg_0 -> PreloadedTexture.lambda$getExecutor$5(executor, arg_0);
    }

    private static void lambda$getExecutor$5(Executor executor, Runnable runnable) {
        executor.execute(() -> PreloadedTexture.lambda$getExecutor$4(runnable));
    }

    private static void lambda$getExecutor$4(Runnable runnable) {
        RenderSystem.recordRenderCall(runnable::run);
    }

    private void lambda$loadTexture$3(TextureManager textureManager) {
        textureManager.loadTexture(this.textureLocation, this);
    }

    private SimpleTexture.TextureData lambda$loadTexture$2(IResourceManager iResourceManager) {
        return SimpleTexture.TextureData.getTextureData(iResourceManager, this.textureLocation);
    }

    private static Void lambda$getCompletableFuture$1(SimpleTexture.TextureData textureData) {
        return null;
    }

    private static SimpleTexture.TextureData lambda$new$0(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        return SimpleTexture.TextureData.getTextureData(iResourceManager, resourceLocation);
    }
}

