package net.silentclient.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

public class DisplayUtil
{
    public static int getCenterX()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        return minecraft.displayWidth / (2 * scaledresolution.getScaleFactor());
    }

    public static int getCenterY()
    {
        Minecraft minecraft = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(minecraft);
        return minecraft.displayHeight / (2 * scaledresolution.getScaleFactor());
    }

    public static void drawCircle(float cx, float cy, float r, Color color, float lineWidth)
    {
        GlStateManager.pushMatrix();
        ColorUtils.setColor(color.getRGB());
        int i = getNumCircleSegments(r);
        float f = (float)((Math.PI * 2D) / (double)i);
        float f1 = (float)Math.cos((double)f);
        float f2 = (float)Math.sin((double)f);
        float f4 = r;
        float f5 = 0.0F;
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int j = 0; j < i; ++j)
        {
            GL11.glVertex2f(f4 + cx, f5 + cy);
            float f3 = f4;
            f4 = f1 * f4 - f2 * f5;
            f5 = f2 * f3 + f1 * f5;
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(2.0F);
        GlStateManager.popMatrix();
    }

    public static int getNumCircleSegments(float r)
    {
        return (int)(50.0D * Math.sqrt((double)r));
    }

    public static void displayHorizontalLine(int y, int x1, int x2, Color color)
    {
        displayFilledRectangle(x1, y, x2, y + 1, color);
    }

    public static void displayVerticalLine(int x, int y1, int y2, Color color)
    {
        displayFilledRectangle(x, y1, x + 1, y2, color);
    }

    public static void displayRectangle(int x1, int y1, int x2, int y2, Color color)
    {
        displayHorizontalLine(y1, x1, x2, color);
        displayHorizontalLine(y2, x1, x2 + 1, color);
        displayVerticalLine(x1, y1, y2, color);
        displayVerticalLine(x2, y1, y2, color);
    }

    public static void displayFilledRectangle(int x1, int y1, int x2, int y2, Color color)
    {
        GL11.glPushMatrix();

        if (x1 < x2)
        {
            int i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2)
        {
            int j = y1;
            y1 = y2;
            y2 = j;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glVertex2f((float)x2, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawLines(float[] points, float thickness, Color color, boolean smooth)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (smooth)
        {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        }
        else
        {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glLineWidth(thickness);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(GL11.GL_LINES);

        for (int i = 0; i < points.length; i += 2)
        {
            GL11.glVertex2f(points[i], points[i + 1]);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public static int[] getScreenSize()
    {
        int[] aint = new int[2];
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        aint[0] = scaledresolution.getScaledWidth();
        aint[1] = scaledresolution.getScaledHeight();
        return aint;
    }

    public static double[] getScreenSizeDouble()
    {
        double[] adouble = new double[2];
        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        adouble[0] = scaledresolution.getScaledWidth_double();
        adouble[1] = scaledresolution.getScaledHeight_double();
        return adouble;
    }

    public static void displayLine(int x1, int y1, int x2, int y2, Color color)
    {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float x, float y, float radius, float thickness, Color color, boolean smooth)
    {
        drawPartialCircle(x, y, radius, 0, 360, thickness, color, smooth);
    }

    public static void drawPartialCircle(float x, float y, float radius, int startAngle, int endAngle, float thickness, Color color, boolean smooth)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        if (startAngle > endAngle)
        {
            int i = startAngle;
            startAngle = endAngle;
            endAngle = i;
        }

        if (startAngle < 0)
        {
            startAngle = 0;
        }

        if (endAngle > 360)
        {
            endAngle = 360;
        }

        if (smooth)
        {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
        }
        else
        {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glLineWidth(thickness);
        GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        float f1 = 0.017453292F;

        for (int j = startAngle; j <= endAngle; ++j)
        {
            float f = (float)(j - 90) * f1;
            GL11.glVertex2f(x + (float)Math.cos((double)f) * radius, y + (float)Math.sin((double)f) * radius);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glLineWidth(2.0F);
        GL11.glPopMatrix();
    }
}
