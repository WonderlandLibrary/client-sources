package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtils2 {

    public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, Color rectColor, Color borderColor) {
        drawBorderedRect(x, y, width, height, borderWidth, rectColor.getRGB(), borderColor.getRGB());
    }

    public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, int rectColor, int borderColor) {
        drawRect(x + borderWidth, y + borderWidth, width - borderWidth * 2.0F, height - borderWidth * 2.0F, rectColor);
        drawRect(x, y, width, borderWidth, borderColor);
        drawRect(x, y + borderWidth, borderWidth, height - borderWidth, borderColor);
        drawRect(x + width - borderWidth, y + borderWidth, borderWidth, height - borderWidth, borderColor);
        drawRect(x + borderWidth, y + height - borderWidth, width - borderWidth * 2.0F, borderWidth, borderColor);
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, int color) {
        //Left
        drawRect(left - 0.5f, top + 0.5f, left, bottom - 0.5f, color);
        //Right
        drawRect(right, top + 0.5f, right + 0.5f, bottom - 0.5f, color);
        //Top
        drawRect(left + 0.5f, top - 0.5f, right - 0.5f, top, color);
        //Bottom
        drawRect(left + 0.5f, bottom, right - 0.5f, bottom + 0.5f, color);
        drawRect(left, top, right, bottom, color);
    }

    public static void drawBorderedRect(float left, float top, float right, float bottom, float thickness, int color) {
        //Left
        drawRect(left - thickness, top, left, bottom, color);
        //Right
        drawRect(right, top, right + thickness, bottom, color);
        //Top
        drawRect(left, top + thickness, right, top, color);
        //Bottom
        drawRect(left, bottom, right, bottom + thickness, color);
    }

    public static void setColor(int colorHex) {
        float alpha = (float) (colorHex >> 24 & 255) / 255.0F;
        float red = (float) (colorHex >> 16 & 255) / 255.0F;
        float green = (float) (colorHex >> 8 & 255) / 255.0F;
        float blue = (float) (colorHex & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    // This method and the two underneath are from Flux
    // UPDATE: before the leak.
    public static void setup2DRendering(Runnable f) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        f.run();
        glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public static void render(int mode, Runnable render){
        glBegin(mode);
        render.run();
        glEnd();
    }

    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void drawGoodCircle(double x, double y, float radius, int color) {
        color(color);
        setup2DRendering(() -> {
            glEnable(GL_POINT_SMOOTH);
            glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
            glPointSize(radius * (2 * mc.gameSettings.guiScale));
            render(GL_POINTS, () -> glVertex2d(x, y));
        });
    }

    public static void color(int color) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, 255);
    }

    public static void drawFullCircle(float cx, float cy, float r) {
        r *= 2.0f;
        cx *= 2.0f;
        cy *= 2.0f;
        final float theta = 0.19634953f;
        final float p = (float) Math.cos(theta);
        final float s = (float) Math.sin(theta);
        float x = r;
        float y = 0.0f;
        enableRender2D();
        glEnable(2848);
        glHint(3154, 4354);
        glEnable(3024);
        glScalef(0.5f, 0.5f, 0.5f);
        glBegin(9);
        for (int ii = 0; ii < 32; ++ii) {
            glVertex2f(x + cx, y + cy);
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }
        glEnd();
        glScalef(2.0f, 2.0f, 2.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        disableRender2D();
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        enableRender2D();
        setColor(color);
        GL11.glBegin(7);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) (x + width), (double) y);
        GL11.glVertex2d((double) (x + width), (double) (y + height));
        GL11.glVertex2d((double) x, (double) (y + height));
        GL11.glEnd();
        disableRender2D();
    }

    public static void enableRender2D() {
        glEnable(3042);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0F);
    }

    public static void disableRender2D() {
        GL11.glDisable(3042);
        glEnable(2884);
        glEnable(3553);
        GL11.glDisable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static int reAlpha(int color, float alpha) {
        try {
            Color c = new Color(color);
            float r = ((float) 1 / 255) * c.getRed();
            float g = ((float) 1 / 255) * c.getGreen();
            float b = ((float) 1 / 255) * c.getBlue();
            return new Color(r, g, b, alpha).getRGB();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return color;
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
        //if (color == 16777215) color = Colors.WHITE.c;
        //if (borderColor == 16777215) borderColor = Colors.WHITE.c;

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
        GL11.glBegin(6);
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        int vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        int i;
        double angleRadians;
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glBegin(6);
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double) centerX, (double) centerY);
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glEnd();
        GL11.glLineWidth(borderWidth);
        GL11.glBegin(3);
        centerX = x + edgeRadius;
        centerY = y + edgeRadius;
        vertices = (int) Math.min(Math.max(edgeRadius, 10.0F), 90.0F);

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 180) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + edgeRadius), (double) y);
        GL11.glVertex2d((double) (x + width - edgeRadius), (double) y);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 90) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + width), (double) (y + edgeRadius));
        GL11.glVertex2d((double) (x + width), (double) (y + height - edgeRadius));
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) i / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) (x + width - edgeRadius), (double) (y + height));
        GL11.glVertex2d((double) (x + edgeRadius), (double) (y + height));
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;

        for (i = vertices; i >= 0; --i) {
            angleRadians = 6.283185307179586D * (double) (i + 270) / (double) (vertices * 4);
            GL11.glVertex2d((double) centerX + Math.sin(angleRadians) * (double) edgeRadius, (double) centerY + Math.cos(angleRadians) * (double) edgeRadius);
        }

        GL11.glVertex2d((double) x, (double) (y + height - edgeRadius));
        GL11.glVertex2d((double) x, (double) (y + edgeRadius));
        GL11.glEnd();
        disableRender2D();
    }

}
