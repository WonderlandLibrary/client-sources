package im.expensive.utils.render.font;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.vector.Matrix4f;

public final class MsdfGlyph {

    private final int code;
    private final float minU, maxU, minV, maxV;
    private final float advance, topPosition,   width, height;
  
    public MsdfGlyph(FontData.GlyphData data, float atlasWidth, float atlasHeight) {
        this.code = data.unicode();
        this.advance = data.advance();
      
        FontData.BoundsData atlasBounds = data.atlasBounds();
        if (atlasBounds != null) {
            this.minU = atlasBounds.left() / atlasWidth;
            this.maxU = atlasBounds.right() / atlasWidth;
            this.minV = 1.0F - atlasBounds.top() / atlasHeight;
            this.maxV = 1.0F - atlasBounds.bottom() / atlasHeight;
        } else {
            this.minU = 0.0f;
            this.maxU = 0.0f;
            this.minV = 0.0f;
            this.maxV = 0.0f;
        }

        FontData.BoundsData planeBounds = data.planeBounds();
        if (planeBounds != null) {
            this.width = planeBounds.right() - planeBounds.left();
            this.height = planeBounds.top() - planeBounds.bottom();
            this.topPosition = planeBounds.top();
        } else {
            this.width = 0.0f;
            this.height = 0.0f;
            this.topPosition = 0.0f;
        }
    }
  
    public float apply(Matrix4f matrix, IVertexBuilder processor, float size, float x, float y, float z, int red, int green, int blue, int alpha) {
        y -= this.topPosition * size;
        y -= 1f;
        float width = this.width * size;

        float height = this.height * size;
        processor.pos(matrix, x, y, z).color(red, green, blue, alpha).tex(this.minU, this.minV).endVertex();
        processor.pos(matrix, x, y + height, z).color(red, green, blue, alpha).tex(this.minU, this.maxV).endVertex();
        processor.pos(matrix, x + width, y + height, z).color(red, green, blue, alpha).tex(this.maxU, this.maxV).endVertex();
        processor.pos(matrix, x + width, y, z).color(red, green, blue, alpha).tex(this.maxU, this.minV).endVertex();
        return this.width * (size - 1) + (Character.isSpaceChar(code) ? this.advance * size : 0);
    }
  
    public float getWidth(float size) {
        return this.width * (size - 1) + (Character.isSpaceChar(code) ? this.advance * size : 0);
    }

    public int getCharCode() {
        return code;
    }

}