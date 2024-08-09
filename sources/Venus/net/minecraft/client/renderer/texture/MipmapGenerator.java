/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.Util;
import net.optifine.Mipmaps;
import net.optifine.texture.IColorBlender;

public class MipmapGenerator {
    private static final float[] POWS22 = Util.make(new float[256], MipmapGenerator::lambda$static$0);

    public static NativeImage[] generateMipmaps(NativeImage nativeImage, int n) {
        return MipmapGenerator.generateMipmaps(nativeImage, n, null);
    }

    public static NativeImage[] generateMipmaps(NativeImage nativeImage, int n, IColorBlender iColorBlender) {
        NativeImage[] nativeImageArray = new NativeImage[n + 1];
        nativeImageArray[0] = nativeImage;
        if (n > 0) {
            boolean bl = false;
            for (int i = 1; i <= n; ++i) {
                NativeImage nativeImage2 = nativeImageArray[i - 1];
                NativeImage nativeImage3 = new NativeImage(nativeImage2.getWidth() >> 1, nativeImage2.getHeight() >> 1, false);
                int n2 = nativeImage3.getWidth();
                int n3 = nativeImage3.getHeight();
                for (int j = 0; j < n2; ++j) {
                    for (int k = 0; k < n3; ++k) {
                        if (iColorBlender != null) {
                            nativeImage3.setPixelRGBA(j, k, iColorBlender.blend(nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 1), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 1)));
                            continue;
                        }
                        nativeImage3.setPixelRGBA(j, k, MipmapGenerator.alphaBlend(nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 0), nativeImage2.getPixelRGBA(j * 2 + 0, k * 2 + 1), nativeImage2.getPixelRGBA(j * 2 + 1, k * 2 + 1), bl));
                    }
                }
                nativeImageArray[i] = nativeImage3;
            }
        }
        return nativeImageArray;
    }

    private static int alphaBlend(int n, int n2, int n3, int n4, boolean bl) {
        return Mipmaps.alphaBlend(n, n2, n3, n4);
    }

    private static int gammaBlend(int n, int n2, int n3, int n4, int n5) {
        float f = MipmapGenerator.getPow22(n >> n5);
        float f2 = MipmapGenerator.getPow22(n2 >> n5);
        float f3 = MipmapGenerator.getPow22(n3 >> n5);
        float f4 = MipmapGenerator.getPow22(n4 >> n5);
        float f5 = (float)((double)((float)Math.pow((double)(f + f2 + f3 + f4) * 0.25, 0.45454545454545453)));
        return (int)((double)f5 * 255.0);
    }

    private static float getPow22(int n) {
        return POWS22[n & 0xFF];
    }

    private static void lambda$static$0(float[] fArray) {
        for (int i = 0; i < fArray.length; ++i) {
            fArray[i] = (float)Math.pow((float)i / 255.0f, 2.2);
        }
    }
}

