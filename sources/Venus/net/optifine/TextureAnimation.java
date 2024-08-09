/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimationFrame;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
    private String srcTex = null;
    private String dstTex = null;
    ResourceLocation dstTexLoc = null;
    private int dstTextId = -1;
    private int dstX = 0;
    private int dstY = 0;
    private int frameWidth = 0;
    private int frameHeight = 0;
    private TextureAnimationFrame[] frames = null;
    private int currentFrameIndex = 0;
    private boolean interpolate = false;
    private int interpolateSkip = 0;
    private ByteBuffer interpolateData = null;
    byte[] srcData = null;
    private ByteBuffer imageData = null;
    private boolean active = true;
    private boolean valid = true;

    public TextureAnimation(String string, byte[] byArray, String string2, ResourceLocation resourceLocation, int n, int n2, int n3, int n4, Properties properties) {
        this.srcTex = string;
        this.dstTex = string2;
        this.dstTexLoc = resourceLocation;
        this.dstX = n;
        this.dstY = n2;
        this.frameWidth = n3;
        this.frameHeight = n4;
        int n5 = n3 * n4 * 4;
        if (byArray.length % n5 != 0) {
            Config.warn("Invalid animated texture length: " + byArray.length + ", frameWidth: " + n3 + ", frameHeight: " + n4);
        }
        this.srcData = byArray;
        int n6 = byArray.length / n5;
        if (properties.get("tile.0") != null) {
            int n7 = 0;
            while (properties.get("tile." + n7) != null) {
                n6 = n7 + 1;
                ++n7;
            }
        }
        String string3 = (String)properties.get("duration");
        int n8 = Math.max(Config.parseInt(string3, 1), 1);
        this.frames = new TextureAnimationFrame[n6];
        for (int i = 0; i < this.frames.length; ++i) {
            TextureAnimationFrame textureAnimationFrame;
            String string4 = (String)properties.get("tile." + i);
            int n9 = Config.parseInt(string4, i);
            String string5 = (String)properties.get("duration." + i);
            int n10 = Math.max(Config.parseInt(string5, n8), 1);
            this.frames[i] = textureAnimationFrame = new TextureAnimationFrame(n9, n10);
        }
        this.interpolate = Config.parseBoolean(properties.getProperty("interpolate"), false);
        this.interpolateSkip = Config.parseInt(properties.getProperty("skip"), 0);
        if (this.interpolate) {
            this.interpolateData = GLAllocation.createDirectByteBuffer(n5);
        }
    }

    public boolean nextFrame() {
        TextureAnimationFrame textureAnimationFrame = this.getCurrentFrame();
        if (textureAnimationFrame == null) {
            return true;
        }
        ++textureAnimationFrame.counter;
        if (textureAnimationFrame.counter < textureAnimationFrame.duration) {
            return this.interpolate;
        }
        textureAnimationFrame.counter = 0;
        ++this.currentFrameIndex;
        if (this.currentFrameIndex >= this.frames.length) {
            this.currentFrameIndex = 0;
        }
        return false;
    }

    public TextureAnimationFrame getCurrentFrame() {
        return this.getFrame(this.currentFrameIndex);
    }

    public TextureAnimationFrame getFrame(int n) {
        if (this.frames.length <= 0) {
            return null;
        }
        if (n < 0 || n >= this.frames.length) {
            n = 0;
        }
        return this.frames[n];
    }

    public int getFrameCount() {
        return this.frames.length;
    }

    public void updateTexture() {
        if (this.valid) {
            if (this.dstTextId < 0) {
                Texture texture = TextureUtils.getTexture(this.dstTexLoc);
                if (texture == null) {
                    this.valid = false;
                    return;
                }
                this.dstTextId = texture.getGlTextureId();
            }
            if (this.imageData == null) {
                this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
                this.imageData.put(this.srcData);
                this.imageData.flip();
                this.srcData = null;
            }
            boolean bl = this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
            if (this.nextFrame() && this.active) {
                int n;
                int n2 = this.frameWidth * this.frameHeight * 4;
                TextureAnimationFrame textureAnimationFrame = this.getCurrentFrame();
                if (textureAnimationFrame != null && (n = n2 * textureAnimationFrame.index) + n2 <= this.imageData.limit()) {
                    if (this.interpolate && textureAnimationFrame.counter > 0) {
                        if (this.interpolateSkip <= 1 || textureAnimationFrame.counter % this.interpolateSkip == 0) {
                            TextureAnimationFrame textureAnimationFrame2 = this.getFrame(this.currentFrameIndex + 1);
                            double d = 1.0 * (double)textureAnimationFrame.counter / (double)textureAnimationFrame.duration;
                            this.updateTextureInerpolate(textureAnimationFrame, textureAnimationFrame2, d);
                        }
                    } else {
                        this.imageData.position(n);
                        GlStateManager.bindTexture(this.dstTextId);
                        TextureUtils.resetDataUnpacking();
                        GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
                    }
                }
            }
        }
    }

    private void updateTextureInerpolate(TextureAnimationFrame textureAnimationFrame, TextureAnimationFrame textureAnimationFrame2, double d) {
        int n;
        int n2 = this.frameWidth * this.frameHeight * 4;
        int n3 = n2 * textureAnimationFrame.index;
        if (n3 + n2 <= this.imageData.limit() && (n = n2 * textureAnimationFrame2.index) + n2 <= this.imageData.limit()) {
            this.interpolateData.clear();
            for (int i = 0; i < n2; ++i) {
                int n4 = this.imageData.get(n3 + i) & 0xFF;
                int n5 = this.imageData.get(n + i) & 0xFF;
                int n6 = this.mix(n4, n5, d);
                byte by = (byte)n6;
                this.interpolateData.put(by);
            }
            this.interpolateData.flip();
            GlStateManager.bindTexture(this.dstTextId);
            TextureUtils.resetDataUnpacking();
            GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
        }
    }

    private int mix(int n, int n2, double d) {
        return (int)((double)n * (1.0 - d) + (double)n2 * d);
    }

    public String getSrcTex() {
        return this.srcTex;
    }

    public String getDstTex() {
        return this.dstTex;
    }

    public ResourceLocation getDstTexLoc() {
        return this.dstTexLoc;
    }

    public boolean isActive() {
        return this.active;
    }
}

