/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.optifine.Config;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicTexture
extends Texture {
    private static final Logger field_243504_d = LogManager.getLogger();
    @Nullable
    private NativeImage dynamicTextureData;

    public DynamicTexture(NativeImage nativeImage) {
        this.dynamicTextureData = nativeImage;
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::lambda$new$0);
        } else {
            TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
            this.updateDynamicTexture();
            if (Config.isShaders()) {
                ShadersTex.initDynamicTextureNS(this);
            }
        }
    }

    public DynamicTexture(int n, int n2, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        this.dynamicTextureData = new NativeImage(n, n2, bl);
        TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
        if (Config.isShaders()) {
            ShadersTex.initDynamicTextureNS(this);
        }
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) {
    }

    public void updateDynamicTexture() {
        if (this.dynamicTextureData != null) {
            this.bindTexture();
            this.dynamicTextureData.uploadTextureSub(0, 0, 0, true);
        } else {
            field_243504_d.warn("Trying to upload disposed texture {}", (Object)this.getGlTextureId());
        }
    }

    @Nullable
    public NativeImage getTextureData() {
        return this.dynamicTextureData;
    }

    public void setTextureData(NativeImage nativeImage) {
        if (this.dynamicTextureData != null) {
            this.dynamicTextureData.close();
        }
        this.dynamicTextureData = nativeImage;
    }

    @Override
    public void close() {
        if (this.dynamicTextureData != null) {
            this.dynamicTextureData.close();
            this.deleteGlTexture();
            this.dynamicTextureData = null;
        }
    }

    private void lambda$new$0() {
        TextureUtil.prepareImage(this.getGlTextureId(), this.dynamicTextureData.getWidth(), this.dynamicTextureData.getHeight());
        this.updateDynamicTexture();
        if (Config.isShaders()) {
            ShadersTex.initDynamicTextureNS(this);
        }
    }
}

