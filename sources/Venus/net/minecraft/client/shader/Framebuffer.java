/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.FramebufferConstants;
import net.optifine.reflect.ReflectorForge;

public class Framebuffer {
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public int framebufferWidth;
    public int framebufferHeight;
    public final boolean useDepth;
    public int framebufferObject;
    public int framebufferTexture;
    public int depthBuffer;
    public final float[] framebufferColor;
    public int framebufferFilter;
    private boolean stencilEnabled = false;

    public Framebuffer(int n, int n2, boolean bl, boolean bl2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.useDepth = bl;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        this.framebufferColor = new float[4];
        this.framebufferColor[0] = 1.0f;
        this.framebufferColor[1] = 1.0f;
        this.framebufferColor[2] = 1.0f;
        this.framebufferColor[3] = 0.0f;
        this.resize(n, n2, bl2);
    }

    public void resize(int n, int n2, boolean bl) {
        int n3 = Math.max(1, n);
        int n4 = Math.max(1, n2);
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$resize$0(n3, n4, bl));
        } else {
            this.resizeRaw(n3, n4, bl);
        }
    }

    private void resizeRaw(int n, int n2, boolean bl) {
        if (!GLX.isUsingFBOs()) {
            this.framebufferWidth = n;
            this.framebufferHeight = n2;
        } else {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            GlStateManager.enableDepthTest();
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createBuffers(n, n2, bl);
            GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
        }
    }

    public void deleteFramebuffer() {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                TextureUtil.releaseTextureId(this.depthBuffer);
                this.depthBuffer = -1;
            }
            if (this.framebufferTexture > -1) {
                TextureUtil.releaseTextureId(this.framebufferTexture);
                this.framebufferTexture = -1;
            }
            if (this.framebufferObject > -1) {
                GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
                GlStateManager.deleteFramebuffers(this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }

    public void func_237506_a_(Framebuffer framebuffer) {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            if (GlStateManager.isFabulous()) {
                GlStateManager.bindFramebuffer(36008, framebuffer.framebufferObject);
                GlStateManager.bindFramebuffer(36009, this.framebufferObject);
                GlStateManager.blitFramebuffer(0, 0, framebuffer.framebufferTextureWidth, framebuffer.framebufferTextureHeight, 0, 0, this.framebufferTextureWidth, this.framebufferTextureHeight, 256, 9728);
            } else {
                GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, this.framebufferObject);
                int n = GlStateManager.getFrameBufferAttachmentParam();
                if (n != 0) {
                    int n2 = GlStateManager.getActiveTextureId();
                    GlStateManager.bindTexture(n);
                    GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, framebuffer.framebufferObject);
                    GlStateManager.copySubImage(3553, 0, 0, 0, 0, 0, Math.min(this.framebufferTextureWidth, framebuffer.framebufferTextureWidth), Math.min(this.framebufferTextureHeight, framebuffer.framebufferTextureHeight));
                    GlStateManager.bindTexture(n2);
                }
            }
            GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
        }
    }

    public void createBuffers(int n, int n2, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.framebufferWidth = n;
        this.framebufferHeight = n2;
        this.framebufferTextureWidth = n;
        this.framebufferTextureHeight = n2;
        if (!GLX.isUsingFBOs()) {
            this.framebufferClear(bl);
        } else {
            this.framebufferObject = GlStateManager.genFramebuffers();
            this.framebufferTexture = TextureUtil.generateTextureId();
            if (this.useDepth) {
                this.depthBuffer = TextureUtil.generateTextureId();
                GlStateManager.bindTexture(this.depthBuffer);
                GlStateManager.texParameter(3553, 10241, 9728);
                GlStateManager.texParameter(3553, 10240, 9728);
                GlStateManager.texParameter(3553, 10242, 10496);
                GlStateManager.texParameter(3553, 10243, 10496);
                GlStateManager.texParameter(3553, 34892, 0);
                if (this.stencilEnabled) {
                    GlStateManager.texImage2D(3553, 0, 36013, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 34041, 36269, null);
                } else {
                    GlStateManager.texImage2D(3553, 0, 6402, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6402, 5126, null);
                }
            }
            this.setFramebufferFilter(9728);
            GlStateManager.bindTexture(this.framebufferTexture);
            GlStateManager.texImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, null);
            GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, this.framebufferObject);
            GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
            if (this.useDepth) {
                if (this.stencilEnabled) {
                    if (ReflectorForge.getForgeUseCombinedDepthStencilAttachment()) {
                        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, 33306, 3553, this.depthBuffer, 0);
                    } else {
                        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, 36096, 3553, this.depthBuffer, 0);
                        GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, 36128, 3553, this.depthBuffer, 0);
                    }
                } else {
                    GlStateManager.framebufferTexture2D(FramebufferConstants.GL_FRAMEBUFFER, FramebufferConstants.GL_DEPTH_ATTACHMENT, 3553, this.depthBuffer, 0);
                }
            }
            this.checkFramebufferComplete();
            this.framebufferClear(bl);
            this.unbindFramebufferTexture();
        }
    }

    public void setFramebufferFilter(int n) {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            this.framebufferFilter = n;
            GlStateManager.bindTexture(this.framebufferTexture);
            GlStateManager.texParameter(3553, 10241, n);
            GlStateManager.texParameter(3553, 10240, n);
            GlStateManager.texParameter(3553, 10242, 10496);
            GlStateManager.texParameter(3553, 10243, 10496);
            GlStateManager.bindTexture(0);
        }
    }

    public void checkFramebufferComplete() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        int n = GlStateManager.checkFramebufferStatus(FramebufferConstants.GL_FRAMEBUFFER);
        if (n != FramebufferConstants.GL_FRAMEBUFFER_COMPLETE) {
            if (n == FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            }
            if (n == FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            }
            if (n == FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
            }
            if (n == FramebufferConstants.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
            }
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + n);
        }
    }

    public void bindFramebufferTexture() {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GlStateManager.bindTexture(this.framebufferTexture);
        }
    }

    public void unbindFramebufferTexture() {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            GlStateManager.bindTexture(0);
        }
    }

    public void bindFramebuffer(boolean bl) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$bindFramebuffer$1(bl));
        } else {
            this.bindFramebufferRaw(bl);
        }
    }

    private void bindFramebufferRaw(boolean bl) {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, this.framebufferObject);
            if (bl) {
                GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }

    public void unbindFramebuffer() {
        if (GLX.isUsingFBOs()) {
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(Framebuffer::lambda$unbindFramebuffer$2);
            } else {
                GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
            }
        }
    }

    public void setFramebufferColor(float f, float f2, float f3, float f4) {
        this.framebufferColor[0] = f;
        this.framebufferColor[1] = f2;
        this.framebufferColor[2] = f3;
        this.framebufferColor[3] = f4;
    }

    public void framebufferRender(int n, int n2) {
        this.framebufferRenderExt(n, n2, false);
    }

    public void framebufferRenderExt(int n, int n2, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
        if (!RenderSystem.isInInitPhase()) {
            RenderSystem.recordRenderCall(() -> this.lambda$framebufferRenderExt$3(n, n2, bl));
        } else {
            this.framebufferRenderExtRaw(n, n2, bl);
        }
    }

    private void framebufferRenderExtRaw(int n, int n2, boolean bl) {
        if (GLX.isUsingFBOs()) {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GlStateManager.colorMask(true, true, true, false);
            GlStateManager.disableDepthTest();
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, n, n2, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translatef(0.0f, 0.0f, -2000.0f);
            GlStateManager.viewport(0, 0, n, n2);
            GlStateManager.enableTexture();
            GlStateManager.disableLighting();
            GlStateManager.disableAlphaTest();
            if (bl) {
                GlStateManager.disableBlend();
                GlStateManager.enableColorMaterial();
            }
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindFramebufferTexture();
            float f = n;
            float f2 = n2;
            float f3 = (float)this.framebufferWidth / (float)this.framebufferTextureWidth;
            float f4 = (float)this.framebufferHeight / (float)this.framebufferTextureHeight;
            Tessellator tessellator = RenderSystem.renderThreadTesselator();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(0.0, f2, 0.0).tex(0.0f, 0.0f).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(f, f2, 0.0).tex(f3, 0.0f).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(f, 0.0, 0.0).tex(f3, f4).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0f, f4).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
            this.unbindFramebufferTexture();
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
        }
    }

    public void framebufferClear(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.bindFramebuffer(false);
        GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
        int n = 16384;
        if (this.useDepth) {
            GlStateManager.clearDepth(1.0);
            n |= 0x100;
        }
        GlStateManager.clear(n, bl);
        this.unbindFramebuffer();
    }

    public int func_242996_f() {
        return this.framebufferTexture;
    }

    public int func_242997_g() {
        return this.depthBuffer;
    }

    public void enableStencil() {
        if (!this.stencilEnabled) {
            this.stencilEnabled = true;
            this.resize(this.framebufferWidth, this.framebufferHeight, Minecraft.IS_RUNNING_ON_MAC);
        }
    }

    public boolean isStencilEnabled() {
        return this.stencilEnabled;
    }

    private void lambda$framebufferRenderExt$3(int n, int n2, boolean bl) {
        this.framebufferRenderExtRaw(n, n2, bl);
    }

    private static void lambda$unbindFramebuffer$2() {
        GlStateManager.bindFramebuffer(FramebufferConstants.GL_FRAMEBUFFER, 0);
    }

    private void lambda$bindFramebuffer$1(boolean bl) {
        this.bindFramebufferRaw(bl);
    }

    private void lambda$resize$0(int n, int n2, boolean bl) {
        this.resizeRaw(n, n2, bl);
    }
}

