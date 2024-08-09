package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.shader.ShaderLink;
import net.mojang.blaze3d.matrix.MatrixStack;

public class RoundedShader implements IAccess {
    private final ShaderLink program = ShaderLink.create("rounded");

    public void draw(float x, float y, float width, float height, float radius, int color) {
        float min = Math.min(width, height);

        float red = ColorUtil.redf(color);
        float green = ColorUtil.greenf(color);
        float blue = ColorUtil.bluef(color);
        float alpha = ColorUtil.alphaf(color);

        program.init();
        program.setUniformf("location", x, y);
        program.setUniformf("size", width, height);
        program.setUniformf("radius", Math.min((min / 2F), radius));
        program.setUniformf("color", red, green, blue, alpha);
        RenderUtil.start();
        ShaderLink.drawQuads(x, y, width, height);
        RenderUtil.stop();
        program.unload();
    }

    public void draw(MatrixStack matrixStack, float x, float y, float width, float height, float radius, int color) {
        float min = Math.min(width, height);

        float red = ColorUtil.redf(color);
        float green = ColorUtil.greenf(color);
        float blue = ColorUtil.bluef(color);
        float alpha = ColorUtil.alphaf(color);

        program.init();
        program.setUniformf("location", x, y);
        program.setUniformf("size", width, height);
        program.setUniformf("radius", Math.min((min / 2F), radius));
        program.setUniformf("color", red, green, blue, alpha);
        RenderUtil.start();
        ShaderLink.drawQuads(matrixStack, x, y, width, height);
        RenderUtil.stop();
        program.unload();
    }
}
