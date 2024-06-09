package me.teus.eclipse.utils;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RectUtils {

    public static void rect(final double x, final double y, final double width, final double height, final int color) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        setColor(color);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        {
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x + width, y);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x, y + height);
        }
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        glEnable(GL11.GL_CULL_FACE);
        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_BLEND);
        setColor(Color.white.getRGB());
    }

    private static void setColor(int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        glColor4f(r, g, b, a);
    }
}
