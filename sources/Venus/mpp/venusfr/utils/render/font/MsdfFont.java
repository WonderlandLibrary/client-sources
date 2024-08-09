/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import java.util.stream.Collectors;
import mpp.venusfr.utils.render.font.FontData;
import mpp.venusfr.utils.render.font.IOUtils;
import mpp.venusfr.utils.render.font.MsdfGlyph;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public final class MsdfFont {
    private final String name;
    private final Texture texture;
    private final FontData.AtlasData atlas;
    private final FontData.MetricsData metrics;
    private final Map<Integer, MsdfGlyph> glyphs;
    private boolean filtered = false;

    private MsdfFont(String string, Texture texture, FontData.AtlasData atlasData, FontData.MetricsData metricsData, Map<Integer, MsdfGlyph> map) {
        this.name = string;
        this.texture = texture;
        this.atlas = atlasData;
        this.metrics = metricsData;
        this.glyphs = map;
    }

    public void bind() {
        GlStateManager.bindTexture(this.texture.getGlTextureId());
        if (!this.filtered) {
            this.texture.setBlurMipmapDirect(true, true);
            this.filtered = true;
        }
    }

    public void unbind() {
        GlStateManager.bindTexture(0);
    }

    public void applyGlyphs(Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f, String string, float f2, float f3, float f4, float f5, int n, int n2, int n3, int n4) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            MsdfGlyph msdfGlyph = this.glyphs.get(c);
            if (msdfGlyph == null) continue;
            f3 += msdfGlyph.apply(matrix4f, iVertexBuilder, f, f3, f4, f5, n, n2, n3, n4) + f2;
        }
    }

    public float getWidth(String string, float f) {
        float f2 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            MsdfGlyph msdfGlyph = this.glyphs.get(c);
            if (msdfGlyph == null) continue;
            f2 += msdfGlyph.getWidth(f);
        }
        return f2;
    }

    public float getWidth(String string, float f, float f2) {
        float f3 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            MsdfGlyph msdfGlyph = this.glyphs.get(c);
            if (msdfGlyph == null) continue;
            f3 += msdfGlyph.getWidth(f) + f2;
        }
        return f3;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return this.name;
    }

    public FontData.AtlasData getAtlas() {
        return this.atlas;
    }

    public FontData.MetricsData getMetrics() {
        return this.metrics;
    }

    public static class Builder {
        public static final String MSDF_PATH = "venusfr/fonts/";
        private String name = "undefined";
        private ResourceLocation dataFile;
        private ResourceLocation atlasFile;

        private Builder() {
        }

        public Builder withName(String string) {
            this.name = string;
            return this;
        }

        public Builder withData(String string) {
            this.dataFile = new ResourceLocation(MSDF_PATH + string);
            return this;
        }

        public Builder withAtlas(String string) {
            this.atlasFile = new ResourceLocation(MSDF_PATH + string);
            return this;
        }

        public MsdfFont build() {
            FontData fontData = IOUtils.fromJsonToInstance(this.dataFile, FontData.class);
            Texture texture = IOUtils.toTexture(this.atlasFile);
            if (fontData == null) {
                throw new RuntimeException("Failed to read font data file: " + this.dataFile.toString() + "; Are you sure this is json file? Try to check the correctness of its syntax.");
            }
            float f = fontData.atlas().width();
            float f2 = fontData.atlas().height();
            Map<Integer, MsdfGlyph> map = fontData.glyphs().stream().collect(Collectors.toMap(Builder::lambda$build$0, arg_0 -> Builder.lambda$build$1(f, f2, arg_0)));
            return new MsdfFont(this.name, texture, fontData.atlas(), fontData.metrics(), map);
        }

        private static MsdfGlyph lambda$build$1(float f, float f2, FontData.GlyphData glyphData) {
            return new MsdfGlyph(glyphData, f, f2);
        }

        private static Integer lambda$build$0(FontData.GlyphData glyphData) {
            return glyphData.unicode();
        }
    }
}

