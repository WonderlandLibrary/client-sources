package com.alan.clients.util.shader.impl;

import com.alan.clients.util.shader.base.RiseShaderProgram;
import com.alan.clients.util.shader.base.ShaderUniforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class RTriGQShader {
    private final RiseShaderProgram program = new RiseShaderProgram("trirgq.glsl", "vertex.vsh");

    /**
     * Draws a rounded rectangle at the given coordinates with the given lengths
     *
     * @param x           The top left x coordinate of the rectangle
     * @param y           The top y coordinate of the rectangle
     * @param width       The width which is used to determine the second x rectangle
     * @param height      The height which is used to determine the second y rectangle
     * @param radius      The radius for the corners of the rectangles (>0)
     * @param firstColor  The first color used
     * @param secondColor The second color used
     * @param vertical    Whether the gradient is horizontal or vertical
     */
    public void draw(float x, float y, float width, float height, float radius, Color firstColor, Color secondColor, Color thirdColor, boolean vertical, boolean leftTop, boolean rightTop, boolean rightBottom, boolean leftBottom) {
        int programId = this.program.getProgramId();
        this.program.start();

        ShaderUniforms.uniform2f(programId, "u_size", width, height);
        ShaderUniforms.uniform1f(programId, "u_radius", radius);
        ShaderUniforms.uniform4f(programId, "u_first_color", firstColor.getRed() / 255.0F, firstColor.getGreen() / 255.0F, firstColor.getBlue() / 255.0F, firstColor.getAlpha() / 255.0F);
        ShaderUniforms.uniform4f(programId, "u_second_color", secondColor.getRed() / 255.0F, secondColor.getGreen() / 255.0F, secondColor.getBlue() / 255.0F, secondColor.getAlpha() / 255.0F);
        ShaderUniforms.uniform4f(programId, "u_third_color", thirdColor.getRed() / 255.0F, thirdColor.getGreen() / 255.0F, thirdColor.getBlue() / 255.0F, thirdColor.getAlpha() / 255.0F);
        ShaderUniforms.uniform1i(programId, "u_direction", vertical ? 1 : 0);
        ShaderUniforms.uniform1f(programId, "u_time", (System.currentTimeMillis() - Minecraft.getMinecraft().getStartMillisTime()) / 1000F);
        ShaderUniforms.uniform4f(programId, "u_edges", leftTop ? 1.0F : 0.0F, rightTop ? 1.0F : 0.0F, rightBottom ? 1.0F : 0.0F, leftBottom ? 1.0F : 0.0F);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RiseShaderProgram.drawQuad(x, y, width, height);
        GlStateManager.disableBlend();
        RiseShaderProgram.stop();
    }

    /**
     * Draws a rounded rectangle at the given coordinates with the given lengths
     *
     * @param x           The top left x coordinate of the rectangle
     * @param y           The top y coordinate of the rectangle
     * @param width       The width which is used to determine the second x rectangle
     * @param height      The height which is used to determine the second y rectangle
     * @param radius      The radius for the corners of the rectangles (>0)
     * @param firstColor  The first color used
     * @param secondColor The second color used
     * @param vertical    Whether the gradient is horizontal or vertical
     */
    public void draw(double x, double y, double width, double height, double radius, Color firstColor, Color secondColor, boolean vertical) {
        draw((float) x, (float) y, (float) width, (float) height, (float) radius, firstColor, secondColor, firstColor,  vertical, true, true, true, true);
    }
}
