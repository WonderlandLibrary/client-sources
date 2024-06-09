// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import java.awt.Color;
import xyz.niggfaclient.utils.shader.ShaderUtil;
import xyz.niggfaclient.utils.Utils;

public class GradientUtil extends Utils
{
    private static final ShaderUtil gradientShader;
    
    public static void drawGradient(final float x, final float y, final float width, final float height, final float alpha, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        final ScaledResolution sr = new ScaledResolution(GradientUtil.mc);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GradientUtil.gradientShader.init();
        GradientUtil.gradientShader.setUniformf("location", x * sr.getScaleFactor(), Minecraft.getMinecraft().displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor());
        GradientUtil.gradientShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        GradientUtil.gradientShader.setUniformf("alpha", alpha);
        GradientUtil.gradientShader.setUniformf("color1", bottomLeft.getRed() / 255.0f, bottomLeft.getGreen() / 255.0f, bottomLeft.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color2", topLeft.getRed() / 255.0f, topLeft.getGreen() / 255.0f, topLeft.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color3", bottomRight.getRed() / 255.0f, bottomRight.getGreen() / 255.0f, bottomRight.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color4", topRight.getRed() / 255.0f, topRight.getGreen() / 255.0f, topRight.getBlue() / 255.0f);
        ShaderUtil.drawQuads(x, y, width, height);
        GradientUtil.gradientShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradient2(final float x, final float y, final float width, final float height, final float alpha, final Color bottomLeft, final Color topLeft, final Color bottomRight, final Color topRight) {
        final ScaledResolution sr = new ScaledResolution(GradientUtil.mc);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GradientUtil.gradientShader.init();
        GradientUtil.gradientShader.setUniformf("location", x * sr.getScaleFactor(), Minecraft.getMinecraft().displayHeight - height * sr.getScaleFactor() - y * sr.getScaleFactor());
        GradientUtil.gradientShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        GradientUtil.gradientShader.setUniformf("alpha", alpha);
        GradientUtil.gradientShader.setUniformf("color1", bottomLeft.getRed() / 255.0f, bottomLeft.getGreen() / 255.0f, bottomLeft.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color2", topLeft.getRed() / 255.0f, topLeft.getGreen() / 255.0f, topLeft.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color3", bottomRight.getRed() / 255.0f, bottomRight.getGreen() / 255.0f, bottomRight.getBlue() / 255.0f);
        GradientUtil.gradientShader.setUniformf("color4", topRight.getRed() / 255.0f, topRight.getGreen() / 255.0f, topRight.getBlue() / 255.0f);
        ShaderUtil.drawQuads(x, y, width - x, height - y);
        GradientUtil.gradientShader.unload();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientLR(final float x, final float y, final float width, final float height, final float alpha, final Color left, final Color right) {
        drawGradient(x, y, width, height, alpha, left, left, right, right);
    }
    
    public static void drawGradientTB(final float x, final float y, final float width, final float height, final float alpha, final Color top, final Color bottom) {
        drawGradient(x, y, width, height, alpha, bottom, top, bottom, top);
    }
    
    public static void drawGradientLR2(final float x, final float y, final float width, final float height, final float alpha, final Color left, final Color right) {
        drawGradient2(x, y, width, height, alpha, left, left, right, right);
    }
    
    public static void drawGradientTB2(final float x, final float y, final float width, final float height, final float alpha, final Color top, final Color bottom) {
        drawGradient2(x, y, width, height, alpha, bottom, top, bottom, top);
    }
    
    static {
        gradientShader = new ShaderUtil("client/shaders/gradient.frag");
    }
}
