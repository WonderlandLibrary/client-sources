/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.world.ClientWorld;
import net.optifine.LightMap;
import net.optifine.util.WorldUtils;

public class LightMapPack {
    private LightMap lightMap;
    private LightMap lightMapRain;
    private LightMap lightMapThunder;
    private int[] colorBuffer1 = new int[0];
    private int[] colorBuffer2 = new int[0];
    private int[] lmColorsBuffer = new int[0];

    public LightMapPack(LightMap lightMap, LightMap lightMap2, LightMap lightMap3) {
        if (lightMap2 != null || lightMap3 != null) {
            if (lightMap2 == null) {
                lightMap2 = lightMap;
            }
            if (lightMap3 == null) {
                lightMap3 = lightMap2;
            }
        }
        this.lightMap = lightMap;
        this.lightMapRain = lightMap2;
        this.lightMapThunder = lightMap3;
    }

    public boolean updateLightmap(ClientWorld clientWorld, float f, NativeImage nativeImage, boolean bl, float f2) {
        int n = nativeImage.getWidth() * nativeImage.getHeight();
        if (this.lmColorsBuffer.length != n) {
            this.lmColorsBuffer = new int[n];
        }
        nativeImage.getBufferRGBA().get(this.lmColorsBuffer);
        boolean bl2 = this.updateLightmap(clientWorld, f, this.lmColorsBuffer, bl, f2);
        if (bl2) {
            nativeImage.getBufferRGBA().put(this.lmColorsBuffer);
        }
        return bl2;
    }

    public boolean updateLightmap(ClientWorld clientWorld, float f, int[] nArray, boolean bl, float f2) {
        if (this.lightMapRain == null && this.lightMapThunder == null) {
            return this.lightMap.updateLightmap(clientWorld, f, nArray, bl);
        }
        if (!WorldUtils.isEnd(clientWorld) && !WorldUtils.isNether(clientWorld)) {
            boolean bl2;
            float f3 = clientWorld.getRainStrength(f2);
            float f4 = clientWorld.getThunderStrength(f2);
            float f5 = 1.0E-4f;
            boolean bl3 = f3 > f5;
            boolean bl4 = bl2 = f4 > f5;
            if (!bl3 && !bl2) {
                return this.lightMap.updateLightmap(clientWorld, f, nArray, bl);
            }
            if (f3 > 0.0f) {
                f4 /= f3;
            }
            float f6 = 1.0f - f3;
            float f7 = f3 - f4;
            if (this.colorBuffer1.length != nArray.length) {
                this.colorBuffer1 = new int[nArray.length];
                this.colorBuffer2 = new int[nArray.length];
            }
            int n = 0;
            int[][] nArrayArray = new int[][]{nArray, this.colorBuffer1, this.colorBuffer2};
            float[] fArray = new float[3];
            if (f6 > f5 && this.lightMap.updateLightmap(clientWorld, f, nArrayArray[n], bl)) {
                fArray[n] = f6;
                ++n;
            }
            if (f7 > f5 && this.lightMapRain != null && this.lightMapRain.updateLightmap(clientWorld, f, nArrayArray[n], bl)) {
                fArray[n] = f7;
                ++n;
            }
            if (f4 > f5 && this.lightMapThunder != null && this.lightMapThunder.updateLightmap(clientWorld, f, nArrayArray[n], bl)) {
                fArray[n] = f4;
                ++n;
            }
            if (n == 2) {
                return this.blend(nArrayArray[0], fArray[0], nArrayArray[5], fArray[1]);
            }
            return n == 3 ? this.blend(nArrayArray[0], fArray[0], nArrayArray[5], fArray[1], nArrayArray[5], fArray[2]) : true;
        }
        return this.lightMap.updateLightmap(clientWorld, f, nArray, bl);
    }

    private boolean blend(int[] nArray, float f, int[] nArray2, float f2) {
        if (nArray2.length != nArray.length) {
            return true;
        }
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            int n5 = nArray2[i];
            int n6 = n5 >> 16 & 0xFF;
            int n7 = n5 >> 8 & 0xFF;
            int n8 = n5 & 0xFF;
            int n9 = (int)((float)n2 * f + (float)n6 * f2);
            int n10 = (int)((float)n3 * f + (float)n7 * f2);
            int n11 = (int)((float)n4 * f + (float)n8 * f2);
            nArray[i] = 0xFF000000 | n9 << 16 | n10 << 8 | n11;
        }
        return false;
    }

    private boolean blend(int[] nArray, float f, int[] nArray2, float f2, int[] nArray3, float f3) {
        if (nArray2.length == nArray.length && nArray3.length == nArray.length) {
            for (int i = 0; i < nArray.length; ++i) {
                int n = nArray[i];
                int n2 = n >> 16 & 0xFF;
                int n3 = n >> 8 & 0xFF;
                int n4 = n & 0xFF;
                int n5 = nArray2[i];
                int n6 = n5 >> 16 & 0xFF;
                int n7 = n5 >> 8 & 0xFF;
                int n8 = n5 & 0xFF;
                int n9 = nArray3[i];
                int n10 = n9 >> 16 & 0xFF;
                int n11 = n9 >> 8 & 0xFF;
                int n12 = n9 & 0xFF;
                int n13 = (int)((float)n2 * f + (float)n6 * f2 + (float)n10 * f3);
                int n14 = (int)((float)n3 * f + (float)n7 * f2 + (float)n11 * f3);
                int n15 = (int)((float)n4 * f + (float)n8 * f2 + (float)n12 * f3);
                nArray[i] = 0xFF000000 | n13 << 16 | n14 << 8 | n15;
            }
            return false;
        }
        return true;
    }
}

