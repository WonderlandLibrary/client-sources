package club.strifeclient.util.rendering;

import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class DrawUtil extends MinecraftUtil {

    public static void drawRect(final float x, final float y, final float w, final float h, final Color color) {
        drawRect(GL_QUADS, x, y, w, h, color);
    }

    public static void drawRect(final int mode, final double x, final double y, final double w, final double h, final Color color) {
        drawRect(mode, (float)x, (float)y, (float)w, (float)h, color);
    }

    public static void drawRect(final int mode, final float x, final float y, final float w, final float h, final Color color) {
        glPushMatrix();
        GlStateManager.enableBlend();
        glDisable(GL_TEXTURE_2D);
        glBegin(mode);
        ColorUtil.doColor(color.getRGB());
        glVertex2f(w, y);
        glVertex2f(x, y);
        glVertex2f(x, h);
        glVertex2f(w, h);
        glEnd();
        GlStateManager.disableBlend();
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static void drawGradientRect(final float x, final float y, final float w, final float h, final boolean perpendicular, final boolean alpha, final Color firstColor, final Color secondColor) {
        drawGradientRect(GL_QUADS, x, y, w, h, perpendicular, alpha, firstColor, secondColor);
    }

    public static void drawGradientRect(final int mode, final float x, final float y, final float w, final float h, final boolean perpendicular, final boolean alpha, final Color firstColor, final Color secondColor) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        if(!alpha) glDisable(GL_ALPHA_TEST);
        glEnable(GL_BLEND);
        glShadeModel(GL_SMOOTH);
        glBegin(mode);
        final int first = firstColor.getRGB();
        final int second = secondColor.getRGB();
        ColorUtil.doColor(first);
        if (perpendicular) {
            glVertex2f(w, y);
            ColorUtil.doColor(second);
            glVertex2f(x, y);
            glVertex2f(x, h);
            ColorUtil.doColor(first);
            glVertex2f(w, h);
        } else {
            glVertex2f(w, y);
            glVertex2f(x, y);
            ColorUtil.doColor(second);
            glVertex2f(x, h);
            glVertex2f(w, h);
        }
        glEnd();
        glShadeModel(GL_FLAT);
        if(!alpha) glEnable(GL_ALPHA_TEST);
        glDisable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static void drawPoint(final float x, final float y, final float size, final Color color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
        glEnable(GL_BLEND);
        glPointSize(size);
        glBegin(GL_POINTS);
        ColorUtil.doColor(color.getRGB());
        glVertex2f(x, y);
        glEnd();
        glDisable(GL_BLEND);
        glDisable(GL_POINT_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public static void drawCenteredPoint(final float x, final float y, final float width, final float height, final float size, final Color color) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glTranslatef(-x, -y, 0);
        glTranslatef(width / 2, height / 2, 0);
        drawPoint(x, y , size, color);
        glPopMatrix();
    }
}
