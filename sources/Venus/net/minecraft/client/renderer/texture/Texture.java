/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.concurrent.Executor;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.ShadersTex;

public abstract class Texture
implements AutoCloseable {
    protected int glTextureId = -1;
    protected boolean blur;
    protected boolean mipmap;
    public MultiTexID multiTex;
    private boolean blurMipmapSet;
    private boolean lastBlur;
    private boolean lastMipmap;

    public void setBlurMipmapDirect(boolean bl, boolean bl2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (!this.blurMipmapSet || this.blur != bl || this.mipmap != bl2) {
            int n;
            int n2;
            this.blurMipmapSet = true;
            this.blur = bl;
            this.mipmap = bl2;
            if (bl) {
                n2 = bl2 ? 9987 : 9729;
                n = 9729;
            } else {
                int n3 = Config.getMipmapType();
                n2 = bl2 ? n3 : 9728;
                n = 9728;
            }
            GlStateManager.bindTexture(this.getGlTextureId());
            GlStateManager.texParameter(3553, 10241, n2);
            GlStateManager.texParameter(3553, 10240, n);
        }
    }

    public int getGlTextureId() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (this.glTextureId == -1) {
            this.glTextureId = TextureUtil.generateTextureId();
        }
        return this.glTextureId;
    }

    public void deleteGlTexture() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::lambda$deleteGlTexture$0);
        } else if (this.glTextureId != -1) {
            ShadersTex.deleteTextures(this, this.glTextureId);
            this.blurMipmapSet = false;
            TextureUtil.releaseTextureId(this.glTextureId);
            this.glTextureId = -1;
        }
    }

    public abstract void loadTexture(IResourceManager var1) throws IOException;

    public void bindTexture() {
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(this::lambda$bindTexture$1);
        } else {
            GlStateManager.bindTexture(this.getGlTextureId());
        }
    }

    public void loadTexture(TextureManager textureManager, IResourceManager iResourceManager, ResourceLocation resourceLocation, Executor executor) {
        textureManager.loadTexture(resourceLocation, this);
    }

    @Override
    public void close() {
    }

    public MultiTexID getMultiTexID() {
        return ShadersTex.getMultiTexID(this);
    }

    public void setBlurMipmap(boolean bl, boolean bl2) {
        this.lastBlur = this.blur;
        this.lastMipmap = this.mipmap;
        this.setBlurMipmapDirect(bl, bl2);
    }

    public void restoreLastBlurMipmap() {
        this.setBlurMipmapDirect(this.lastBlur, this.lastMipmap);
    }

    private void lambda$bindTexture$1() {
        GlStateManager.bindTexture(this.getGlTextureId());
    }

    private void lambda$deleteGlTexture$0() {
        ShadersTex.deleteTextures(this, this.glTextureId);
        this.blurMipmapSet = false;
        if (this.glTextureId != -1) {
            TextureUtil.releaseTextureId(this.glTextureId);
            this.glTextureId = -1;
        }
    }
}

