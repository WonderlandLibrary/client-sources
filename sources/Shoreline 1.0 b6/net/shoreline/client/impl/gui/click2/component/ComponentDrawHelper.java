package net.shoreline.client.impl.gui.click2.component;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.shoreline.client.init.Programs;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author xgraza
 * @since 03/31/24
 */
public interface ComponentDrawHelper
{

    /**
     * Draws a rounded rectangle with a shader
     * @param x
     * @param y
     * @param width
     * @param height
     * @param radius
     * @param color
     */
    default void drawRoundedRectangle(final float x, final float y,
                                      final float width, final float height,
                                      final float radius, final Color color)
    {
        Programs.ROUNDED_RECTANGLE.setDimensions(width, height);
        Programs.ROUNDED_RECTANGLE.setRadius(radius);
        Programs.ROUNDED_RECTANGLE.setSoftness(1.0f);
        Programs.ROUNDED_RECTANGLE.setColor(color);

        Programs.ROUNDED_RECTANGLE.use();

        RenderSystem.enableBlend();

        //RenderSystem.setShaderTexture(0, Programs.ROUNDED_RECTANGLE.getId());
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(x, y, 0.0f).texture(0, 0).next();
        buffer.vertex(x, y + height, 0.0f).texture(0, 1).next();
        buffer.vertex(x + width, y + height, 0.0f).texture(1, 1).next();
        buffer.vertex(x + width, y, 0.0f).texture(1, 0).next();
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.disableBlend();

        Programs.ROUNDED_RECTANGLE.stopUse();
    }
}
