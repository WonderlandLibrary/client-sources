/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.Properties;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomLoadingScreens;

public class CustomLoadingScreen {
    private ResourceLocation locationTexture;
    private int scaleMode = 0;
    private int scale = 2;
    private boolean center;
    private static final int SCALE_DEFAULT = 2;
    private static final int SCALE_MODE_FIXED = 0;
    private static final int SCALE_MODE_FULL = 1;
    private static final int SCALE_MODE_STRETCH = 2;

    public CustomLoadingScreen(ResourceLocation resourceLocation, int n, int n2, boolean bl) {
        this.locationTexture = resourceLocation;
        this.scaleMode = n;
        this.scale = n2;
        this.center = bl;
    }

    public static CustomLoadingScreen parseScreen(String string, int n, Properties properties) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        int n2 = CustomLoadingScreen.parseScaleMode(CustomLoadingScreen.getProperty("scaleMode", n, properties));
        int n3 = n2 == 0 ? 2 : 1;
        int n4 = CustomLoadingScreen.parseScale(CustomLoadingScreen.getProperty("scale", n, properties), n3);
        boolean bl = Config.parseBoolean(CustomLoadingScreen.getProperty("center", n, properties), false);
        return new CustomLoadingScreen(resourceLocation, n2, n4, bl);
    }

    private static String getProperty(String string, int n, Properties properties) {
        if (properties == null) {
            return null;
        }
        String string2 = properties.getProperty("dim" + n + "." + string);
        return string2 != null ? string2 : properties.getProperty(string);
    }

    private static int parseScaleMode(String string) {
        if (string == null) {
            return 1;
        }
        if ((string = string.toLowerCase().trim()).equals("fixed")) {
            return 1;
        }
        if (string.equals("full")) {
            return 0;
        }
        if (string.equals("stretch")) {
            return 1;
        }
        CustomLoadingScreens.warn("Invalid scale mode: " + string);
        return 1;
    }

    private static int parseScale(String string, int n) {
        if (string == null) {
            return n;
        }
        int n2 = Config.parseInt(string = string.trim(), -1);
        if (n2 < 1) {
            CustomLoadingScreens.warn("Invalid scale: " + string);
            return n;
        }
        return n2;
    }

    public void drawBackground(int n, int n2) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        Config.getTextureManager().bindTexture(this.locationTexture);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 16 * this.scale;
        float f2 = (float)n / f;
        float f3 = (float)n2 / f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        if (this.center) {
            f4 = (f - (float)n) / (f * 2.0f);
            f5 = (f - (float)n2) / (f * 2.0f);
        }
        switch (this.scaleMode) {
            case 1: {
                f = Math.max(n, n2);
                f2 = (float)(this.scale * n) / f;
                f3 = (float)(this.scale * n2) / f;
                if (!this.center) break;
                f4 = (float)this.scale * (f - (float)n) / (f * 2.0f);
                f5 = (float)this.scale * (f - (float)n2) / (f * 2.0f);
                break;
            }
            case 2: {
                f2 = this.scale;
                f3 = this.scale;
                f4 = 0.0f;
                f5 = 0.0f;
            }
        }
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(0.0, n2, 0.0).tex(f4, f5 + f3).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(n, n2, 0.0).tex(f4 + f2, f5 + f3).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(n, 0.0, 0.0).tex(f4 + f2, f5).color(255, 255, 255, 255).endVertex();
        bufferBuilder.pos(0.0, 0.0, 0.0).tex(f4, f5).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
    }
}

