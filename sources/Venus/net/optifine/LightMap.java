/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.client.world.ClientWorld;
import net.optifine.Config;
import net.optifine.CustomColormap;

public class LightMap {
    private CustomColormap lightMapRgb = null;
    private float[][] sunRgbs = new float[16][3];
    private float[][] torchRgbs = new float[16][3];

    public LightMap(CustomColormap customColormap) {
        this.lightMapRgb = customColormap;
    }

    public CustomColormap getColormap() {
        return this.lightMapRgb;
    }

    public boolean updateLightmap(ClientWorld clientWorld, float f, int[] nArray, boolean bl) {
        if (this.lightMapRgb == null) {
            return true;
        }
        int n = this.lightMapRgb.getHeight();
        if (bl && n < 64) {
            return true;
        }
        int n2 = this.lightMapRgb.getWidth();
        if (n2 < 16) {
            LightMap.warn("Invalid lightmap width: " + n2);
            this.lightMapRgb = null;
            return true;
        }
        int n3 = 0;
        if (bl) {
            n3 = n2 * 16 * 2;
        }
        float f2 = 1.1666666f * (clientWorld.getSunBrightness(1.0f) - 0.2f);
        if (clientWorld.getTimeLightningFlash() > 0) {
            f2 = 1.0f;
        }
        f2 = Config.limitTo1(f2);
        float f3 = f2 * (float)(n2 - 1);
        float f4 = Config.limitTo1(f + 0.5f) * (float)(n2 - 1);
        float f5 = Config.limitTo1((float)Config.getGameSettings().gamma);
        boolean bl2 = f5 > 1.0E-4f;
        float[][] fArray = this.lightMapRgb.getColorsRgb();
        this.getLightMapColumn(fArray, f3, n3, n2, this.sunRgbs);
        this.getLightMapColumn(fArray, f4, n3 + 16 * n2, n2, this.torchRgbs);
        float[] fArray2 = new float[3];
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n4;
                for (n4 = 0; n4 < 3; ++n4) {
                    float f6 = Config.limitTo1(this.sunRgbs[i][n4] + this.torchRgbs[j][n4]);
                    if (bl2) {
                        float f7 = 1.0f - f6;
                        f7 = 1.0f - f7 * f7 * f7 * f7;
                        f6 = f5 * f7 + (1.0f - f5) * f6;
                    }
                    fArray2[n4] = f6;
                }
                n4 = (int)(fArray2[0] * 255.0f);
                int n5 = (int)(fArray2[1] * 255.0f);
                int n6 = (int)(fArray2[2] * 255.0f);
                nArray[i * 16 + j] = 0xFF000000 | n6 << 16 | n5 << 8 | n4;
            }
        }
        return false;
    }

    private void getLightMapColumn(float[][] fArray, float f, int n, int n2, float[][] fArray2) {
        int n3;
        int n4 = (int)Math.floor(f);
        if (n4 == (n3 = (int)Math.ceil(f))) {
            for (int i = 0; i < 16; ++i) {
                float[] fArray3 = fArray[n + i * n2 + n4];
                float[] fArray4 = fArray2[i];
                for (int j = 0; j < 3; ++j) {
                    fArray4[j] = fArray3[j];
                }
            }
        } else {
            float f2 = 1.0f - (f - (float)n4);
            float f3 = 1.0f - ((float)n3 - f);
            for (int i = 0; i < 16; ++i) {
                float[] fArray5 = fArray[n + i * n2 + n4];
                float[] fArray6 = fArray[n + i * n2 + n3];
                float[] fArray7 = fArray2[i];
                for (int j = 0; j < 3; ++j) {
                    fArray7[j] = fArray5[j] * f2 + fArray6[j] * f3;
                }
            }
        }
    }

    private static void dbg(String string) {
        Config.dbg("CustomColors: " + string);
    }

    private static void warn(String string) {
        Config.warn("CustomColors: " + string);
    }
}

