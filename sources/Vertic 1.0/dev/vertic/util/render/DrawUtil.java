package dev.vertic.util.render;

import dev.vertic.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class DrawUtil implements Utils {

    public static void push() {
        GL11.glPushMatrix();
    }

    public static void pop() {
        GL11.glPopMatrix();
    }

    public static void enable(final int glTarget) {
        GL11.glEnable(glTarget);
    }

    public static void disable(final int glTarget) {
        GL11.glDisable(glTarget);
    }

    public static void start() {
        enable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        disable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void stop() {
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        enable(GL11.GL_CULL_FACE);
        enable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_BLEND);
        color(Color.white);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void begin(final int glMode) {
        GL11.glBegin(glMode);
    }

    public static void end() {
        GL11.glEnd();
    }

    public static void vertex(final double x, final double y) {
        GL11.glVertex2d(x, y);
    }

    public static void translate(final double x, final double y) {
        GL11.glTranslated(x, y, 0);
    }

    public static void scale(final double x, final double y) {
        GL11.glScaled(x, y, 1);
    }

    public static void rotate(final double x, final double y, final double z, final double angle) {
        GL11.glRotated(angle, x, y, z);
    }

    public static void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static void color(final double red, final double green, final double blue) {
        color(red, green, blue, 1);
    }

    public static void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static void color(Color color, final int alpha) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.5);
    }

    public static void lineWidth(final double width) {
        GL11.glLineWidth((float) width);
    }

    public static void rect(final double x, final double y, final double width, final double height, final boolean filled, final Color color) {
        start();
        if (color != null)
            color(color);
        begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINES);
        {
            vertex(x, y);
            vertex(x + width, y);
            vertex(x + width, y + height);
            vertex(x, y + height);
            if (!filled) {
                vertex(x, y);
                vertex(x, y + height);
                vertex(x + width, y);
                vertex(x + width, y + height);
            }
        }
        end();
        stop();
    }
    public static void rectCentered(double x, double y, final double width, final double height, final boolean filled, final Color color) {
        x -= width / 2;
        y -= height / 2;
        rect(x, y, width, height, filled, color);
    }

    public static void polygon(final double x, final double y, double sideLength, final double amountOfSides, final boolean filled, final Color color) {
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        if (!filled) GL11.glLineWidth(2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);
        {
            for (double i = 0; i <= amountOfSides / 4; i++) {
                final double angle = i * 4 * (Math.PI * 2) / 360;
                vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
            }
        }
        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();
    }
    public static void polygonCentered(double x, double y, final double sideLength, final int amountOfSides, final boolean filled, final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, amountOfSides, filled, color);
    }

    public static void circle(final double x, final double y, final double radius, final boolean filled, final Color color) {
        polygon(x, y, radius, 360, filled, color);
    }
    public static void circleCentered(double x, double y, final double radius, final boolean filled, final Color color) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, 360, filled, color);
    }

    public static void triangle(final double x, final double y, final double sideLength, final boolean filled, final Color color) {
        polygon(x, y, sideLength, 3, filled, color);
    }
    public static void triangleCentered(double x, double y, final double sideLength, final boolean filled, final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, 3, filled, color);
    }

    public static void line(final double firstX, final double firstY, final double secondX, final double secondY, final double lineWidth, final Color color) {
        start();
        if (color != null)
            color(color);
        lineWidth(lineWidth);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(GL11.GL_LINES);
        {
            vertex(firstX, firstY);
            vertex(secondX, secondY);
        }
        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();
    }

    public static void image(final ResourceLocation imageLocation, final float x, final float y, final float width, final float height) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        enable(GL11.GL_BLEND);
        GlStateManager.disableAlpha();
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        mc.getTextureManager().bindTexture(imageLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GlStateManager.enableAlpha();
        disable(GL11.GL_BLEND);
    }

    public static void imageCentered(final ResourceLocation imageLocation, float x, float y, final float width, final float height) {
        x -= width / 2f;
        y -= height / 2f;
        image(imageLocation, x, y, width, height);
    }

    public static void gradient(final double x, final double y, final double width, final double height, final boolean filled, final Color color1, final Color color2) {
        start();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableAlpha();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0);
        if (color1 != null)
            color(color1);
        begin(filled ? GL11.GL_QUADS : GL11.GL_LINES);
        {
            vertex(x, y);
            vertex(x + width, y);
            if (color2 != null)
                color(color2);
            vertex(x + width, y + height);
            vertex(x, y + height);
            if (!filled) {
                vertex(x, y);
                vertex(x, y + height);
                vertex(x + width, y);
                vertex(x + width, y + height);
            }
        }
        end();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glShadeModel(GL11.GL_FLAT);
        stop();
    }






    public static void roundedRect(double x, double y, double width, double height, final double radius, final Color color, boolean[] round) {
        double x1 = x + width;
        double y1 = y + height;
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        color(color);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        round(x, y, x1, y1, radius, round);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glEnable(3042);
        GL11.glPopAttrib();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        stop();
    }
    public static void roundedRect(final double x, final double y, final double width, final double height, final double cornerRadius, final Color color) {
        roundedRect(x, y, width, height, cornerRadius, color, new boolean[] {true, true, true, true});
    }

    public static void centeredRoundedRect(double x, double y, final double width, final double height, final double cornerRadius, final Color color) {
        x -= width / 2;
        y -= height / 2;
        roundedRect(x, y, width, height, cornerRadius, color);
    }

    public static void roundedOutline(double x, double y, double width, double height, final double radius, final double borderSize, final Color color, boolean[] drawCorner) {
        double x1 = x + width;
        double y1 = y + height;
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);
        x *= 2.0;
        y *= 2.0;
        x1 *= 2.0;
        y1 *= 2.0;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        color(color);
        GL11.glEnable(2848);
        GL11.glLineWidth((float) borderSize);
        GL11.glBegin(2);
        round(x, y, x1, y1, radius, drawCorner);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth(1.0f);
//        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        stop();
    }
    public static void roundedOutline(final double x, final double y, final double width, final double height, final double cornerRadius, final double borderSize, final Color color) {
        roundedOutline(x, y, width, height, cornerRadius, borderSize, color, new boolean[] {true, true, true, true});
    }

    public static void centeredRoundedOutline(double x, double y, double width, double height, double cornerRadius, double border, final Color color) {
        x -= width / 2;
        y -= height / 2;
        roundedOutline(x, y, width, height, cornerRadius, border, color);
    }

    public static void roundedOutlinedRect(final double x, final double y, final double width, final double height, final double cornerRadius, final double borderSize, final Color backgroundColor, final Color outlineColor) {
        roundedRect(x, y, width, height, cornerRadius, backgroundColor);
        roundedOutline(x, y, width, height, cornerRadius, borderSize, outlineColor);
    }


    /*
    * ??
    */
    public static void roundHelper(double x, double y, double radius, int pn, int pn2, int originalRotation, int finalRotation) {
        for (int i = originalRotation; i <= finalRotation; i += 3)
            GL11.glVertex2d(x + (radius * -pn) + (Math.sin((i * 3.141592653589793) / 180.0) * radius * pn), y + (radius * pn2) + (Math.cos((i * 3.141592653589793) / 180.0) * radius * pn));
    }
    public static void round(double x, double y, double x1, double y1, double radius, final boolean[] round) {
        if(round[0])
            roundHelper(x, y, radius, -1, 1,0, 90);
        else
            GL11.glVertex2d(x, y);

        if(round[1])
            roundHelper(x, y1, radius, -1, -1, 90, 180);
        else
            GL11.glVertex2d(x, y1);

        if(round[2])
            roundHelper(x1, y1, radius, 1, -1, 0, 90);
        else
            GL11.glVertex2d(x1, y1);

        if(round[3])
            roundHelper(x1, y, radius, 1, 1, 90, 180);
        else
            GL11.glVertex2d(x1, y);
    }

}
