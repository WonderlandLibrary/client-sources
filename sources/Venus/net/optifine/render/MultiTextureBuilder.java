/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.Config;
import net.optifine.render.MultiTextureData;
import net.optifine.render.RenderTypes;
import net.optifine.render.SpriteRenderData;
import net.optifine.util.IntArray;
import net.optifine.util.TextureUtils;

public class MultiTextureBuilder {
    private int vertexCount;
    private RenderType blockLayer;
    private TextureAtlasSprite[] quadSprites;
    private boolean reorderingAllowed;
    private boolean[] drawnIcons = new boolean[256];
    private List<SpriteRenderData> spriteRenderDatas = new ArrayList<SpriteRenderData>();
    private IntArray vertexPositions = new IntArray(16);
    private IntArray vertexCounts = new IntArray(16);

    public MultiTextureData build(int n, RenderType renderType, TextureAtlasSprite[] textureAtlasSpriteArray) {
        if (textureAtlasSpriteArray == null) {
            return null;
        }
        this.vertexCount = n;
        this.blockLayer = renderType;
        this.quadSprites = textureAtlasSpriteArray;
        this.reorderingAllowed = this.blockLayer != RenderTypes.TRANSLUCENT;
        int n2 = Config.getTextureMap().getCountRegisteredSprites();
        if (this.drawnIcons.length <= n2) {
            this.drawnIcons = new boolean[n2 + 1];
        }
        Arrays.fill(this.drawnIcons, false);
        this.spriteRenderDatas.clear();
        int n3 = 0;
        int n4 = -1;
        int n5 = this.vertexCount / 4;
        for (int i = 0; i < n5; ++i) {
            int n6;
            TextureAtlasSprite textureAtlasSprite = this.quadSprites[i];
            if (textureAtlasSprite == null || this.drawnIcons[n6 = textureAtlasSprite.getIndexInMap()]) continue;
            if (textureAtlasSprite == TextureUtils.iconGrassSideOverlay) {
                if (n4 >= 0) continue;
                n4 = i;
                continue;
            }
            i = this.drawForIcon(textureAtlasSprite, i) - 1;
            ++n3;
            if (!this.reorderingAllowed) continue;
            this.drawnIcons[n6] = true;
        }
        if (n4 >= 0) {
            this.drawForIcon(TextureUtils.iconGrassSideOverlay, n4);
            ++n3;
        }
        SpriteRenderData[] spriteRenderDataArray = this.spriteRenderDatas.toArray(new SpriteRenderData[this.spriteRenderDatas.size()]);
        return new MultiTextureData(spriteRenderDataArray);
    }

    private int drawForIcon(TextureAtlasSprite textureAtlasSprite, int n) {
        this.vertexPositions.clear();
        this.vertexCounts.clear();
        int n2 = -1;
        int n3 = -1;
        int n4 = this.vertexCount / 4;
        for (int i = n; i < n4; ++i) {
            TextureAtlasSprite textureAtlasSprite2 = this.quadSprites[i];
            if (textureAtlasSprite2 == textureAtlasSprite) {
                if (n3 >= 0) continue;
                n3 = i;
                continue;
            }
            if (n3 < 0) continue;
            this.draw(n3, i);
            if (!this.reorderingAllowed) {
                this.spriteRenderDatas.add(new SpriteRenderData(textureAtlasSprite, this.vertexPositions.toIntArray(), this.vertexCounts.toIntArray()));
                return i;
            }
            n3 = -1;
            if (n2 >= 0) continue;
            n2 = i;
        }
        if (n3 >= 0) {
            this.draw(n3, n4);
        }
        if (n2 < 0) {
            n2 = n4;
        }
        this.spriteRenderDatas.add(new SpriteRenderData(textureAtlasSprite, this.vertexPositions.toIntArray(), this.vertexCounts.toIntArray()));
        return n2;
    }

    private void draw(int n, int n2) {
        int n3 = n2 - n;
        if (n3 > 0) {
            int n4 = n * 4;
            int n5 = n3 * 4;
            this.vertexPositions.put(n4);
            this.vertexCounts.put(n5);
        }
    }
}

