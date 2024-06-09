/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.xd;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import winter.utils.render.xd.Box;

public final class OGLRender {
    public static final RenderItem RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().renderEngine, Minecraft.getMinecraft().modelManager);
    public static float playerViewY;
    public static float playerViewX;
    private static ScaledResolution scaledResolution;

    private OGLRender() {
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

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void enableGL3D(float lineWidth) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        Minecraft.getMinecraft().entityRenderer.func_175072_h();
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(lineWidth);
    }

    public static void disableGL3D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static int genTexture() {
        return GL11.glGenTextures();
    }

    public static int applyTexture(int texId, File file, boolean linear, boolean repeat) throws IOException {
        OGLRender.applyTexture(texId, ImageIO.read(file), linear, repeat);
        return texId;
    }

    public static int applyTexture(int texId, BufferedImage image, boolean linear, boolean repeat) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        int y2 = 0;
        while (y2 < image.getHeight()) {
            int x2 = 0;
            while (x2 < image.getWidth()) {
                int pixel = pixels[y2 * image.getWidth() + x2];
                buffer.put((byte)(pixel >> 16 & 255));
                buffer.put((byte)(pixel >> 8 & 255));
                buffer.put((byte)(pixel & 255));
                buffer.put((byte)(pixel >> 24 & 255));
                ++x2;
            }
            ++y2;
        }
        buffer.flip();
        OGLRender.applyTexture(texId, image.getWidth(), image.getHeight(), buffer, linear, repeat);
        return texId;
    }

    public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, boolean linear, boolean repeat) {
        GL11.glBindTexture(3553, texId);
        GL11.glTexParameteri(3553, 10241, linear ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10240, linear ? 9729 : 9728);
        GL11.glTexParameteri(3553, 10242, repeat ? 10497 : 10496);
        GL11.glTexParameteri(3553, 10243, repeat ? 10497 : 10496);
        GL11.glPixelStorei(3317, 1);
        GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, pixels);
        return texId;
    }

    public static void renderTexture(int texID, float x2, float y2, float width, float height) {
        GL11.glBindTexture(3553, texID);
        OGLRender.renderTexture(x2, y2, width, height);
    }

    public static void renderTexture(int textureWidth, int textureHeight, float x2, float y2, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / (float)textureWidth;
        float renderSRCY = srcY / (float)textureHeight;
        float renderSRCWidth = srcWidth / (float)textureWidth;
        float renderSRCHeight = srcHeight / (float)textureHeight;
        boolean tex2D = GL11.glGetBoolean(3553);
        boolean blend = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3553);
        GL11.glBegin(4);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x2 + width, y2);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2f(x2, y2);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x2, y2 + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x2, y2 + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2f(x2 + width, y2 + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2f(x2 + width, y2);
        GL11.glEnd();
        if (!tex2D) {
            GL11.glDisable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }

    public static void renderTexture(float x2, float y2, float width, float height) {
        boolean tex2D = GL11.glGetBoolean(3553);
        boolean blend = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glBegin(4);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f((x2 *= 2.0f) + (width *= 2.0f), y2 *= 2.0f);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex2f(x2, y2);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x2, y2 + (height *= 2.0f));
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex2f(x2, y2 + height);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex2f(x2 + width, y2 + height);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex2f(x2 + width, y2);
        GL11.glEnd();
        if (!tex2D) {
            GL11.glDisable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }

    public static void drawLine(float x2, float y2, float x1, float y1, float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }

    public static void drawRect(Rectangle rectangle, int color) {
        OGLRender.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, int color) {
        OGLRender.enableGL2D();
        OGLRender.glColor(color);
        OGLRender.drawRect(x2, y2, x1, y1);
        OGLRender.disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x1, float y1, float width, int internalColor, int borderColor) {
        OGLRender.enableGL2D();
        OGLRender.glColor(internalColor);
        OGLRender.drawRect(x2 + width, y2 + width, x1 - width, y1 - width);
        OGLRender.glColor(borderColor);
        OGLRender.drawRect(x2 + width, y2, x1 - width, y2 + width);
        OGLRender.drawRect(x2, y2, x2 + width, y1);
        OGLRender.drawRect(x1 - width, y2, x1, y1);
        OGLRender.drawRect(x2 + width, y1 - width, x1 - width, y1);
        OGLRender.disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x1, float y1, int insideC, int borderC) {
        OGLRender.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        OGLRender.drawVLine(x2 *= 2.0f, y2 *= 2.0f, (y1 *= 2.0f) - 1.0f, borderC);
        OGLRender.drawVLine((x1 *= 2.0f) - 1.0f, y2, y1, borderC);
        OGLRender.drawHLine(x2, x1 - 1.0f, y2, borderC);
        OGLRender.drawHLine(x2, x1 - 2.0f, y1 - 1.0f, borderC);
        OGLRender.drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        OGLRender.disableGL2D();
    }

    public static void drawBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int inside, int border) {
        OGLRender.enableGL2D();
        OGLRender.drawRect(x2, y2, x1, y1, inside);
        OGLRender.glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        OGLRender.disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        OGLRender.enableGL2D();
        OGLRender.drawGradientRect(x2, y2, x1, y1, top, bottom);
        OGLRender.glColor(border);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        OGLRender.disableGL2D();
    }

    public static void drawRoundedRect(float x2, float y2, float x1, float y1, int borderC, int insideC) {
        OGLRender.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        OGLRender.drawVLine(x2 *= 2.0f, (y2 *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        OGLRender.drawVLine((x1 *= 2.0f) - 1.0f, y2 + 1.0f, y1 - 2.0f, borderC);
        OGLRender.drawHLine(x2 + 2.0f, x1 - 3.0f, y2, borderC);
        OGLRender.drawHLine(x2 + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        OGLRender.drawHLine(x2 + 1.0f, x2 + 1.0f, y2 + 1.0f, borderC);
        OGLRender.drawHLine(x1 - 2.0f, x1 - 2.0f, y2 + 1.0f, borderC);
        OGLRender.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        OGLRender.drawHLine(x2 + 1.0f, x2 + 1.0f, y1 - 2.0f, borderC);
        OGLRender.drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        OGLRender.disableGL2D();
    }

    public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor) {
        float x2 = rectangle.x;
        float y2 = rectangle.y;
        float x1 = rectangle.x + rectangle.width;
        float y1 = rectangle.y + rectangle.height;
        OGLRender.enableGL2D();
        OGLRender.glColor(internalColor);
        OGLRender.drawRect(x2 + width, y2 + width, x1 - width, y1 - width);
        OGLRender.glColor(borderColor);
        OGLRender.drawRect(x2 + 1.0f, y2, x1 - 1.0f, y2 + width);
        OGLRender.drawRect(x2, y2, x2 + width, y1);
        OGLRender.drawRect(x1 - width, y2, x1, y1);
        OGLRender.drawRect(x2 + 1.0f, y1 - width, x1 - 1.0f, y1);
        OGLRender.disableGL2D();
    }

    public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        OGLRender.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        OGLRender.glColor(topColor);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        OGLRender.glColor(bottomColor);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        OGLRender.disableGL2D();
    }

    public static void drawGradientHRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        OGLRender.enableGL2D();
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        OGLRender.glColor(topColor);
        GL11.glVertex2f(x2, y2);
        GL11.glVertex2f(x2, y1);
        OGLRender.glColor(bottomColor);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        OGLRender.disableGL2D();
    }

    public static void drawGradientRect(double x2, double y2, double x22, double y22, int col1, int col2) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        OGLRender.glColor(col1);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        OGLRender.glColor(col2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawGradientBorderedRect(double x2, double y2, double x22, double y22, float l1, int col1, int col2, int col3) {
        OGLRender.enableGL2D();
        GL11.glPushMatrix();
        OGLRender.glColor(col1);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        OGLRender.drawGradientRect(x2, y2, x22, y22, col2, col3);
        OGLRender.disableGL2D();
    }

    public static void drawStrip(int x2, int y2, float width, double angle, float points, float radius, int color) {
        float a2;
        int i2;
        float yc2;
        float xc2;
        float f1 = (float)(color >> 24 & 255) / 255.0f;
        float f2 = (float)(color >> 16 & 255) / 255.0f;
        float f3 = (float)(color >> 8 & 255) / 255.0f;
        float f4 = (float)(color & 255) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated(x2, y2, 0.0);
        GL11.glColor4f(f2, f3, f4, f1);
        GL11.glLineWidth(width);
        if (angle > 0.0) {
            GL11.glBegin(3);
            i2 = 0;
            while ((double)i2 < angle) {
                a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
                xc2 = (float)(Math.cos(a2) * (double)radius);
                yc2 = (float)(Math.sin(a2) * (double)radius);
                GL11.glVertex2f(xc2, yc2);
                ++i2;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin(3);
            i2 = 0;
            while ((double)i2 > angle) {
                a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
                xc2 = (float)(Math.cos(a2) * (double)(- radius));
                yc2 = (float)(Math.sin(a2) * (double)(- radius));
                GL11.glVertex2f(xc2, yc2);
                --i2;
            }
            GL11.glEnd();
        }
        OGLRender.disableGL2D();
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float x2, float y2, float x1, int y1) {
        if (y2 < x2) {
            float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        OGLRender.drawRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawVLine(float x2, float y2, float x1, int y1) {
        if (x1 < y2) {
            float var5 = y2;
            y2 = x1;
            x1 = var5;
        }
        OGLRender.drawRect(x2, y2 + 1.0f, x2 + 1.0f, x1, y1);
    }

    public static void drawHLine(float x2, float y2, float x1, int y1, int y22) {
        if (y2 < x2) {
            float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        OGLRender.drawGradientRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1, y22);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, float r2, float g2, float b2, float a2) {
        OGLRender.enableGL2D();
        GL11.glColor4f(r2, g2, b2, a2);
        OGLRender.drawRect(x2, y2, x1, y1);
        OGLRender.disableGL2D();
    }

    public static void drawRect(float x2, float y2, float x1, float y1) {
        GL11.glBegin(7);
        GL11.glVertex2f(x2, y1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x1, y2);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
    }

    public static void drawCircle(float cx, float cy2, float r2, int num_segments, int c2) {
        cx *= 2.0f;
        cy2 *= 2.0f;
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f1 = (float)(c2 >> 16 & 255) / 255.0f;
        float f22 = (float)(c2 >> 8 & 255) / 255.0f;
        float f3 = (float)(c2 & 255) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p2 = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x2 = r2 *= 2.0f;
        float y2 = 0.0f;
        OGLRender.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(2);
        int ii2 = 0;
        while (ii2 < num_segments) {
            GL11.glVertex2f(x2 + cx, y2 + cy2);
            float t = x2;
            x2 = p2 * x2 - s * y2;
            y2 = s * t + p2 * y2;
            ++ii2;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        OGLRender.disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy2, double r2, int c2) {
        r2 *= 2.0;
        cx *= 2;
        cy2 *= 2;
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f1 = (float)(c2 >> 16 & 255) / 255.0f;
        float f22 = (float)(c2 >> 8 & 255) / 255.0f;
        float f3 = (float)(c2 & 255) / 255.0f;
        OGLRender.enableGL2D();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glBegin(6);
        int i2 = 0;
        while (i2 <= 360) {
            double x2 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
            double y2 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d((double)cx + x2, (double)cy2 + y2);
            ++i2;
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        OGLRender.disableGL2D();
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569f * (float)redRGB;
        float green = 0.003921569f * (float)greenRGB;
        float blue = 0.003921569f * (float)blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static Color getRandomColor() {
        return OGLRender.getRandomColor(1000, 0.6f);
    }

    public static Color getRandomColor(int saturationRandom, float luminance) {
        float randomFloat = (float)Math.random();
        float saturation = ((float)Math.random() * (float)saturationRandom + 1000.0f) / (float)saturationRandom + 1000.0f;
        return Color.getHSBColor(randomFloat, saturation, luminance);
    }

    public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
        var9.startDrawingQuads();
        var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0, (float)(par3 + 0) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0, (float)(par3 + par5) * var7, (float)(par4 + par6) * var8);
        var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0, (float)(par3 + par5) * var7, (float)(par4 + 0) * var8);
        var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0, (float)(par3 + 0) * var7, (float)(par4 + 0) * var8);
        var9.draw();
    }

    public static void drawOutlinedBox(Box box) {
        if (box == null) {
            return;
        }
        GL11.glBegin(3);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glEnd();
    }

    public static void renderCrosses(Box box) {
        GL11.glBegin(1);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glEnd();
    }

    public static void drawBox(Box box) {
        if (box == null) {
            return;
        }
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.minZ);
        GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(box.maxX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.minZ);
        GL11.glVertex3d(box.minX, box.minY, box.maxZ);
        GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
        GL11.glEnd();
    }

    public static void updateScaledResolution() {
        scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
    }

    public static ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public static void prepareScissorBox(float x2, float y2, float x22, float y22) {
        OGLRender.updateScaledResolution();
        int factor = scaledResolution.getScaleFactor();
        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scaledResolution.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
    }
}

