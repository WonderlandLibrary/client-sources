/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public class ColorHelper {

    public static class PackedColor {
        public static int getAlpha(int n) {
            return n >>> 24;
        }

        public static int getRed(int n) {
            return n >> 16 & 0xFF;
        }

        public static int getGreen(int n) {
            return n >> 8 & 0xFF;
        }

        public static int getBlue(int n) {
            return n & 0xFF;
        }

        public static int packColor(int n, int n2, int n3, int n4) {
            return n << 24 | n2 << 16 | n3 << 8 | n4;
        }

        public static int blendColors(int n, int n2) {
            return PackedColor.packColor(PackedColor.getAlpha(n) * PackedColor.getAlpha(n2) / 255, PackedColor.getRed(n) * PackedColor.getRed(n2) / 255, PackedColor.getGreen(n) * PackedColor.getGreen(n2) / 255, PackedColor.getBlue(n) * PackedColor.getBlue(n2) / 255);
        }
    }
}

