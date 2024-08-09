/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.render.MultiTextureData;
import net.optifine.render.SpriteRenderData;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;

public class MultiTextureRenderer {
    private static IntBuffer bufferPositions = Config.createDirectIntBuffer(1024);
    private static IntBuffer bufferCounts = Config.createDirectIntBuffer(1024);
    private static boolean shaders;

    public static void draw(int n, MultiTextureData multiTextureData) {
        shaders = Config.isShaders();
        SpriteRenderData[] spriteRenderDataArray = multiTextureData.getSpriteRenderDatas();
        for (int i = 0; i < spriteRenderDataArray.length; ++i) {
            SpriteRenderData spriteRenderData = spriteRenderDataArray[i];
            MultiTextureRenderer.draw(n, spriteRenderData);
        }
    }

    private static void draw(int n, SpriteRenderData spriteRenderData) {
        int n2;
        TextureAtlasSprite textureAtlasSprite = spriteRenderData.getSprite();
        int[] nArray = spriteRenderData.getPositions();
        int[] nArray2 = spriteRenderData.getCounts();
        GlStateManager.bindTexture(textureAtlasSprite.glSpriteTextureId);
        if (shaders) {
            n2 = textureAtlasSprite.spriteNormal != null ? textureAtlasSprite.spriteNormal.glSpriteTextureId : 0;
            int n3 = textureAtlasSprite.spriteSpecular != null ? textureAtlasSprite.spriteSpecular.glSpriteTextureId : 0;
            AtlasTexture atlasTexture = textureAtlasSprite.getAtlasTexture();
            ShadersTex.bindNSTextures(n2, n3, atlasTexture.isNormalBlend(), atlasTexture.isSpecularBlend(), atlasTexture.isMipmaps());
            if (Shaders.uniform_spriteBounds.isDefined()) {
                Shaders.uniform_spriteBounds.setValue(textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV());
            }
        }
        if (bufferPositions.capacity() < nArray.length) {
            n2 = MathHelper.smallestEncompassingPowerOfTwo(nArray.length);
            bufferPositions = Config.createDirectIntBuffer(n2);
            bufferCounts = Config.createDirectIntBuffer(n2);
        }
        bufferPositions.clear();
        bufferCounts.clear();
        bufferPositions.put(nArray);
        bufferCounts.put(nArray2);
        bufferPositions.flip();
        bufferCounts.flip();
        GlStateManager.glMultiDrawArrays(n, bufferPositions, bufferCounts);
    }
}

