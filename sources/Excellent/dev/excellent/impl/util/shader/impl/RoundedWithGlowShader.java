package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.game.IWindow;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.shader.ShaderLink;
import net.mojang.blaze3d.matrix.MatrixStack;

public class RoundedWithGlowShader implements IRenderAccess, IWindow {
    private final ShaderLink program = ShaderLink.create("roundedWithGlow");
    private final ShaderLink gradient = ShaderLink.create("roundedGradientWithGlow");

    public void draw(MatrixStack matrix, float x, float y, float xEnd, float yEnd, int color, float round, float softness) {
        float[] rgbaf = ColorUtil.getRGBAf(color);
        if (rgbaf[3] == 0) {
            return;
        }

        program.init();
        program.setUniformf("resolution", mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
        program.setUniformf("round", round);
        program.setUniformf("color", rgbaf);
        program.setUniformf("softness", softness);
        float deltaX = xEnd - x;
        float deltaY = yEnd - y;
        program.setUniformf("size", deltaX * 2.0f - round / 2.0f, deltaY * 2.0f - round / 2.0f);
        program.setUniformf("start", x * 2.0f + deltaX, y * 2.0f + deltaY);
        RenderUtil.start();
        RenderUtil.allocRect(matrix, x - softness * 2, y - softness * 2, xEnd + softness * 2, yEnd + softness * 2);
        RenderUtil.stop();
        program.unload();
    }

    public void drawGradient(MatrixStack matrix, float x, float y, float xEnd, float yEnd, int color1, int color3,
                             int color4, int color2, float round, float alpha, float softness) {

        gradient.init();
        gradient.setUniformf("resolution", mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
        gradient.setUniformf("round", round);
        gradient.setUniformf("color1", ColorUtil.getRGBAf(color1));
        gradient.setUniformf("color2", ColorUtil.getRGBAf(color2));
        gradient.setUniformf("color3", ColorUtil.getRGBAf(color3));
        gradient.setUniformf("color4", ColorUtil.getRGBAf(color4));
        gradient.setUniformf("alpha", alpha);
        gradient.setUniformf("softness", softness);
        float deltaX = xEnd - x;
        float deltaY = yEnd - y;
        gradient.setUniformf("size", deltaX * 2.0f - round / 2.0f, deltaY * 2.0f - round / 2.0f);
        gradient.setUniformf("start", x * 2.0f + deltaX, y * 2.0f + deltaY);
        RenderUtil.start();
        RenderUtil.allocRect(matrix, x - softness * 2, y - softness * 2, xEnd + softness * 2, yEnd + softness * 2);
        RenderUtil.stop();
        gradient.unload();
    }
}
