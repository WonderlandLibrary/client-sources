/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.ConnectedProperties;
import net.optifine.ConnectedTextures;
import net.optifine.render.RenderEnv;

public class ConnectedTexturesCompact {
    private static final int COMPACT_NONE = 0;
    private static final int COMPACT_ALL = 1;
    private static final int COMPACT_V = 2;
    private static final int COMPACT_H = 3;
    private static final int COMPACT_HV = 4;

    public static BakedQuad[] getConnectedTextureCtmCompact(int n, ConnectedProperties connectedProperties, int n2, BakedQuad bakedQuad, RenderEnv renderEnv) {
        int n3;
        if (connectedProperties.ctmTileIndexes != null && n >= 0 && n < connectedProperties.ctmTileIndexes.length && (n3 = connectedProperties.ctmTileIndexes[n]) >= 0 && n3 <= connectedProperties.tileIcons.length) {
            return ConnectedTexturesCompact.getQuadsCompact(n3, connectedProperties.tileIcons, bakedQuad, renderEnv);
        }
        switch (n) {
            case 1: {
                return ConnectedTexturesCompact.getQuadsCompactH(0, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 2: {
                return ConnectedTexturesCompact.getQuadsCompact(3, connectedProperties.tileIcons, bakedQuad, renderEnv);
            }
            case 3: {
                return ConnectedTexturesCompact.getQuadsCompactH(3, 0, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 4: {
                return ConnectedTexturesCompact.getQuadsCompact4(0, 3, 2, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 5: {
                return ConnectedTexturesCompact.getQuadsCompact4(3, 0, 4, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 6: {
                return ConnectedTexturesCompact.getQuadsCompact4(2, 4, 2, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 7: {
                return ConnectedTexturesCompact.getQuadsCompact4(3, 3, 4, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 8: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 1, 4, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 9: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 4, 4, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 10: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 4, 1, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 11: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 1, 4, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 12: {
                return ConnectedTexturesCompact.getQuadsCompactV(0, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 13: {
                return ConnectedTexturesCompact.getQuadsCompact4(0, 3, 2, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 14: {
                return ConnectedTexturesCompact.getQuadsCompactV(3, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 15: {
                return ConnectedTexturesCompact.getQuadsCompact4(3, 0, 1, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 16: {
                return ConnectedTexturesCompact.getQuadsCompact4(2, 4, 0, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 17: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 2, 3, 0, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 18: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 4, 3, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 19: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 2, 4, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 20: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 4, 4, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 21: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 4, 1, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 22: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 4, 1, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 23: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 1, 4, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 24: {
                return ConnectedTexturesCompact.getQuadsCompact(2, connectedProperties.tileIcons, bakedQuad, renderEnv);
            }
            case 25: {
                return ConnectedTexturesCompact.getQuadsCompactH(2, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 26: {
                return ConnectedTexturesCompact.getQuadsCompact(1, connectedProperties.tileIcons, bakedQuad, renderEnv);
            }
            case 27: {
                return ConnectedTexturesCompact.getQuadsCompactH(1, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 28: {
                return ConnectedTexturesCompact.getQuadsCompact4(2, 4, 2, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 29: {
                return ConnectedTexturesCompact.getQuadsCompact4(3, 3, 1, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 30: {
                return ConnectedTexturesCompact.getQuadsCompact4(2, 1, 2, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 31: {
                return ConnectedTexturesCompact.getQuadsCompact4(3, 3, 4, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 32: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 1, 1, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 33: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 1, 4, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 34: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 1, 1, 4, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 35: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 4, 4, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 36: {
                return ConnectedTexturesCompact.getQuadsCompactV(2, 0, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 37: {
                return ConnectedTexturesCompact.getQuadsCompact4(2, 1, 0, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 38: {
                return ConnectedTexturesCompact.getQuadsCompactV(1, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 39: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 2, 3, 0, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 40: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 1, 3, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 41: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 2, 4, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 42: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 4, 3, 3, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 43: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 2, 1, 2, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 44: {
                return ConnectedTexturesCompact.getQuadsCompact4(1, 4, 1, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 45: {
                return ConnectedTexturesCompact.getQuadsCompact4(4, 1, 1, 1, connectedProperties.tileIcons, n2, bakedQuad, renderEnv);
            }
            case 46: {
                return ConnectedTexturesCompact.getQuadsCompact(4, connectedProperties.tileIcons, bakedQuad, renderEnv);
            }
        }
        return ConnectedTexturesCompact.getQuadsCompact(0, connectedProperties.tileIcons, bakedQuad, renderEnv);
    }

    private static BakedQuad[] getQuadsCompactH(int n, int n2, TextureAtlasSprite[] textureAtlasSpriteArray, int n3, BakedQuad bakedQuad, RenderEnv renderEnv) {
        return ConnectedTexturesCompact.getQuadsCompact(Dir.LEFT, n, Dir.RIGHT, n2, textureAtlasSpriteArray, n3, bakedQuad, renderEnv);
    }

    private static BakedQuad[] getQuadsCompactV(int n, int n2, TextureAtlasSprite[] textureAtlasSpriteArray, int n3, BakedQuad bakedQuad, RenderEnv renderEnv) {
        return ConnectedTexturesCompact.getQuadsCompact(Dir.UP, n, Dir.DOWN, n2, textureAtlasSpriteArray, n3, bakedQuad, renderEnv);
    }

    private static BakedQuad[] getQuadsCompact4(int n, int n2, int n3, int n4, TextureAtlasSprite[] textureAtlasSpriteArray, int n5, BakedQuad bakedQuad, RenderEnv renderEnv) {
        if (n == n2) {
            return n3 == n4 ? ConnectedTexturesCompact.getQuadsCompact(Dir.UP, n, Dir.DOWN, n3, textureAtlasSpriteArray, n5, bakedQuad, renderEnv) : ConnectedTexturesCompact.getQuadsCompact(Dir.UP, n, Dir.DOWN_LEFT, n3, Dir.DOWN_RIGHT, n4, textureAtlasSpriteArray, n5, bakedQuad, renderEnv);
        }
        if (n3 == n4) {
            return ConnectedTexturesCompact.getQuadsCompact(Dir.UP_LEFT, n, Dir.UP_RIGHT, n2, Dir.DOWN, n3, textureAtlasSpriteArray, n5, bakedQuad, renderEnv);
        }
        if (n == n3) {
            return n2 == n4 ? ConnectedTexturesCompact.getQuadsCompact(Dir.LEFT, n, Dir.RIGHT, n2, textureAtlasSpriteArray, n5, bakedQuad, renderEnv) : ConnectedTexturesCompact.getQuadsCompact(Dir.LEFT, n, Dir.UP_RIGHT, n2, Dir.DOWN_RIGHT, n4, textureAtlasSpriteArray, n5, bakedQuad, renderEnv);
        }
        return n2 == n4 ? ConnectedTexturesCompact.getQuadsCompact(Dir.UP_LEFT, n, Dir.DOWN_LEFT, n3, Dir.RIGHT, n2, textureAtlasSpriteArray, n5, bakedQuad, renderEnv) : ConnectedTexturesCompact.getQuadsCompact(Dir.UP_LEFT, n, Dir.UP_RIGHT, n2, Dir.DOWN_LEFT, n3, Dir.DOWN_RIGHT, n4, textureAtlasSpriteArray, n5, bakedQuad, renderEnv);
    }

    private static BakedQuad[] getQuadsCompact(int n, TextureAtlasSprite[] textureAtlasSpriteArray, BakedQuad bakedQuad, RenderEnv renderEnv) {
        TextureAtlasSprite textureAtlasSprite = textureAtlasSpriteArray[n];
        return ConnectedTextures.getQuads(textureAtlasSprite, bakedQuad, renderEnv);
    }

    private static BakedQuad[] getQuadsCompact(Dir dir, int n, Dir dir2, int n2, TextureAtlasSprite[] textureAtlasSpriteArray, int n3, BakedQuad bakedQuad, RenderEnv renderEnv) {
        BakedQuad bakedQuad2 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n], dir, n3, bakedQuad, renderEnv);
        BakedQuad bakedQuad3 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n2], dir2, n3, bakedQuad, renderEnv);
        return renderEnv.getArrayQuadsCtm(bakedQuad2, bakedQuad3);
    }

    private static BakedQuad[] getQuadsCompact(Dir dir, int n, Dir dir2, int n2, Dir dir3, int n3, TextureAtlasSprite[] textureAtlasSpriteArray, int n4, BakedQuad bakedQuad, RenderEnv renderEnv) {
        BakedQuad bakedQuad2 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n], dir, n4, bakedQuad, renderEnv);
        BakedQuad bakedQuad3 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n2], dir2, n4, bakedQuad, renderEnv);
        BakedQuad bakedQuad4 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n3], dir3, n4, bakedQuad, renderEnv);
        return renderEnv.getArrayQuadsCtm(bakedQuad2, bakedQuad3, bakedQuad4);
    }

    private static BakedQuad[] getQuadsCompact(Dir dir, int n, Dir dir2, int n2, Dir dir3, int n3, Dir dir4, int n4, TextureAtlasSprite[] textureAtlasSpriteArray, int n5, BakedQuad bakedQuad, RenderEnv renderEnv) {
        BakedQuad bakedQuad2 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n], dir, n5, bakedQuad, renderEnv);
        BakedQuad bakedQuad3 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n2], dir2, n5, bakedQuad, renderEnv);
        BakedQuad bakedQuad4 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n3], dir3, n5, bakedQuad, renderEnv);
        BakedQuad bakedQuad5 = ConnectedTexturesCompact.getQuadCompact(textureAtlasSpriteArray[n4], dir4, n5, bakedQuad, renderEnv);
        return renderEnv.getArrayQuadsCtm(bakedQuad2, bakedQuad3, bakedQuad4, bakedQuad5);
    }

    private static BakedQuad getQuadCompact(TextureAtlasSprite textureAtlasSprite, Dir dir, int n, BakedQuad bakedQuad, RenderEnv renderEnv) {
        switch (1.$SwitchMap$net$optifine$ConnectedTexturesCompact$Dir[dir.ordinal()]) {
            case 1: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 0, 0, 16, 8, n, bakedQuad, renderEnv);
            }
            case 2: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 8, 0, 16, 8, n, bakedQuad, renderEnv);
            }
            case 3: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 8, 0, 16, 16, n, bakedQuad, renderEnv);
            }
            case 4: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 8, 8, 16, 16, n, bakedQuad, renderEnv);
            }
            case 5: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 0, 8, 16, 16, n, bakedQuad, renderEnv);
            }
            case 6: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 0, 8, 8, 16, n, bakedQuad, renderEnv);
            }
            case 7: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 0, 0, 8, 16, n, bakedQuad, renderEnv);
            }
            case 8: {
                return ConnectedTexturesCompact.getQuadCompact(textureAtlasSprite, dir, 0, 0, 8, 8, n, bakedQuad, renderEnv);
            }
        }
        return bakedQuad;
    }

    private static BakedQuad getQuadCompact(TextureAtlasSprite textureAtlasSprite, Dir dir, int n, int n2, int n3, int n4, int n5, BakedQuad bakedQuad, RenderEnv renderEnv) {
        Map[][] mapArray = ConnectedTextures.getSpriteQuadCompactMaps();
        if (mapArray == null) {
            return bakedQuad;
        }
        int n6 = textureAtlasSprite.getIndexInMap();
        if (n6 >= 0 && n6 < mapArray.length) {
            BakedQuad bakedQuad2;
            IdentityHashMap<BakedQuad, BakedQuad> identityHashMap;
            Map[] mapArray2 = mapArray[n6];
            if (mapArray2 == null) {
                mapArray2 = new Map[Dir.VALUES.length];
                mapArray[n6] = mapArray2;
            }
            if ((identityHashMap = mapArray2[dir.ordinal()]) == null) {
                mapArray2[dir.ordinal()] = identityHashMap = new IdentityHashMap<BakedQuad, BakedQuad>(1);
            }
            if ((bakedQuad2 = (BakedQuad)identityHashMap.get(bakedQuad)) == null) {
                bakedQuad2 = ConnectedTexturesCompact.makeSpriteQuadCompact(bakedQuad, textureAtlasSprite, n5, n, n2, n3, n4);
                identityHashMap.put(bakedQuad, bakedQuad2);
            }
            return bakedQuad2;
        }
        return bakedQuad;
    }

    private static BakedQuad makeSpriteQuadCompact(BakedQuad bakedQuad, TextureAtlasSprite textureAtlasSprite, int n, int n2, int n3, int n4, int n5) {
        int[] nArray = (int[])bakedQuad.getVertexData().clone();
        TextureAtlasSprite textureAtlasSprite2 = bakedQuad.getSprite();
        for (int i = 0; i < 4; ++i) {
            ConnectedTexturesCompact.fixVertexCompact(nArray, i, textureAtlasSprite2, textureAtlasSprite, n, n2, n3, n4, n5);
        }
        return new BakedQuad(nArray, bakedQuad.getTintIndex(), bakedQuad.getFace(), textureAtlasSprite, bakedQuad.applyDiffuseLighting());
    }

    private static void fixVertexCompact(int[] nArray, int n, TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite textureAtlasSprite2, int n2, int n3, int n4, int n5, int n6) {
        float f;
        float f2;
        int n7 = nArray.length / 4;
        int n8 = n7 * n;
        float f3 = Float.intBitsToFloat(nArray[n8 + 4]);
        float f4 = Float.intBitsToFloat(nArray[n8 + 4 + 1]);
        double d = textureAtlasSprite.getSpriteU16(f3);
        double d2 = textureAtlasSprite.getSpriteV16(f4);
        float f5 = Float.intBitsToFloat(nArray[n8 + 0]);
        float f6 = Float.intBitsToFloat(nArray[n8 + 1]);
        float f7 = Float.intBitsToFloat(nArray[n8 + 2]);
        switch (n2) {
            case 0: {
                f2 = f5;
                f = 1.0f - f7;
                break;
            }
            case 1: {
                f2 = f5;
                f = f7;
                break;
            }
            case 2: {
                f2 = 1.0f - f5;
                f = 1.0f - f6;
                break;
            }
            case 3: {
                f2 = f5;
                f = 1.0f - f6;
                break;
            }
            case 4: {
                f2 = f7;
                f = 1.0f - f6;
                break;
            }
            case 5: {
                f2 = 1.0f - f7;
                f = 1.0f - f6;
                break;
            }
            default: {
                return;
            }
        }
        float f8 = (float)textureAtlasSprite.getWidth() / (textureAtlasSprite.getMaxU() - textureAtlasSprite.getMinU());
        float f9 = (float)textureAtlasSprite.getHeight() / (textureAtlasSprite.getMaxV() - textureAtlasSprite.getMinV());
        float f10 = 4.0f / Math.max(f9, f8);
        float f11 = 16.0f * (1.0f - f10);
        float f12 = 16.0f * (1.0f - f10);
        if (d < (double)n3) {
            f2 = (float)((double)f2 + ((double)n3 - d) / (double)f11);
            d = n3;
        }
        if (d > (double)n5) {
            f2 = (float)((double)f2 - (d - (double)n5) / (double)f11);
            d = n5;
        }
        if (d2 < (double)n4) {
            f = (float)((double)f + ((double)n4 - d2) / (double)f12);
            d2 = n4;
        }
        if (d2 > (double)n6) {
            f = (float)((double)f - (d2 - (double)n6) / (double)f12);
            d2 = n6;
        }
        switch (n2) {
            case 0: {
                f5 = f2;
                f7 = 1.0f - f;
                break;
            }
            case 1: {
                f5 = f2;
                f7 = f;
                break;
            }
            case 2: {
                f5 = 1.0f - f2;
                f6 = 1.0f - f;
                break;
            }
            case 3: {
                f5 = f2;
                f6 = 1.0f - f;
                break;
            }
            case 4: {
                f7 = f2;
                f6 = 1.0f - f;
                break;
            }
            case 5: {
                f7 = 1.0f - f2;
                f6 = 1.0f - f;
                break;
            }
            default: {
                return;
            }
        }
        nArray[n8 + 4] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedU(d));
        nArray[n8 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite2.getInterpolatedV(d2));
        nArray[n8 + 0] = Float.floatToRawIntBits(f5);
        nArray[n8 + 1] = Float.floatToRawIntBits(f6);
        nArray[n8 + 2] = Float.floatToRawIntBits(f7);
    }

    private static enum Dir {
        UP,
        UP_RIGHT,
        RIGHT,
        DOWN_RIGHT,
        DOWN,
        DOWN_LEFT,
        LEFT,
        UP_LEFT;

        public static final Dir[] VALUES;

        static {
            VALUES = Dir.values();
        }
    }
}

