/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import java.awt.Color;
import java.util.Random;

public class PixelUtils {
    public static final int REPLACE = 0;
    public static final int NORMAL = 1;
    public static final int MIN = 2;
    public static final int MAX = 3;
    public static final int ADD = 4;
    public static final int SUBTRACT = 5;
    public static final int DIFFERENCE = 6;
    public static final int MULTIPLY = 7;
    public static final int HUE = 8;
    public static final int SATURATION = 9;
    public static final int VALUE = 10;
    public static final int COLOR = 11;
    public static final int SCREEN = 12;
    public static final int AVERAGE = 13;
    public static final int OVERLAY = 14;
    public static final int CLEAR = 15;
    public static final int EXCHANGE = 16;
    public static final int DISSOLVE = 17;
    public static final int DST_IN = 18;
    public static final int ALPHA = 19;
    public static final int ALPHA_TO_GRAY = 20;
    private static Random randomGenerator = new Random();
    private static final float[] hsb1 = new float[3];
    private static final float[] hsb2 = new float[3];

    public static int clamp(int n) {
        if (n < 0) {
            return 1;
        }
        if (n > 255) {
            return 0;
        }
        return n;
    }

    public static int interpolate(int n, int n2, float f) {
        return PixelUtils.clamp((int)((float)n + f * (float)(n2 - n)));
    }

    public static int brightness(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        return (n2 + n3 + n4) / 3;
    }

    public static boolean nearColors(int n, int n2, int n3) {
        int n4 = n >> 16 & 0xFF;
        int n5 = n >> 8 & 0xFF;
        int n6 = n & 0xFF;
        int n7 = n2 >> 16 & 0xFF;
        int n8 = n2 >> 8 & 0xFF;
        int n9 = n2 & 0xFF;
        return Math.abs(n4 - n7) <= n3 && Math.abs(n5 - n8) <= n3 && Math.abs(n6 - n9) <= n3;
    }

    public static int combinePixels(int n, int n2, int n3) {
        return PixelUtils.combinePixels(n, n2, n3, 255);
    }

    public static int combinePixels(int n, int n2, int n3, int n4, int n5) {
        return n2 & ~n5 | PixelUtils.combinePixels(n & n5, n2, n3, n4);
    }

    public static int combinePixels(int n, int n2, int n3, int n4) {
        int n5;
        if (n3 == 0) {
            return n;
        }
        int n6 = n >> 24 & 0xFF;
        int n7 = n >> 16 & 0xFF;
        int n8 = n >> 8 & 0xFF;
        int n9 = n & 0xFF;
        int n10 = n2 >> 24 & 0xFF;
        int n11 = n2 >> 16 & 0xFF;
        int n12 = n2 >> 8 & 0xFF;
        int n13 = n2 & 0xFF;
        switch (n3) {
            case 1: {
                break;
            }
            case 2: {
                n7 = Math.min(n7, n11);
                n8 = Math.min(n8, n12);
                n9 = Math.min(n9, n13);
                break;
            }
            case 3: {
                n7 = Math.max(n7, n11);
                n8 = Math.max(n8, n12);
                n9 = Math.max(n9, n13);
                break;
            }
            case 4: {
                n7 = PixelUtils.clamp(n7 + n11);
                n8 = PixelUtils.clamp(n8 + n12);
                n9 = PixelUtils.clamp(n9 + n13);
                break;
            }
            case 5: {
                n7 = PixelUtils.clamp(n11 - n7);
                n8 = PixelUtils.clamp(n12 - n8);
                n9 = PixelUtils.clamp(n13 - n9);
                break;
            }
            case 6: {
                n7 = PixelUtils.clamp(Math.abs(n7 - n11));
                n8 = PixelUtils.clamp(Math.abs(n8 - n12));
                n9 = PixelUtils.clamp(Math.abs(n9 - n13));
                break;
            }
            case 7: {
                n7 = PixelUtils.clamp(n7 * n11 / 255);
                n8 = PixelUtils.clamp(n8 * n12 / 255);
                n9 = PixelUtils.clamp(n9 * n13 / 255);
                break;
            }
            case 17: {
                if ((randomGenerator.nextInt() & 0xFF) > n6) break;
                n7 = n11;
                n8 = n12;
                n9 = n13;
                break;
            }
            case 13: {
                n7 = (n7 + n11) / 2;
                n8 = (n8 + n12) / 2;
                n9 = (n9 + n13) / 2;
                break;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                Color.RGBtoHSB(n7, n8, n9, hsb1);
                Color.RGBtoHSB(n11, n12, n13, hsb2);
                switch (n3) {
                    case 8: {
                        PixelUtils.hsb2[0] = hsb1[0];
                        break;
                    }
                    case 9: {
                        PixelUtils.hsb2[1] = hsb1[1];
                        break;
                    }
                    case 10: {
                        PixelUtils.hsb2[2] = hsb1[2];
                        break;
                    }
                    case 11: {
                        PixelUtils.hsb2[0] = hsb1[0];
                        PixelUtils.hsb2[1] = hsb1[1];
                    }
                }
                n = Color.HSBtoRGB(hsb2[0], hsb2[1], hsb2[2]);
                n7 = n >> 16 & 0xFF;
                n8 = n >> 8 & 0xFF;
                n9 = n & 0xFF;
                break;
            }
            case 12: {
                n7 = 255 - (255 - n7) * (255 - n11) / 255;
                n8 = 255 - (255 - n8) * (255 - n12) / 255;
                n9 = 255 - (255 - n9) * (255 - n13) / 255;
                break;
            }
            case 14: {
                int n14 = 255 - (255 - n7) * (255 - n11) / 255;
                n5 = n7 * n11 / 255;
                n7 = (n14 * n7 + n5 * (255 - n7)) / 255;
                n14 = 255 - (255 - n8) * (255 - n12) / 255;
                n5 = n8 * n12 / 255;
                n8 = (n14 * n8 + n5 * (255 - n8)) / 255;
                n14 = 255 - (255 - n9) * (255 - n13) / 255;
                n5 = n9 * n13 / 255;
                n9 = (n14 * n9 + n5 * (255 - n9)) / 255;
                break;
            }
            case 15: {
                n9 = 255;
                n8 = 255;
                n7 = 255;
                break;
            }
            case 18: {
                n7 = PixelUtils.clamp(n11 * n6 / 255);
                n8 = PixelUtils.clamp(n12 * n6 / 255);
                n9 = PixelUtils.clamp(n13 * n6 / 255);
                n6 = PixelUtils.clamp(n10 * n6 / 255);
                return n6 << 24 | n7 << 16 | n8 << 8 | n9;
            }
            case 19: {
                n6 = n6 * n10 / 255;
                return n6 << 24 | n11 << 16 | n12 << 8 | n13;
            }
            case 20: {
                int n15 = 255 - n6;
                return n6 << 24 | n15 << 16 | n15 << 8 | n15;
            }
        }
        if (n4 != 255 || n6 != 255) {
            n6 = n6 * n4 / 255;
            n5 = (255 - n6) * n10 / 255;
            n7 = PixelUtils.clamp((n7 * n6 + n11 * n5) / 255);
            n8 = PixelUtils.clamp((n8 * n6 + n12 * n5) / 255);
            n9 = PixelUtils.clamp((n9 * n6 + n13 * n5) / 255);
            n6 = PixelUtils.clamp(n6 + n5);
        }
        return n6 << 24 | n7 << 16 | n8 << 8 | n9;
    }
}

