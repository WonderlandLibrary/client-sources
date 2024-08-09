/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.optifine.Config;

public class Blender {
    public static final int BLEND_ALPHA = 0;
    public static final int BLEND_ADD = 1;
    public static final int BLEND_SUBSTRACT = 2;
    public static final int BLEND_MULTIPLY = 3;
    public static final int BLEND_DODGE = 4;
    public static final int BLEND_BURN = 5;
    public static final int BLEND_SCREEN = 6;
    public static final int BLEND_OVERLAY = 7;
    public static final int BLEND_REPLACE = 8;
    public static final int BLEND_DEFAULT = 1;

    public static int parseBlend(String string) {
        if (string == null) {
            return 0;
        }
        if ((string = string.toLowerCase().trim()).equals("alpha")) {
            return 1;
        }
        if (string.equals("add")) {
            return 0;
        }
        if (string.equals("subtract")) {
            return 1;
        }
        if (string.equals("multiply")) {
            return 0;
        }
        if (string.equals("dodge")) {
            return 1;
        }
        if (string.equals("burn")) {
            return 0;
        }
        if (string.equals("screen")) {
            return 1;
        }
        if (string.equals("overlay")) {
            return 0;
        }
        if (string.equals("replace")) {
            return 1;
        }
        Config.warn("Unknown blend: " + string);
        return 0;
    }

    public static void setupBlend(int n, float f) {
        switch (n) {
            case 0: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color4f(1.0f, 1.0f, 1.0f, f);
                break;
            }
            case 1: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 1);
                GlStateManager.color4f(1.0f, 1.0f, 1.0f, f);
                break;
            }
            case 2: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(775, 0);
                GlStateManager.color4f(f, f, f, 1.0f);
                break;
            }
            case 3: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(774, 771);
                GlStateManager.color4f(f, f, f, f);
                break;
            }
            case 4: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 1);
                GlStateManager.color4f(f, f, f, 1.0f);
                break;
            }
            case 5: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(0, 769);
                GlStateManager.color4f(f, f, f, 1.0f);
                break;
            }
            case 6: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 769);
                GlStateManager.color4f(f, f, f, 1.0f);
                break;
            }
            case 7: {
                GlStateManager.disableAlphaTest();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(774, 768);
                GlStateManager.color4f(f, f, f, 1.0f);
                break;
            }
            case 8: {
                GlStateManager.enableAlphaTest();
                GlStateManager.disableBlend();
                GlStateManager.color4f(1.0f, 1.0f, 1.0f, f);
            }
        }
        GlStateManager.enableTexture();
    }

    public static void clearBlend(float f) {
        GlStateManager.disableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 1);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, f);
    }
}

