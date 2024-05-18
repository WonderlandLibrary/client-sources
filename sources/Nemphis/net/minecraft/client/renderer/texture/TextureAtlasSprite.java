/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.TextureUtils;
import shadersmod.client.ShadersTex;

public class TextureAtlasSprite {
    private final String iconName;
    protected List framesTextureData = Lists.newArrayList();
    protected int[][] field_176605_b;
    private AnimationMetadataSection animationMetadata;
    protected boolean rotated;
    protected int originX;
    protected int originY;
    protected int width;
    protected int height;
    private float minU;
    private float maxU;
    private float minV;
    private float maxV;
    protected int frameCounter;
    protected int tickCounter;
    private static String field_176607_p = "builtin/clock";
    private static String field_176606_q = "builtin/compass";
    private static final String __OBFID = "CL_00001062";
    private int indexInMap = -1;
    public float baseU;
    public float baseV;
    public int sheetWidth;
    public int sheetHeight;
    public int glSpriteTextureId = -1;
    public TextureAtlasSprite spriteSingle = null;
    public boolean isSpriteSingle = false;
    public int mipmapLevels = 0;

    private TextureAtlasSprite(TextureAtlasSprite parent) {
        this.iconName = parent.iconName;
        this.isSpriteSingle = true;
    }

    protected TextureAtlasSprite(String p_i1282_1_) {
        this.iconName = p_i1282_1_;
        if (Config.isMultiTexture()) {
            this.spriteSingle = new TextureAtlasSprite(this);
        }
    }

    protected static TextureAtlasSprite func_176604_a(ResourceLocation p_176604_0_) {
        String var1 = p_176604_0_.toString();
        return field_176607_p.equals(var1) ? new TextureClock(var1) : (field_176606_q.equals(var1) ? new TextureCompass(var1) : new TextureAtlasSprite(var1));
    }

    public static void func_176602_a(String p_176602_0_) {
        field_176607_p = p_176602_0_;
    }

    public static void func_176603_b(String p_176603_0_) {
        field_176606_q = p_176603_0_;
    }

    public void initSprite(int p_110971_1_, int p_110971_2_, int p_110971_3_, int p_110971_4_, boolean p_110971_5_) {
        this.originX = p_110971_3_;
        this.originY = p_110971_4_;
        this.rotated = p_110971_5_;
        float var6 = (float)(0.009999999776482582 / (double)p_110971_1_);
        float var7 = (float)(0.009999999776482582 / (double)p_110971_2_);
        this.minU = (float)p_110971_3_ / (float)p_110971_1_ + var6;
        this.maxU = (float)(p_110971_3_ + this.width) / (float)p_110971_1_ - var6;
        this.minV = (float)p_110971_4_ / (float)p_110971_2_ + var7;
        this.maxV = (float)(p_110971_4_ + this.height) / (float)p_110971_2_ - var7;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }

    public void copyFrom(TextureAtlasSprite p_94217_1_) {
        this.originX = p_94217_1_.originX;
        this.originY = p_94217_1_.originY;
        this.width = p_94217_1_.width;
        this.height = p_94217_1_.height;
        this.rotated = p_94217_1_.rotated;
        this.minU = p_94217_1_.minU;
        this.maxU = p_94217_1_.maxU;
        this.minV = p_94217_1_.minV;
        this.maxV = p_94217_1_.maxV;
        if (this.spriteSingle != null) {
            this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
        }
    }

    public int getOriginX() {
        return this.originX;
    }

    public int getOriginY() {
        return this.originY;
    }

    public int getIconWidth() {
        return this.width;
    }

    public int getIconHeight() {
        return this.height;
    }

    public float getMinU() {
        return this.minU;
    }

    public float getMaxU() {
        return this.maxU;
    }

    public float getInterpolatedU(double p_94214_1_) {
        float var3 = this.maxU - this.minU;
        return this.minU + var3 * (float)p_94214_1_ / 16.0f;
    }

    public float getMinV() {
        return this.minV;
    }

    public float getMaxV() {
        return this.maxV;
    }

    public float getInterpolatedV(double p_94207_1_) {
        float var3 = this.maxV - this.minV;
        return this.minV + var3 * ((float)p_94207_1_ / 16.0f);
    }

    public String getIconName() {
        return this.iconName;
    }

    public void updateAnimation() {
        ++this.tickCounter;
        if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
            int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
            int var2 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
            this.frameCounter = (this.frameCounter + 1) % var2;
            this.tickCounter = 0;
            int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
            boolean texBlur = false;
            boolean texClamp = this.isSpriteSingle;
            if (var1 != var3 && var3 >= 0 && var3 < this.framesTextureData.size()) {
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSub((int[][])this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, texBlur, texClamp);
                } else {
                    TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, texBlur, texClamp);
                }
            }
        } else if (this.animationMetadata.func_177219_e()) {
            this.func_180599_n();
        }
    }

    private void func_180599_n() {
        int var5;
        int var4;
        double var1 = 1.0 - (double)this.tickCounter / (double)this.animationMetadata.getFrameTimeSingle(this.frameCounter);
        int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
        if (var3 != (var5 = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % (var4 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount()))) && var5 >= 0 && var5 < this.framesTextureData.size()) {
            int[][] var6 = (int[][])this.framesTextureData.get(var3);
            int[][] var7 = (int[][])this.framesTextureData.get(var5);
            if (this.field_176605_b == null || this.field_176605_b.length != var6.length) {
                this.field_176605_b = new int[var6.length][];
            }
            int var8 = 0;
            while (var8 < var6.length) {
                if (this.field_176605_b[var8] == null) {
                    this.field_176605_b[var8] = new int[var6[var8].length];
                }
                if (var8 < var7.length && var7[var8].length == var6[var8].length) {
                    int var9 = 0;
                    while (var9 < var6[var8].length) {
                        int var10 = var6[var8][var9];
                        int var11 = var7[var8][var9];
                        int var12 = (int)((double)((var10 & 16711680) >> 16) * var1 + (double)((var11 & 16711680) >> 16) * (1.0 - var1));
                        int var13 = (int)((double)((var10 & 65280) >> 8) * var1 + (double)((var11 & 65280) >> 8) * (1.0 - var1));
                        int var14 = (int)((double)(var10 & 255) * var1 + (double)(var11 & 255) * (1.0 - var1));
                        this.field_176605_b[var8][var9] = var10 & -16777216 | var12 << 16 | var13 << 8 | var14;
                        ++var9;
                    }
                }
                ++var8;
            }
            TextureUtil.uploadTextureMipmap(this.field_176605_b, this.width, this.height, this.originX, this.originY, false, false);
        }
    }

    public int[][] getFrameTextureData(int p_147965_1_) {
        return (int[][])this.framesTextureData.get(p_147965_1_);
    }

    public int getFrameCount() {
        return this.framesTextureData.size();
    }

    public void setIconWidth(int p_110966_1_) {
        this.width = p_110966_1_;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconWidth(this.width);
        }
    }

    public void setIconHeight(int p_110969_1_) {
        this.height = p_110969_1_;
        if (this.spriteSingle != null) {
            this.spriteSingle.setIconHeight(this.height);
        }
    }

    public void func_180598_a(BufferedImage[] p_180598_1_, AnimationMetadataSection p_180598_2_) throws IOException {
        int var11;
        this.resetSprite();
        int var3 = p_180598_1_[0].getWidth();
        int var4 = p_180598_1_[0].getHeight();
        this.width = var3;
        this.height = var4;
        int[][] var5 = new int[p_180598_1_.length][];
        int var6 = 0;
        while (var6 < p_180598_1_.length) {
            BufferedImage i = p_180598_1_[var6];
            if (i != null) {
                if (var6 > 0 && (i.getWidth() != var3 >> var6 || i.getHeight() != var4 >> var6)) {
                    throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", var6, i.getWidth(), i.getHeight(), var3 >> var6, var4 >> var6));
                }
                var5[var6] = new int[i.getWidth() * i.getHeight()];
                i.getRGB(0, 0, i.getWidth(), i.getHeight(), var5[var6], 0, i.getWidth());
            }
            ++var6;
        }
        if (p_180598_2_ == null) {
            if (var4 != var3) {
                throw new RuntimeException("broken aspect ratio and not an animation");
            }
            this.framesTextureData.add(var5);
        } else {
            var6 = var4 / var3;
            var11 = var3;
            int datas = var3;
            this.height = this.width;
            if (p_180598_2_.getFrameCount() > 0) {
                Iterator data = p_180598_2_.getFrameIndexSet().iterator();
                while (data.hasNext()) {
                    int di = (Integer)data.next();
                    if (di >= var6) {
                        throw new RuntimeException("invalid frameindex " + di);
                    }
                    this.allocateFrameTextureData(di);
                    this.framesTextureData.set(di, TextureAtlasSprite.getFrameTextureData(var5, var11, datas, di));
                }
                this.animationMetadata = p_180598_2_;
            } else {
                ArrayList var13 = Lists.newArrayList();
                int di = 0;
                while (di < var6) {
                    this.framesTextureData.add(TextureAtlasSprite.getFrameTextureData(var5, var11, datas, di));
                    var13.add(new AnimationFrame(di, -1));
                    ++di;
                }
                this.animationMetadata = new AnimationMetadataSection(var13, this.width, this.height, p_180598_2_.getFrameTime(), p_180598_2_.func_177219_e());
            }
        }
        var11 = 0;
        while (var11 < this.framesTextureData.size()) {
            int[][] var12 = (int[][])this.framesTextureData.get(var11);
            if (var12 != null && !this.iconName.startsWith("minecraft:blocks/leaves_")) {
                int di = 0;
                while (di < var12.length) {
                    int[] var14 = var12[di];
                    this.fixTransparentColor(var14);
                    ++di;
                }
            }
            ++var11;
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.func_180598_a(p_180598_1_, p_180598_2_);
        }
    }

    public void generateMipmaps(int p_147963_1_) {
        ArrayList var2 = Lists.newArrayList();
        int var3 = 0;
        while (var3 < this.framesTextureData.size()) {
            final int[][] var4 = (int[][])this.framesTextureData.get(var3);
            if (var4 != null) {
                try {
                    var2.add(TextureUtil.generateMipmapData(p_147963_1_, this.width, var4));
                }
                catch (Throwable var8) {
                    CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
                    CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
                    var7.addCrashSection("Frame index", var3);
                    var7.addCrashSectionCallable("Frame sizes", new Callable(){
                        private static final String __OBFID = "CL_00001063";

                        public String call() {
                            StringBuilder var1 = new StringBuilder();
                            int[][] var2 = var4;
                            int var3 = var2.length;
                            int var4x = 0;
                            while (var4x < var3) {
                                int[] var5 = var2[var4x];
                                if (var1.length() > 0) {
                                    var1.append(", ");
                                }
                                var1.append((Object)(var5 == null ? "null" : Integer.valueOf(var5.length)));
                                ++var4x;
                            }
                            return var1.toString();
                        }
                    });
                    throw new ReportedException(var6);
                }
            }
            ++var3;
        }
        this.setFramesTextureData(var2);
        if (this.spriteSingle != null) {
            this.spriteSingle.generateMipmaps(p_147963_1_);
        }
    }

    private void allocateFrameTextureData(int p_130099_1_) {
        if (this.framesTextureData.size() <= p_130099_1_) {
            int var2 = this.framesTextureData.size();
            while (var2 <= p_130099_1_) {
                this.framesTextureData.add(null);
                ++var2;
            }
        }
        if (this.spriteSingle != null) {
            this.spriteSingle.allocateFrameTextureData(p_130099_1_);
        }
    }

    private static int[][] getFrameTextureData(int[][] p_147962_0_, int p_147962_1_, int p_147962_2_, int p_147962_3_) {
        int[][] var4 = new int[p_147962_0_.length][];
        int var5 = 0;
        while (var5 < p_147962_0_.length) {
            int[] var6 = p_147962_0_[var5];
            if (var6 != null) {
                var4[var5] = new int[(p_147962_1_ >> var5) * (p_147962_2_ >> var5)];
                System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
            }
            ++var5;
        }
        return var4;
    }

    public void clearFramesTextureData() {
        this.framesTextureData.clear();
        if (this.spriteSingle != null) {
            this.spriteSingle.clearFramesTextureData();
        }
    }

    public boolean hasAnimationMetadata() {
        if (this.animationMetadata != null) {
            return true;
        }
        return false;
    }

    public void setFramesTextureData(List p_110968_1_) {
        this.framesTextureData = p_110968_1_;
        if (this.spriteSingle != null) {
            this.spriteSingle.setFramesTextureData(p_110968_1_);
        }
    }

    private void resetSprite() {
        this.animationMetadata = null;
        this.setFramesTextureData(Lists.newArrayList());
        this.frameCounter = 0;
        this.tickCounter = 0;
        if (this.spriteSingle != null) {
            this.spriteSingle.resetSprite();
        }
    }

    public String toString() {
        return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
    }

    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return false;
    }

    public boolean load(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    public int getIndexInMap() {
        return this.indexInMap;
    }

    public void setIndexInMap(int indexInMap) {
        this.indexInMap = indexInMap;
    }

    private void fixTransparentColor(int[] data) {
        if (data != null) {
            int col;
            int i;
            int colAvg;
            int greenAvg;
            int blueAvg;
            long redSum = 0;
            long greenSum = 0;
            long blueSum = 0;
            long count = 0;
            int redAvg = 0;
            while (redAvg < data.length) {
                greenAvg = data[redAvg];
                blueAvg = greenAvg >> 24 & 255;
                if (blueAvg >= 16) {
                    colAvg = greenAvg >> 16 & 255;
                    i = greenAvg >> 8 & 255;
                    col = greenAvg & 255;
                    redSum += (long)colAvg;
                    greenSum += (long)i;
                    blueSum += (long)col;
                    ++count;
                }
                ++redAvg;
            }
            if (count > 0) {
                redAvg = (int)(redSum / count);
                greenAvg = (int)(greenSum / count);
                blueAvg = (int)(blueSum / count);
                colAvg = redAvg << 16 | greenAvg << 8 | blueAvg;
                i = 0;
                while (i < data.length) {
                    col = data[i];
                    int alpha = col >> 24 & 255;
                    if (alpha <= 16) {
                        data[i] = colAvg;
                    }
                    ++i;
                }
            }
        }
    }

    public double getSpriteU16(float atlasU) {
        float dU = this.maxU - this.minU;
        return (atlasU - this.minU) / dU * 16.0f;
    }

    public double getSpriteV16(float atlasV) {
        float dV = this.maxV - this.minV;
        return (atlasV - this.minV) / dV * 16.0f;
    }

    public void bindSpriteTexture() {
        if (this.glSpriteTextureId < 0) {
            this.glSpriteTextureId = TextureUtil.glGenTextures();
            TextureUtil.func_180600_a(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
            TextureUtils.applyAnisotropicLevel();
        }
        TextureUtils.bindTexture(this.glSpriteTextureId);
    }

    public void deleteSpriteTexture() {
        if (this.glSpriteTextureId >= 0) {
            TextureUtil.deleteTexture(this.glSpriteTextureId);
            this.glSpriteTextureId = -1;
        }
    }

    public float toSingleU(float u) {
        u -= this.baseU;
        float ku = (float)this.sheetWidth / (float)this.width;
        return u *= ku;
    }

    public float toSingleV(float v) {
        v -= this.baseV;
        float kv = (float)this.sheetHeight / (float)this.height;
        return v *= kv;
    }

}

