/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class FontTexture
extends Texture {
    private final ResourceLocation textureLocation;
    private final RenderType field_228158_e_;
    private final RenderType field_228159_f_;
    private final boolean colored;
    private final Entry entry;

    public FontTexture(ResourceLocation resourceLocation, boolean bl) {
        this.textureLocation = resourceLocation;
        this.colored = bl;
        this.entry = new Entry(0, 0, 256, 256);
        TextureUtil.prepareImage(bl ? NativeImage.PixelFormatGLCode.RGBA : NativeImage.PixelFormatGLCode.INTENSITY, this.getGlTextureId(), 256, 256);
        this.field_228158_e_ = RenderType.getText(resourceLocation);
        this.field_228159_f_ = RenderType.getTextSeeThrough(resourceLocation);
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) {
    }

    @Override
    public void close() {
        this.deleteGlTexture();
    }

    @Nullable
    public TexturedGlyph createTexturedGlyph(IGlyphInfo iGlyphInfo) {
        if (iGlyphInfo.isColored() != this.colored) {
            return null;
        }
        Entry entry = this.entry.func_211224_a(iGlyphInfo);
        if (entry != null) {
            this.bindTexture();
            iGlyphInfo.uploadGlyph(entry.xOffset, entry.yOffset);
            float f = 256.0f;
            float f2 = 256.0f;
            float f3 = 0.01f;
            return new TexturedGlyph(this.field_228158_e_, this.field_228159_f_, ((float)entry.xOffset + 0.01f) / 256.0f, ((float)entry.xOffset - 0.01f + (float)iGlyphInfo.getWidth()) / 256.0f, ((float)entry.yOffset + 0.01f) / 256.0f, ((float)entry.yOffset - 0.01f + (float)iGlyphInfo.getHeight()) / 256.0f, iGlyphInfo.func_211198_f(), iGlyphInfo.func_211199_g(), iGlyphInfo.func_211200_h(), iGlyphInfo.func_211204_i());
        }
        return null;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    static class Entry {
        private final int xOffset;
        private final int yOffset;
        private final int field_211227_c;
        private final int field_211228_d;
        private Entry field_211229_e;
        private Entry field_211230_f;
        private boolean field_211231_g;

        private Entry(int n, int n2, int n3, int n4) {
            this.xOffset = n;
            this.yOffset = n2;
            this.field_211227_c = n3;
            this.field_211228_d = n4;
        }

        @Nullable
        Entry func_211224_a(IGlyphInfo iGlyphInfo) {
            if (this.field_211229_e != null && this.field_211230_f != null) {
                Entry entry = this.field_211229_e.func_211224_a(iGlyphInfo);
                if (entry == null) {
                    entry = this.field_211230_f.func_211224_a(iGlyphInfo);
                }
                return entry;
            }
            if (this.field_211231_g) {
                return null;
            }
            int n = iGlyphInfo.getWidth();
            int n2 = iGlyphInfo.getHeight();
            if (n <= this.field_211227_c && n2 <= this.field_211228_d) {
                if (n == this.field_211227_c && n2 == this.field_211228_d) {
                    this.field_211231_g = true;
                    return this;
                }
                int n3 = this.field_211227_c - n;
                int n4 = this.field_211228_d - n2;
                if (n3 > n4) {
                    this.field_211229_e = new Entry(this.xOffset, this.yOffset, n, this.field_211228_d);
                    this.field_211230_f = new Entry(this.xOffset + n + 1, this.yOffset, this.field_211227_c - n - 1, this.field_211228_d);
                } else {
                    this.field_211229_e = new Entry(this.xOffset, this.yOffset, this.field_211227_c, n2);
                    this.field_211230_f = new Entry(this.xOffset, this.yOffset + n2 + 1, this.field_211227_c, this.field_211228_d - n2 - 1);
                }
                return this.field_211229_e.func_211224_a(iGlyphInfo);
            }
            return null;
        }
    }
}

