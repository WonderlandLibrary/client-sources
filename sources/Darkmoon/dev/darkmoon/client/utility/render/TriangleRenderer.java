package dev.darkmoon.client.utility.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class TriangleRenderer {
    public static void renderTriangle(float scale, float x, float y, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        float alpha = (float)(color >> 24 & 255) / 255.0F;

        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y + scale, 0.0D).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + scale, y + scale, 0.0D).color(red, green, blue, alpha).endVertex();
        buffer.pos(x + scale / 2, y, 0.0D).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
}