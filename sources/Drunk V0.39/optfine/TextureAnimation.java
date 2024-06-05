/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package optfine;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import optfine.Config;
import optfine.CustomAnimationFrame;
import optfine.TextureUtils;
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
    private CustomAnimationFrame[] frames = null;
    private int activeFrame = 0;
    byte[] srcData = null;
    private ByteBuffer imageData = null;

    public TextureAnimation(String p_i63_1_, byte[] p_i63_2_, String p_i63_3_, ResourceLocation p_i63_4_, int p_i63_5_, int p_i63_6_, int p_i63_7_, int p_i63_8_, Properties p_i63_9_, int p_i63_10_) {
        this.srcTex = p_i63_1_;
        this.dstTex = p_i63_3_;
        this.dstTexLoc = p_i63_4_;
        this.dstX = p_i63_5_;
        this.dstY = p_i63_6_;
        this.frameWidth = p_i63_7_;
        this.frameHeight = p_i63_8_;
        int i = p_i63_7_ * p_i63_8_ * 4;
        if (p_i63_2_.length % i != 0) {
            Config.warn("Invalid animated texture length: " + p_i63_2_.length + ", frameWidth: " + p_i63_7_ + ", frameHeight: " + p_i63_8_);
        }
        this.srcData = p_i63_2_;
        int j = p_i63_2_.length / i;
        if (p_i63_9_.get("tile.0") != null) {
            int k = 0;
            while (p_i63_9_.get("tile." + k) != null) {
                j = k + 1;
                ++k;
            }
        }
        String s2 = (String)p_i63_9_.get("duration");
        int l = Config.parseInt(s2, p_i63_10_);
        this.frames = new CustomAnimationFrame[j];
        int i1 = 0;
        while (i1 < this.frames.length) {
            CustomAnimationFrame customanimationframe;
            String s = (String)p_i63_9_.get("tile." + i1);
            int j1 = Config.parseInt(s, i1);
            String s1 = (String)p_i63_9_.get("duration." + i1);
            int k1 = Config.parseInt(s1, l);
            this.frames[i1] = customanimationframe = new CustomAnimationFrame(j1, k1);
            ++i1;
        }
    }

    public boolean nextFrame() {
        if (this.frames.length <= 0) {
            return false;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        CustomAnimationFrame customanimationframe = this.frames[this.activeFrame];
        ++customanimationframe.counter;
        if (customanimationframe.counter < customanimationframe.duration) {
            return false;
        }
        customanimationframe.counter = 0;
        ++this.activeFrame;
        if (this.activeFrame < this.frames.length) return true;
        this.activeFrame = 0;
        return true;
    }

    public int getActiveFrameIndex() {
        if (this.frames.length <= 0) {
            return 0;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        CustomAnimationFrame customanimationframe = this.frames[this.activeFrame];
        return customanimationframe.index;
    }

    public int getFrameCount() {
        return this.frames.length;
    }

    public boolean updateTexture() {
        if (this.dstTextId < 0) {
            ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);
            if (itextureobject == null) {
                return false;
            }
            this.dstTextId = itextureobject.getGlTextureId();
        }
        if (this.imageData == null) {
            this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
            this.imageData.put(this.srcData);
            this.srcData = null;
        }
        if (!this.nextFrame()) {
            return false;
        }
        int k = this.frameWidth * this.frameHeight * 4;
        int i = this.getActiveFrameIndex();
        int j = k * i;
        if (j + k > this.imageData.capacity()) {
            return false;
        }
        this.imageData.position(j);
        GlStateManager.bindTexture(this.dstTextId);
        GL11.glTexSubImage2D((int)3553, (int)0, (int)this.dstX, (int)this.dstY, (int)this.frameWidth, (int)this.frameHeight, (int)6408, (int)5121, (ByteBuffer)this.imageData);
        return true;
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
}

