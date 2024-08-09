package dev.excellent.impl.util.shader.impl;

import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.shader.ShaderLink;

public class RoundedGradientShader {
    private final ShaderLink program = ShaderLink.create("roundedGradient");

    public void draw(float x, float y, float width, float height, float radius, int color1, int color2, int color3, int color4) {
        float min = Math.min(width, height);

        float red1 = (color1 >> 16 & 0xFF) / 255F;
        float green1 = (color1 >> 8 & 0xFF) / 255F;
        float blue1 = (color1 & 0xFF) / 255F;
        float alpha1 = (color1 >> 24 & 0xFF) / 255F;

        float red2 = (color2 >> 16 & 0xFF) / 255F;
        float green2 = (color2 >> 8 & 0xFF) / 255F;
        float blue2 = (color2 & 0xFF) / 255F;
        float alpha2 = (color2 >> 24 & 0xFF) / 255F;

        float red3 = (color3 >> 16 & 0xFF) / 255F;
        float green3 = (color3 >> 8 & 0xFF) / 255F;
        float blue3 = (color3 & 0xFF) / 255F;
        float alpha3 = (color3 >> 24 & 0xFF) / 255F;

        float red4 = (color4 >> 16 & 0xFF) / 255F;
        float green4 = (color4 >> 8 & 0xFF) / 255F;
        float blue4 = (color4 & 0xFF) / 255F;
        float alpha4 = (color4 >> 24 & 0xFF) / 255F;

        program.init();
        program.setUniformf("location", x, y);
        program.setUniformf("size", width, height);
        program.setUniformf("radius", Math.min((min / 2F), radius));
        program.setUniformf("color1", red1, green1, blue1, alpha1);
        program.setUniformf("color2", red2, green2, blue2, alpha2);
        program.setUniformf("color3", red3, green3, blue3, alpha3);
        program.setUniformf("color4", red4, green4, blue4, alpha4);
        RenderUtil.start();
        ShaderLink.drawQuads(x, y + height, width, -height);
        RenderUtil.stop();
        program.unload();
    }

    public void draw(double x, double y, double width, double height, double radius, int color1, int color2, int color3, int color4) {
        draw((float) x, (float) y, (float) width, (float) height, (float) radius, color1, color2, color3, color4);
    }
}