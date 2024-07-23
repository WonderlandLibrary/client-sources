package io.github.liticane.monoxide.util.render.updated;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class NewRenderUtil {

    public static void use(int mode, Runnable runnable) {
        glBegin(mode);
        runnable.run();
        glEnd();
    }

    public static void start() {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        glEnable(GL_BLEND);

        glBlendFunc(
                GL_SRC_ALPHA,
                GL_ONE_MINUS_SRC_ALPHA
        );

        glDisable(GL_TEXTURE_2D);
        glDisable(GL_CULL_FACE);

        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void stop() {
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();

        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);

        color(Color.WHITE);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public static void startSmooth() {
        glEnable(GL_POLYGON_SMOOTH);
        glEnable(GL_LINE_SMOOTH);
        glEnable(GL_POINT_SMOOTH);
    }

    public static void endSmooth() {
        glDisable(GL_POINT_SMOOTH);
        glDisable(GL_LINE_SMOOTH);
        glDisable(GL_POLYGON_SMOOTH);
    }

    public static void color(Color color) {
        if (color == null)
            return;

        glColor4d(
                color.getRed() / 255.0D,
                color.getGreen() / 255.0D,
                color.getBlue() / 255.0D,
                color.getAlpha() / 255.0D
        );
    }

    public static void color(int color) {
        glColor4d(
                (color >> 16 & 255) / 255.0D,
                (color >> 8 & 255) / 255.0D,
                (color & 255) / 255.0D,
                (color >> 24 & 255) / 255.0D
        );
    }

    public static void scale(float x, float y, float scale, Runnable runnable) {
        GlStateManager.pushMatrix();
        glTranslatef(x, y, 0);
        glScalef(scale, scale, 1);
        glTranslatef(-x, -y, 0);
        runnable.run();
        GlStateManager.popMatrix();
    }

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public static void rectangle(double x, double y, double width, double height, boolean hollow, Color color) {
        start();

        color(color);
        use(hollow ? GL_LINE_LOOP : GL_TRIANGLE_FAN, () -> {
            glVertex2d(x, y);
            glVertex2d(x + width, y);
            glVertex2d(x + width, y + height);
            glVertex2d(x, y + height);
        });

        stop();
    }

    public static void rectangle(double x, double y, double width, double height, Color color) {
        rectangle(x, y, width, height, false, color);
    }

    public static void horizontalGradient(double x, double y, double width, double height, boolean hollow, Color first, Color second) {
        start();

        glShadeModel(GL_SMOOTH);

        use(hollow ? GL_LINE_LOOP : GL_TRIANGLE_FAN, () -> {
            color(first);

            glVertex2d(x, y);
            glVertex2d(x, y + height);

            color(second);

            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        });

        glShadeModel(GL_FLAT);

        stop();
    }

    public static void horizontalGradient(double x, double y, double width, double height, Color first, Color second) {
        horizontalGradient(x, y, width, height, false, first, second);
    }

    public static void verticalGradient(double x, double y, double width, double height, boolean hollow, Color first, Color second) {
        start();

        glShadeModel(GL_SMOOTH);

        use(hollow ? GL_LINE_LOOP : GL_TRIANGLE_FAN, () -> {
            color(first);

            glVertex2d(x, y);
            glVertex2d(x + width, y);

            color(second);

            glVertex2d(x + width, y + height);
            glVertex2d(x, y + height);
        });

        glShadeModel(GL_FLAT);

        stop();
    }

    public static void circle(double x, double y, float radius, Color color) {
        start();

        use(GL_POLYGON, () -> {
            for (int i = 0; i <= 360; i++) {
                glVertex2d(
                        x + Math.sin(i * Math.PI / 180.0D) * radius,
                        y + Math.cos(i * Math.PI / 180.0D) * radius
                );
            }
        });

        stop();
    }

}
