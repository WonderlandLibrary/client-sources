/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

public class Spectrum {
    private static int adjust(float f, float f2, float f3) {
        if ((double)f == 0.0) {
            return 1;
        }
        return (int)Math.round(255.0 * Math.pow(f * f2, f3));
    }

    public static int wavelengthToRGB(float f) {
        float f2;
        float f3;
        float f4;
        float f5 = 0.8f;
        int n = (int)f;
        if (n < 380) {
            f4 = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
        } else if (n < 440) {
            f4 = -(f - 440.0f) / 60.0f;
            f3 = 0.0f;
            f2 = 1.0f;
        } else if (n < 490) {
            f4 = 0.0f;
            f3 = (f - 440.0f) / 50.0f;
            f2 = 1.0f;
        } else if (n < 510) {
            f4 = 0.0f;
            f3 = 1.0f;
            f2 = -(f - 510.0f) / 20.0f;
        } else if (n < 580) {
            f4 = (f - 510.0f) / 70.0f;
            f3 = 1.0f;
            f2 = 0.0f;
        } else if (n < 645) {
            f4 = 1.0f;
            f3 = -(f - 645.0f) / 65.0f;
            f2 = 0.0f;
        } else if (n <= 780) {
            f4 = 1.0f;
            f3 = 0.0f;
            f2 = 0.0f;
        } else {
            f4 = 0.0f;
            f3 = 0.0f;
            f2 = 0.0f;
        }
        float f6 = 380 <= n && n <= 419 ? 0.3f + 0.7f * (f - 380.0f) / 40.0f : (420 <= n && n <= 700 ? 1.0f : (701 <= n && n <= 780 ? 0.3f + 0.7f * (780.0f - f) / 80.0f : 0.0f));
        int n2 = Spectrum.adjust(f4, f6, f5);
        int n3 = Spectrum.adjust(f3, f6, f5);
        int n4 = Spectrum.adjust(f2, f6, f5);
        return 0xFF000000 | n2 << 16 | n3 << 8 | n4;
    }
}

