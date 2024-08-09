package im.expensive.utils.render.font;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lombok.Getter;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

import java.util.Map;
import java.util.stream.Collectors;

public final class MsdfFont {

    @Getter
    private final String name;
    private final Texture texture;
    @Getter
    private final FontData.AtlasData atlas;
    @Getter
    private final FontData.MetricsData metrics;
    private final Map<Integer, MsdfGlyph> glyphs;
    private boolean filtered = false;

    private MsdfFont(String name, Texture texture, FontData.AtlasData atlas, FontData.MetricsData metrics, Map<Integer, MsdfGlyph> glyphs) {
        this.name = name;
        this.texture = texture;
        this.atlas = atlas;
        this.metrics = metrics;
        this.glyphs = glyphs;
    }
    // **IMPORTANT**: to correct msdf font anti-aliasing we should set GL_TEXTURE_MAG_FILTER and GL_TEXTURE_MIN_FILTER to GL_LINEAR.
    public void bind() {
        GlStateManager.bindTexture(this.texture.getGlTextureId());
        if (!this.filtered) {

            this.texture.setBlurMipmapDirect(true, false);
            this.filtered = true;
        }
    }
  
    public void unbind() {
        GlStateManager.bindTexture(0);
    }
  
    public void applyGlyphs(Matrix4f matrix, IVertexBuilder processor, float size, String text, float thickness, float x, float y, float z, int red, int green, int blue, int alpha) {
        for (int i = 0; i < text.length(); i++) {
          
            int _char = text.charAt(i);
            MsdfGlyph glyph = this.glyphs.get(_char);
          
            if (glyph == null)
                continue;

            x += glyph.apply(matrix, processor, size, x, y, z, red, green, blue, alpha) + thickness;
        }
    }
  
    public float getWidth(String text, float size) {
        float width = 0.0f;
        for (int i = 0; i < text.length(); i++) {
            int _char = text.charAt(i);
            MsdfGlyph glyph = this.glyphs.get(_char);
          
            if (glyph == null)
                continue;

          
            width += glyph.getWidth(size);
        }
      
        return width;
    }

    public float getWidth(String text, float size, float thickness) {
        float width = 0.0f;
        for (int i = 0; i < text.length(); i++) {
            int _char = text.charAt(i);
            MsdfGlyph glyph = this.glyphs.get(_char);

            if (glyph == null)
                continue;


            width += glyph.getWidth(size) + thickness;
        }

        return width;
    }


    public static MsdfFont.Builder builder() {
        return new Builder();
    }
  
    public static class Builder {
      
        public static final String MSDF_PATH = "expensive/fonts/";
        private String name = "undefined";
        private ResourceLocation dataFile;
        private ResourceLocation atlasFile;
      
        private Builder() {}
      
        public MsdfFont.Builder withName(String name) {
            this.name = name;
            return this;
        }
      
        // should be json file
        public MsdfFont.Builder withData(String dataFile) {
            this.dataFile = new ResourceLocation(MSDF_PATH + dataFile);
            return this;
        }
      
        // should be png file
        public MsdfFont.Builder withAtlas(String atlasFile) {
            this.atlasFile = new ResourceLocation(MSDF_PATH + atlasFile);
            return this;
        }
      
        public MsdfFont build() {
            FontData data = IOUtils.fromJsonToInstance(this.dataFile, FontData.class); // I LIKE JSON
            Texture texture = IOUtils.toTexture(this.atlasFile);
          
            if (data == null)
                throw new RuntimeException("Failed to read font data file: " + this.dataFile.toString() +
                        "; Are you sure this is json file? Try to check the correctness of its syntax.");

            float aWidth = data.atlas().width();
            float aHeight = data.atlas().height();
            Map<Integer, MsdfGlyph> glyphs = data.glyphs().stream()
                    .collect(Collectors.<FontData.GlyphData, Integer, MsdfGlyph>toMap(
                            (glyphData) -> glyphData.unicode(),
                            (glyphData) -> new MsdfGlyph(glyphData, aWidth, aHeight)
                    ));

            return new MsdfFont(this.name, texture, data.atlas(), data.metrics(), glyphs);
        }

    }

}