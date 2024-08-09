/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;

public class NaturalProperties {
    public int rotation = 1;
    public boolean flip = false;
    private Map[] quadMaps = new Map[8];

    public NaturalProperties(String string) {
        if (string.equals("4")) {
            this.rotation = 4;
        } else if (string.equals("2")) {
            this.rotation = 2;
        } else if (string.equals("F")) {
            this.flip = true;
        } else if (string.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
        } else if (string.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
        } else {
            Config.warn("NaturalTextures: Unknown type: " + string);
        }
    }

    public boolean isValid() {
        if (this.rotation != 2 && this.rotation != 4) {
            return this.flip;
        }
        return false;
    }

    public synchronized BakedQuad getQuad(BakedQuad bakedQuad, int n, boolean bl) {
        int n2 = n;
        if (bl) {
            n2 = n | 4;
        }
        if (n2 > 0 && n2 < this.quadMaps.length) {
            BakedQuad bakedQuad2;
            IdentityHashMap<BakedQuad, BakedQuad> identityHashMap = this.quadMaps[n2];
            if (identityHashMap == null) {
                this.quadMaps[n2] = identityHashMap = new IdentityHashMap<BakedQuad, BakedQuad>(1);
            }
            if ((bakedQuad2 = (BakedQuad)identityHashMap.get(bakedQuad)) == null) {
                bakedQuad2 = this.makeQuad(bakedQuad, n, bl);
                identityHashMap.put(bakedQuad, bakedQuad2);
            }
            return bakedQuad2;
        }
        return bakedQuad;
    }

    private BakedQuad makeQuad(BakedQuad bakedQuad, int n, boolean bl) {
        int[] nArray = bakedQuad.getVertexData();
        int n2 = bakedQuad.getTintIndex();
        Direction direction = bakedQuad.getFace();
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        boolean bl2 = bakedQuad.applyDiffuseLighting();
        if (!this.isFullSprite(bakedQuad)) {
            n = 0;
        }
        nArray = this.transformVertexData(nArray, n, bl);
        return new BakedQuad(nArray, n2, direction, textureAtlasSprite, bl2);
    }

    private int[] transformVertexData(int[] nArray, int n, boolean bl) {
        int[] nArray2 = (int[])nArray.clone();
        int n2 = 4 - n;
        if (bl) {
            n2 += 3;
        }
        n2 %= 4;
        int n3 = nArray2.length / 4;
        for (int i = 0; i < 4; ++i) {
            int n4 = i * n3;
            int n5 = n2 * n3;
            nArray2[n5 + 4] = nArray[n4 + 4];
            nArray2[n5 + 4 + 1] = nArray[n4 + 4 + 1];
            if (bl) {
                if (--n2 >= 0) continue;
                n2 = 3;
                continue;
            }
            if (++n2 <= 3) continue;
            n2 = 0;
        }
        return nArray2;
    }

    private boolean isFullSprite(BakedQuad bakedQuad) {
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        float f = textureAtlasSprite.getMinU();
        float f2 = textureAtlasSprite.getMaxU();
        float f3 = f2 - f;
        float f4 = f3 / 256.0f;
        float f5 = textureAtlasSprite.getMinV();
        float f6 = textureAtlasSprite.getMaxV();
        float f7 = f6 - f5;
        float f8 = f7 / 256.0f;
        int[] nArray = bakedQuad.getVertexData();
        int n = nArray.length / 4;
        for (int i = 0; i < 4; ++i) {
            int n2 = i * n;
            float f9 = Float.intBitsToFloat(nArray[n2 + 4]);
            float f10 = Float.intBitsToFloat(nArray[n2 + 4 + 1]);
            if (!this.equalsDelta(f9, f, f4) && !this.equalsDelta(f9, f2, f4)) {
                return true;
            }
            if (this.equalsDelta(f10, f5, f8) || this.equalsDelta(f10, f6, f8)) continue;
            return true;
        }
        return false;
    }

    private boolean equalsDelta(float f, float f2, float f3) {
        float f4 = MathHelper.abs(f - f2);
        return f4 < f3;
    }
}

