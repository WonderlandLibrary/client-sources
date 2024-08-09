package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.game.IWindow;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.shader.ShaderLink;
import net.mojang.blaze3d.matrix.MatrixStack;

public class RoundedWithNoiseShader implements IRenderAccess, IWindow {
    private final ShaderLink program = ShaderLink.create("roundedWithNoise");

    public void draw(MatrixStack matrix, float x, float y, float xEnd, float yEnd, float round, float alpha) {
        if (alpha == 0.0f) {
            return;
        }

        program.init();
        program.setUniformf("resolution", mc.getMainWindow().getFramebufferWidth(), mc.getMainWindow().getFramebufferHeight());
        program.setUniformf("round", round);
        program.setUniformf("alpha", alpha);
        float deltaX = xEnd - x;
        float deltaY = yEnd - y;
        program.setUniformf("size", deltaX * 2.0f - round / 2.0f, deltaY * 2.0f - round / 2.0f);
        program.setUniformf("start", x * 2.0f + deltaX, y * 2.0f + deltaY);
        RenderUtil.start();
        RenderUtil.allocRect(matrix, x, y, xEnd, yEnd);
        RenderUtil.stop();
        program.unload();
    }


}
