/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import java.util.Arrays;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class BakedQuadRetextured
extends BakedQuad {
    public BakedQuadRetextured(BakedQuad bakedQuad, TextureAtlasSprite textureAtlasSprite) {
        super(BakedQuadRetextured.remapVertexData(bakedQuad.getVertexData(), bakedQuad.getSprite(), textureAtlasSprite), bakedQuad.getTintIndex(), FaceBakery.getFacingFromVertexData(bakedQuad.getVertexData()), textureAtlasSprite, bakedQuad.applyDiffuseLighting());
    }

    private static int[] remapVertexData(int[] nArray, TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite textureAtlasSprite2) {
        int[] nArray2 = Arrays.copyOf(nArray, nArray.length);
        for (int i = 0; i < 4; ++i) {
            VertexFormat vertexFormat = DefaultVertexFormats.BLOCK;
            int n = vertexFormat.getIntegerSize() * i;
            int n2 = vertexFormat.getOffset(2) / 4;
            nArray2[n + n2] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedU(textureAtlasSprite.getUnInterpolatedU(Float.intBitsToFloat(nArray[n + n2]))));
            nArray2[n + n2 + 1] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedV(textureAtlasSprite.getUnInterpolatedV(Float.intBitsToFloat(nArray[n + n2 + 1]))));
        }
        return nArray2;
    }
}

