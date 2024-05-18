package host.kix.uzi.utilities.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector2f;
import java.awt.*;

/**
 * Created by myche on 2/11/2017.
 */
public class RenderingMethods {

    private static float FADING_HUE = 1F;

    public static float getFadingHue() {
        return FADING_HUE;
    }

    public static void drawBorderedRect(final int x, final int y, final int x1, final int y1, final float width, final int internalColor, final int borderColor) {
        enableGL2D();
        Gui.drawRect(x + (int) width, y + (int) width, x1 - (int) width, y1 - (int) width, internalColor);
        Gui.drawRect(x + (int) width, y, x1 - (int) width, y + (int) width, borderColor);
        Gui.drawRect(x, y, x + (int) width, y1, borderColor);
        Gui.drawRect(x1 - (int) width, y, x1, y1, borderColor);
        Gui.drawRect(x + (int) width, y1 - (int) width, x1 - (int) width, y1, borderColor);
        disableGL2D();
    }


    public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside, int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
        glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        disableGL2D();
    }

    public static void renderCircleWithHoleInCenter(Vector2f center, float radiusOuter, float radiusInner, float[] color, float angle, float step) {
        float p1X = (float) (center.x + Math.sin(angle) * radiusOuter);
        float p1Y = (float) (center.y + Math.cos(angle) * radiusOuter);
        float p2X = (float) (center.x + Math.sin(angle) * radiusInner);
        float p2Y = (float) (center.y + Math.cos(angle) * radiusInner);
        float p3X = (float) (center.x + Math.sin(angle + step) * radiusInner);
        float p3Y = (float) (center.y + Math.cos(angle + step) * radiusInner);
        float p4X = (float) (center.x + Math.sin(angle + step) * radiusOuter);
        float p4Y = (float) (center.y + Math.cos(angle + step) * radiusOuter);
        float alpha = color[3];
        float red = color[0];
        float blue = color[1];
        float green = color[2];
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.draw();
        worldRenderer.addVertex(p1X, p1Y, 0.0D);
        worldRenderer.addVertex(p2X, p2Y, 0.0D);
        worldRenderer.addVertex(p3X, p3Y, 0.0D);
        worldRenderer.addVertex(p4X, p4Y, 0.0D);
    }

    public static void drawHollowRect(double left, double top, double right, double bottom, float borderWidth, int borderColor) {
        float alpha = (borderColor >> 24 & 0xFF) / 255.0f;
        float red = (borderColor >> 16 & 0xFF) / 255.0f;
        float green = (borderColor >> 8 & 0xFF) / 255.0f;
        float blue = (borderColor & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(red, green, blue, alpha);

        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glLineWidth(borderWidth);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, top, 0.0F);
        worldRenderer.addVertex(right, top, 0.0F);
        worldRenderer.addVertex(left, bottom, 0.0F);
        worldRenderer.addVertex(right, bottom, 0.0F);
        tessellator.draw();
        GL11.glLineWidth(2.0F);

        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }


    public static void drawRect(Rectangle rectangle, int color) {
        drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        enableGL2D();
        glColor(color);
        drawRect(x, y, x1, y1);
        disableGL2D();
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawRect(float x, float y, float x1, float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
    }

    public static void enableGL3D(float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void enableGL3D() {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4353);
        GL11.glDisable(2896);
    }

    public static void disableGL3D() {
        GL11.glEnable(2896);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, final int insideC, final int borderC) {
        enableGL2D();
        x *= 2.0f;
        x1 *= 2.0f;
        y *= 2.0f;
        y1 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawVLine(x, y, y1 - 1, borderC);
        drawVLine(x1 - 1, y, y1, borderC);
        drawHLine(x, x1 - 1, y, borderC);
        drawHLine(x, x1 - 2, y1 - 1, borderC);
        Gui.drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        disableGL2D();
    }

    public static void drawHLine(int x, int y, final int x1, final int y1) {
        if (y < x) {
            final int var5 = x;
            x = y;
            y = var5;
        }
        Gui.drawRect(x, x1, y + 1, x1 + 1, y1);
    }

    public static void drawVLine(final int x, int y, int x1, final int y1) {
        if (x1 < y) {
            final int var5 = y;
            y = x1;
            x1 = var5;
        }
        Gui.drawRect(x, y + 1, x + 1, x1, y1);
    }

    public static void drawHLine(float x, float y, final float x1, final int y1, final int y2) {
        if (y < x) {
            final float var5 = x;
            x = y;
            y = var5;
        }
        drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
    }


    public static void drawTracerLine(double[] pos, float[] c, float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(width);
        GL11.glColor4f(c[0], c[1], c[2], c[3]);
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(0, Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0);
            GL11.glVertex3d(pos[0], pos[1], pos[2]);
        }
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float var7 = (float) (startColor >> 24 & 255) / 255.0F;
        float var8 = (float) (startColor >> 16 & 255) / 255.0F;
        float var9 = (float) (startColor >> 8 & 255) / 255.0F;
        float var10 = (float) (startColor & 255) / 255.0F;
        float var11 = (float) (endColor >> 24 & 255) / 255.0F;
        float var12 = (float) (endColor >> 16 & 255) / 255.0F;
        float var13 = (float) (endColor >> 8 & 255) / 255.0F;
        float var14 = (float) (endColor & 255) / 255.0F;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex(right, top, 0);
        var16.addVertex(left, top, 0);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, 0);
        var16.addVertex(right, bottom, 0);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }

}
