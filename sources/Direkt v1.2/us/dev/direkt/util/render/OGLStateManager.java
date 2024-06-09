package us.dev.direkt.util.render;

/**
 * Created by Meckimp on 1/6/2016.
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public  class OGLStateManager {

    public static  RenderItem RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().getTextureManager(), Minecraft.getMinecraft().getModelManager(), Minecraft.getMinecraft().getItemColors());
    public static float playerViewY;
    public static float playerViewX;

    private static ScaledResolution scaledResolution;

    private OGLStateManager() {}

    /**
     * Enables 2D GL constants for 2D rendering.
     */
    public static void enableGL2D() {
        GlStateManager.disableTexture2D();
    }

    /**
     * Disables 2D GL constants for 2D rendering.
     */
    public static void disableGL2D() {
        GlStateManager.enableTexture2D();
    }

    /**
     * Enables GL constants for 3D rendering.
     */
    public static void enableGL3D(float lineWidth) {
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glEnable(GL_CULL_FACE);
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
        glLineWidth(lineWidth);
    }

    /**
     * Disables GL constants for 3D rendering.
     */
    public static void disableGL3D() {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_BLEND);
        glEnable(GL_ALPHA_TEST);
        glDepthMask(true);
        glCullFace(GL_BACK);
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        glHint(GL_POLYGON_SMOOTH_HINT, GL_DONT_CARE);
    }

    public static int genTexture() {
        return glGenTextures();
    }

    /**
     * @param linear Smoothes out the texture when scaling. <br>
     * @param repeat Will repeat the texture if the UV coordinates are outside of the 0.0F ~ 1.0F range. <br>
     * */
    public static int applyTexture(int texId, File file, boolean linear, boolean repeat) throws IOException {
        applyTexture(texId, ImageIO.read(file), linear, repeat);

        return texId;
    }

    /**
     * @param linear Smoothes out the texture when scaling. <br>
     * @param repeat Will repeat the texture if the UV coordinates are outside of the 0.0F ~ 1.0F range. <br>
     * */
    public static int applyTexture(int texId, BufferedImage image, boolean linear, boolean repeat) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        applyTexture(texId, image.getWidth(), image.getHeight(), buffer, linear, repeat);

        return texId;
    }

    /**
     * @param linear Smoothes out the texture when scaling. <br>
     * @param repeat Will repeat the texture if the UV coordinates are outside of the 0.0F ~ 1.0F range. <br>
     * */
    public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, boolean linear, boolean repeat) {
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, linear ? GL_LINEAR : GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, linear ? GL_LINEAR : GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, repeat ? GL_REPEAT : GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, repeat ? GL_REPEAT : GL_CLAMP);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        return texId;
    }

    public static void renderTexture(int texID, float x, float y, float width, float height) {
        glBindTexture(GL_TEXTURE_2D, texID);
        renderTexture(x, y, width, height);
    }

    public static Vec3d to2D(double x, double y, double z) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);

        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
        if (result) {
            return new Vec3d(screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2));
        }
        return null;
    }

    public static void renderTexture(int textureWidth, int textureHeight, float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / textureWidth;
        float renderSRCY = srcY / textureHeight;
        float renderSRCWidth = (srcWidth) / textureWidth;
        float renderSRCHeight = (srcHeight) / textureHeight;
        boolean tex2D = glGetBoolean(GL_TEXTURE_2D);
        boolean blend = glGetBoolean(GL_BLEND);
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glBegin(GL_TRIANGLES);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        glVertex2f(x + width, y);
        glTexCoord2f(renderSRCX, renderSRCY);
        glVertex2f(x, y);
        glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        glVertex2f(x, y + height);
        glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        glVertex2f(x, y + height);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        glVertex2f(x + width, y + height);
        glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        glVertex2f(x + width, y);
        glEnd();

        if (!tex2D) {
            glDisable(GL_TEXTURE_2D);
        }

        if (!blend) {
            glDisable(GL_BLEND);
        }

        glPopMatrix();

    }

    public static void renderTexture(float x, float y, float width, float height) {
        boolean tex2D = glGetBoolean(GL_TEXTURE_2D);
        boolean blend = glGetBoolean(GL_BLEND);
        glPushMatrix();
        glEnable(GL_BLEND);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glScalef(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
        width *= 2;
        height *= 2;
        glBegin(GL_TRIANGLES);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();

        if (!tex2D) {
            glDisable(GL_TEXTURE_2D);
        }

        if (!blend) {
            glDisable(GL_BLEND);
        }

        glPopMatrix();
    }

    public static void drawLine(float x, float y, float x1, float y1, float width) {
        glDisable(GL_TEXTURE_2D);
        glLineWidth(width);
        glBegin(GL_LINES);
        glVertex2f(x, y);
        glVertex2f(x1, y1);
        glEnd();
        glEnable(GL_TEXTURE_2D);
    }

    /**
     * Draws a rectangle on the rectangle instance.
     */
    public static void drawRect(Rectangle rectangle, int color) {
        drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    /**
     * Draws a rectangle at the coordinates specified with the hexadecimal color.
     */
    public static void drawRect(float x, float y, float x1, float y1, int color) {
        enableGL2D();
        GlStateManager.enableBlend();
        glColor(color);
        drawRect(x, y, x1, y1);
        GlStateManager.disableBlend();
        disableGL2D();
    }

    /**
     * Draws a bordered rectangle at the coordinates specified with the hexadecimal color.
     */
    public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x + width, y, x1 - width, y + width);
        drawRect(x, y, x + width, y1);
        drawRect(x1 - width, y, x1, y1);
        drawRect(x + width, y1 - width, x1 - width, y1);
        disableGL2D();
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
        enableGL2D();
        x *= 2; x1 *= 2; y *= 2; y1 *= 2;
        glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y, y1 - 1, borderC);
        drawVLine(x1 - 1, y , y1, borderC);
        drawHLine(x, x1 - 1, y, borderC);
        drawHLine(x, x1 - 2, y1 -1, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside, int border) {
        enableGL2D();
        drawRect(x, y, x1, y1, inside);
        glColor(border);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glBegin(GL_LINE_STRIP);
        glVertex2f(x, y);
        glVertex2f(x, y1);
        glVertex2f(x1, y1);
        glVertex2f(x1, y);
        glVertex2f(x, y);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        enableGL2D();
        drawGradientRect(x, y, x1, y1, top, bottom);
        glColor(border);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLineWidth(lineWidth);
        glBegin(GL_LINE_STRIP);
        glVertex2f(x, y);
        glVertex2f(x, y1);
        glVertex2f(x1, y1);
        glVertex2f(x1, y);
        glVertex2f(x, y);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        disableGL2D();
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        enableGL2D();
        x *= 2; y *= 2; x1 *= 2; y1 *= 2;
        glScalef(0.5F, 0.5F, 0.5F);
        drawVLine(x, y + 1, y1 -2, borderC);
        drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
        drawHLine(x + 2, x1 - 3, y, borderC);
        drawHLine(x + 2, x1 - 3, y1 -1, borderC);
        drawHLine(x + 1, x + 1, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
        drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
        drawHLine(x + 1, x + 1, y1 - 2, borderC);
        drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
        glScalef(2.0F, 2.0F, 2.0F);
        disableGL2D();
    }

    /**
     * Draws a bordered rectangle at the coordinates specified with the hexadecimal color.
     */
    public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor) {
        float x = rectangle.x;
        float y = rectangle.y;
        float x1 = rectangle.x + rectangle.width;
        float y1 = rectangle.y + rectangle.height;
        enableGL2D();
        glColor(internalColor);
        drawRect(x + width, y + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x + 1, y, x1 - 1, y + width);
        drawRect(x, y, x + width, y1);
        drawRect(x1 - width, y, x1, y1);
        drawRect(x + 1, y1 - width, x1 - 1, y1);
        disableGL2D();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     */
    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        enableGL2D();
        glShadeModel(GL_SMOOTH);
        glBegin(GL_QUADS);
        glColor(topColor);
        glVertex2f(x, y1);
        glVertex2f(x1, y1);
        glColor(bottomColor);
        glVertex2f(x1, y);
        glVertex2f(x, y);
        glEnd();
        glShadeModel(GL_FLAT);
        disableGL2D();
    }

    public static void drawGradientHRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        enableGL2D();
        glShadeModel(GL_SMOOTH);
        glBegin(GL_QUADS);
        glColor(topColor);
        glVertex2f(x, y);
        glVertex2f(x, y1);
        glColor(bottomColor);
        glVertex2f(x1, y1);
        glVertex2f(x1, y);
        glEnd();
        glShadeModel(GL_FLAT);
        disableGL2D();
    }

    public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2) {
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glShadeModel(GL_SMOOTH);
        glPushMatrix();
        glBegin(GL_QUADS);
        glColor(col1);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glColor(col2);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glShadeModel(GL_FLAT);
    }

    public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3) {
        enableGL2D();
        glPushMatrix();
        glColor(col1);
        glLineWidth(1F);
        glBegin(GL_LINES);
        glVertex2d(x, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glVertex2d(x2, y);
        glVertex2d(x, y);
        glVertex2d(x2, y);
        glVertex2d(x, y2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
        drawGradientRect(x, y, x2, y2, col2, col3);
        disableGL2D();
    }

    public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color) {
        float f1 = (color >> 24 & 255) / 255.0F;
        float f2 = (color >> 16 & 255) / 255.0F;
        float f3 = (color >> 8 & 255) / 255.0F;
        float f4 = (color & 255) / 255.0F;
        glPushMatrix();
        glTranslated(x, y, 0);
        glColor4f(f2, f3, f4, f1);
        glLineWidth(width);

        if (angle > 0) {
            glBegin(GL_LINE_STRIP);

            for (int i = 0; i < angle; i++)
            {
                float a = (float)(i * (angle * Math.PI / points));
                float xc = (float)(Math.cos(a) * radius);
                float yc = (float)(Math.sin(a) * radius);
                glVertex2f(xc, yc);
            }

            glEnd();
        }

        if (angle < 0) {
            glBegin(GL_LINE_STRIP);

            for (int i = 0; i > angle; i--) {
                float a = (float)(i * (angle * Math.PI / points));
                float xc = (float)(Math.cos(a) * -radius);
                float yc = (float)(Math.sin(a) * -radius);
                glVertex2f(xc, yc);
            }

            glEnd();
        }

        disableGL2D();
        glDisable(GL_MAP1_VERTEX_3);
        glPopMatrix();
    }

    public static void drawHLine(float x, float y, float x1, int y1) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }

        drawRect(x, x1, y + 1, x1 + 1, y1);
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float var5 = y;
            y = x1;
            x1 = var5;
        }

        drawRect(x, y + 1, x + 1, x1, y1);
    }

    public static void drawHLine(float x, float y, float x1, int y1, int y2) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }

        drawGradientRect(x, x1, y + 1, x1 + 1, y1, y2);
    }

    /**
     * Draws a rectangle at the coordinates specified.
     */
    public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a) {
        enableGL2D();
        GlStateManager.enableBlend();
        glColor4f(r, g, b, a);
        drawRect(x, y, x1, y1);
        GlStateManager.disableBlend();
        disableGL2D();
    }

    public static void drawRect(float x, float y, float x1, float y1) {
        // glRectd(x, y, x1, y1);
        glBegin(GL_QUADS);
        glVertex2f(x, y1);
        glVertex2f(x1, y1);
        glVertex2f(x1, y);
        glVertex2f(x, y);
        glEnd();
    }

    public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
        r *= 2;
        cx *= 2;
        cy *= 2;
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        float theta = (float) (2 * 3.1415926 / (num_segments));
        float p = (float) Math.cos(theta);//calculate the sine and cosine
        float s = (float) Math.sin(theta);
        float t;
        float x = r;
        float y = 0;//start at angle = 0
        enableGL2D();
        glScalef(0.5F, 0.5F, 0.5F);
        glColor4f(f1, f2, f3, f);
        glBegin(GL_LINE_LOOP);

        for(int ii = 0; ii < num_segments; ii++) {
            glVertex2f(x + cx, y + cy);// vertex vertex

            //rotate the stuff
            t = x;
            x = p * x - s * y;
            y = s * t + p * y;
        }

        glEnd();
        glScalef(2F, 2F, 2F);
        disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy, double r, int c) {
        r *= 2;
        cx *= 2;
        cy *= 2;
        float f = (float) (c >> 24 & 0xff) / 255F;
        float f1 = (float) (c >> 16 & 0xff) / 255F;
        float f2 = (float) (c >> 8 & 0xff) / 255F;
        float f3 = (float) (c & 0xff) / 255F;
        enableGL2D();
        glScalef(0.5F, 0.5F, 0.5F);
        glColor4f(f1, f2, f3, f);
        glBegin(GL_TRIANGLE_FAN);

        for (int i = 0; i <= 360; i++) {
            double x = Math.sin((i * Math.PI / 180)) * r;
            double y = Math.cos((i * Math.PI / 180)) * r;
            glVertex2d(cx + x, cy + y);
        }

        glEnd();
        glScalef(2F, 2F, 2F);
        disableGL2D();
    }

    /**
     *
     * */
    public static void glColor(Color color) {
        glColor4f((color.getRed() / 255F), (color.getGreen() / 255F), (color.getBlue() / 255F), (color.getAlpha() / 255F));
    }

    /**
     *
     * */
    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 255) / 255.0F;
        float red = (hex >> 16 & 255) / 255.0F;
        float green = (hex >> 8 & 255) / 255.0F;
        float blue = (hex & 255) / 255.0F;
        glColor4f(red, green, blue, alpha);
    }

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = (1 / 255.0F) * redRGB;
        float green = (1 / 255.0F) * greenRGB;
        float blue = (1 / 255.0F) * blueRGB;
        glColor4f(red, green, blue, alpha);
    }

    /**
     * @return A java.awt.Color instance with a random r, g, b value.
     */
    /*public static Color getRandomColor() {
        return getRandomColor(1000, 0.6F);
    }*/

    /**
     * This isn't mine, but it's the most beautiful random color generator I've seen. Reminds me of easter.
     * */
    /*public static Color getRandomColor(int saturationRandom, float luminance) {
         float randomFloat = MathUtils.getRandom();
         float saturation = (MathUtils.getRandom(saturationRandom) + 1000) / saturationRandom + 1000F;

        return Color.getHSBColor(randomFloat, saturation, luminance);
    }

    public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
        var9.begin(GL11.GL_QUADS, var9.getVertexFormat());
        var9.pos((double)(par1), (double)(par2 + par6), 0).tex((double)((float)(par3) * var7), (double)((float)(par4 + par6) * var8));
        var9.pos((double)(par1 + par5), (double)(par2 + par6), 0).tex((double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        var9.pos((double)(par1 + par5), (double)(par2), 0).tex((double)((float)(par3 + par5) * var7), (double)((float)(par4) * var8));
        var9.pos((double)(par1), (double)(par2), 0).tex((double)((float)(par3) * var7), (double)((float)(par4) * var8));
        var9.finishDrawing();
    }*/

    /**
     * Stole from RenderGlobal.
     * Draws a box outline on the Box instance.
     */
    /*public static void drawOutlinedBox(Box box) {
        if (box == null) {
            return;
        }

        glBegin(GL_LINE_STRIP);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.minZ);
        glEnd();
        glBegin(GL_LINE_STRIP);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glEnd();
        glBegin(GL_LINES);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glEnd();
    }*/

    /**
     * Renders criss-cross lines.
     * */
    /*public static void renderCrosses(Box box) {
        glBegin(GL_LINES);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);

        glVertex3d(box.minX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.minZ);

        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.maxZ);

        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glEnd();
    }*/

    /**
     * Overly complicated box method that will draw a box at the Box instance.
     */
    /*public static void drawBox(Box box) {
        if (box == null) {
            return;
        }
        // back
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glEnd();
        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glEnd();
        // left
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glEnd();
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glEnd();
        // right
        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glEnd();
        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glEnd();
        // front
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glEnd();
        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glEnd();
        // top
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glEnd();

        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.maxY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.minZ);
        glVertex3d(box.minX, box.maxY, box.maxZ);
        glVertex3d(box.maxX, box.maxY, box.maxZ);
        glEnd();

        // bottom
        glBegin(GL_QUADS);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glEnd();

        glBegin(GL_QUADS);
        glVertex3d(box.maxX, box.minY, box.minZ);
        glVertex3d(box.minX, box.minY, box.minZ);
        glVertex3d(box.minX, box.minY, box.maxZ);
        glVertex3d(box.maxX, box.minY, box.maxZ);
        glEnd();
    }*/

    /**
     * Updates the scaledResolution to the current window width and height.
     */
    public static void updateScaledResolution() {
        scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
    }

    public static ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    /**
     * @return Used for nifty things ;)
     * @author Jonalu
     */
    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        updateScaledResolution();
        int factor = scaledResolution.getScaleFactor();
        glScissor((int) (x * factor),
                (int) ((scaledResolution.getScaledHeight() - y2) * factor),
                (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }

}

