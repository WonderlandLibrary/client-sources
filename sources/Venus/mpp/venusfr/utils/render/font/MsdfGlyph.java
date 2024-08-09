/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import mpp.venusfr.utils.render.font.FontData;
import net.minecraft.util.math.vector.Matrix4f;

public final class MsdfGlyph {
    private final int code;
    private final float minU;
    private final float maxU;
    private final float minV;
    private final float maxV;
    private final float advance;
    private final float topPosition;
    private final float width;
    private final float height;

    public MsdfGlyph(FontData.GlyphData glyphData, float f, float f2) {
        this.code = glyphData.unicode();
        this.advance = glyphData.advance();
        FontData.BoundsData boundsData = glyphData.atlasBounds();
        if (boundsData != null) {
            this.minU = boundsData.left() / f;
            this.maxU = boundsData.right() / f;
            this.minV = 1.0f - boundsData.top() / f2;
            this.maxV = 1.0f - boundsData.bottom() / f2;
        } else {
            this.minU = 0.0f;
            this.maxU = 0.0f;
            this.minV = 0.0f;
            this.maxV = 0.0f;
        }
        FontData.BoundsData boundsData2 = glyphData.planeBounds();
        if (boundsData2 != null) {
            this.width = boundsData2.right() - boundsData2.left();
            this.height = boundsData2.top() - boundsData2.bottom();
            this.topPosition = boundsData2.top();
        } else {
            this.width = 0.0f;
            this.height = 0.0f;
            this.topPosition = 0.0f;
        }
    }

    public float apply(Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, int n, int n2, int n3, int n4) {
        f3 -= this.topPosition * f;
        float f5 = this.width * f;
        float f6 = this.height * f;
        iVertexBuilder.pos(matrix4f, f2, f3 -= 1.0f, f4).color(n, n2, n3, n4).tex(this.minU, this.minV).endVertex();
        iVertexBuilder.pos(matrix4f, f2, f3 + f6, f4).color(n, n2, n3, n4).tex(this.minU, this.maxV).endVertex();
        iVertexBuilder.pos(matrix4f, f2 + f5, f3 + f6, f4).color(n, n2, n3, n4).tex(this.maxU, this.maxV).endVertex();
        iVertexBuilder.pos(matrix4f, f2 + f5, f3, f4).color(n, n2, n3, n4).tex(this.maxU, this.minV).endVertex();
        return this.width * (f - 1.0f) + (Character.isSpaceChar(this.code) ? this.advance * f : 0.0f);
    }

    public float getWidth(float f) {
        return this.width * (f - 1.0f) + (Character.isSpaceChar(this.code) ? this.advance * f : 0.0f);
    }

    public int getCharCode() {
        return this.code;
    }
}

