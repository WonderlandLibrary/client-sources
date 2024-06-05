package xyz.vergoclient.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import xyz.vergoclient.util.animations.Animation;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static xyz.vergoclient.ui.notifications.ingame.NotificationManager.interpolate;
import static xyz.vergoclient.util.main.RenderUtils2.disableRender2D;
import static xyz.vergoclient.util.main.RenderUtils2.enableRender2D;

public class RenderUtils3 {

    private static float
            scissorX,
            scissorY,
            scissorWidth,
            scissorHeight,
            scissorSF;
    private static boolean isScissoring;

    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, Color rectColor, Color borderColor) {
        drawBorderedRect(x, y, width, height, borderWidth, rectColor.getRGB(), borderColor.getRGB());
    }

    public static float[] getScissor() {
        if (isScissoring) {
            return new float[] {scissorX,scissorY,scissorWidth,scissorHeight,scissorSF};
        }
        return new float[] {-1};
    }

    public static void drawLine2D(double x1, double y1, double x2, double y2, float width, int color) {
        enableRender2D();
        setColor(color);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        glVertex2d(x1, y1);
        glVertex2d(x2, y2);
        GL11.glEnd();
        disableRender2D();
    }

    public static void scissor(double x, double y, double width, double height, Runnable data) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        scissor(x, y, width, height);
        data.run();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void scissor(double x, double y, double width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        final double scale = sr.getScaleFactor();
        double finalHeight = height * scale;
        double finalY = (sr.getScaledHeight() - y) * scale;
        double finalX = x * scale;
        double finalWidth = width * scale;
        glScissor((int) finalX, (int) (finalY - finalHeight), (int) finalWidth, (int) finalHeight);
    }

    public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, int rectColor, int borderColor) {
        drawRect(x + borderWidth, y + borderWidth, width - borderWidth * 2.0F, height - borderWidth * 2.0F, rectColor);
        drawRect(x, y, width, borderWidth, borderColor);
        drawRect(x, y + borderWidth, borderWidth, height - borderWidth, borderColor);
        drawRect(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth, borderColor);
        drawRect(x + borderWidth, y + height - borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
    }

    public static int darker(int hexColor, int factor) {
        float alpha = (float) (hexColor >> 24 & 255);
        float red = Math.max((float) (hexColor >> 16 & 255) - (float) (hexColor >> 16 & 255) / (100.0F / (float) factor), 0.0F);
        float green = Math.max((float) (hexColor >> 8 & 255) - (float) (hexColor >> 8 & 255) / (100.0F / (float) factor), 0.0F);
        float blue = Math.max((float) (hexColor & 255) - (float) (hexColor & 255) / (100.0F / (float) factor), 0.0F);
        return (int) ((float) (((int) alpha << 24) + ((int) red << 16) + ((int) green << 8)) + blue);
    }

    public static void drawOutlinedRect(float x, float y, float width, float height, float lineSize, int lineColor) {
        drawRect(x, y, width, y + lineSize, lineColor);
        drawRect(x, height - lineSize, width, height, lineColor);
        drawRect(x, y + lineSize, x + lineSize, height - lineSize, lineColor);
        drawRect(width - lineSize, y + lineSize, width, height - lineSize, lineColor);
    }

    public static void drawRect(float x, float y, float width, float height, Color color) {
        drawRect(x, y, width, height, color.getRGB());
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        enableRender2D();
        setColor(color);
        GL11.glBegin(7);
        glVertex2d((double) x, (double) y);
        glVertex2d((double) (x + width), (double) y);
        glVertex2d((double) (x + width), (double) (y + height));
        glVertex2d((double) x, (double) (y + height));
        GL11.glEnd();
        disableRender2D();
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
        if (color == 16777215) color = Color.white.getRGB();
        if (borderColor == 16777215) borderColor = Color.white.getRGB();

        if (edgeRadius < 0.0F) {
            edgeRadius = 0.0F;
        }

        if (edgeRadius > width / 2.0F) {
            edgeRadius = width / 2.0F;
        }

        if (edgeRadius > height / 2.0F) {
            edgeRadius = height / 2.0F;
        }

        drawRect(x + edgeRadius, y + edgeRadius, width - edgeRadius * 2.0F, height - edgeRadius * 2.0F, color);
        drawRect(x + edgeRadius, y, width - edgeRadius * 2.0F, edgeRadius, color);
        drawRect(x + edgeRadius, y + height - edgeRadius, width - edgeRadius * 2.0F, edgeRadius, color);
        drawRect(x, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
        drawRect(x + width - edgeRadius, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0F, color);
        enableRender2D();
        setColor(color);
        GL11.glBegin(6);
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        glVertex2d((double) centerX, (double) centerY);
        int vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        int i;
        double angleRadians;
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        setColor(borderColor);
        GL11.glLineWidth(borderWidth);
        GL11.glBegin(3);
        centerX = x + edgeRadius;
        centerY = y + edgeRadius;
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        glVertex2d((double) (x + edgeRadius), (double) y);
        glVertex2d((double) (x + width - edgeRadius), (double) y);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        glVertex2d((double) (x + width), (double) (y + edgeRadius));
        glVertex2d((double) (x + width), (double) (y + height - edgeRadius));
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        glVertex2d((double) (x + width - edgeRadius), (double) (y + height));
        glVertex2d((double) (x + edgeRadius), (double) (y + height));
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        glVertex2d((double) x, (double) (y + height - edgeRadius));
        glVertex2d((double) x, (double) (y + edgeRadius));
        GL11.glEnd();
        disableRender2D();
    }

    public static void setColor(int colorHex) {
        float alpha = (float) (colorHex >> 24 & 255) / 255.0F;
        float red = (float) (colorHex >> 16 & 255) / 255.0F;
        float green = (float) (colorHex >> 8 & 255) / 255.0F;
        float blue = (float) (colorHex & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void doGlScissor(int x, int y, float width, float height, float scale) {
        int scaleFactor = 1;

        while (scaleFactor < scale && mc.displayWidth / (scaleFactor + 1) >= 320 && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }

        GL11.glScissor((int) (x * scaleFactor), (int) (mc.displayHeight - (y + height) * scaleFactor), (int) (width * scaleFactor), (int) (height * scaleFactor));
    }

    public static void beginCrop(float x, float y, float width, float height, float scaleFactor) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (x * (float) scaleFactor), (int) ((float) Display.getHeight() - y * (float) scaleFactor), (int) (width * (float) scaleFactor), (int) (height * (float) scaleFactor));
        isScissoring = true;
        scissorX = x;
        scissorY = y;
        scissorWidth = width;
        scissorHeight = height;
        scissorSF = scaleFactor;
    }

    public static void endCrop() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        isScissoring = false;
    }

    public static int getDisplayWidth() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int displayWidth = scaledResolution.getScaledWidth();
        return displayWidth;
    }

    public static int getDisplayHeight() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int displayHeight = scaledResolution.getScaledHeight();
        return displayHeight;
    }

    // New ClickGUi
    public static void drawClickGuiArrow(float x, float y, float size, Animation animation, int color) {
        glTranslatef(x, y, 0);
        GLUtils.setup2DRendering(() -> GLUtils.render(GL_TRIANGLE_STRIP, () -> {
            setColor(color);


            double interpolation = interpolate(0.0, size / 2.0, animation.getOutput());
            if (animation.getOutput() >= .48) {
                glVertex2d(size / 2f, interpolate(size / 2.0, 0.0, animation.getOutput()));
            }
            glVertex2d(0, interpolation);

            if (animation.getOutput() < .48) {
                glVertex2d(size / 2f, interpolate(size / 2.0, 0.0, animation.getOutput()));
            }
            glVertex2d(size, interpolation);

        }));
        glTranslatef(-x, -y, 0);
    }
}