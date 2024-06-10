package xyz.gucciclient.utils;

import org.lwjgl.opengl.*;

public class Render
{
    public static void drawFullCircle(float cx, float cy, double r, final int c) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        r *= 2.0;
        cx *= 2.0f;
        cy *= 2.0f;
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; ++i) {
            final double x = Math.sin(i * 3.141592653589793 / 180.0) * r;
            final double y = Math.cos(i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void drawCheckmark(final float x, final float y, final int hexColor) {
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + 1.0f), (double)(y + 1.0f));
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
        GL11.glVertex2d((double)(x + 6.0f), (double)(y - 2.0f));
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void drawArrow(float x, float y, final boolean isOpen, final int hexColor) {
        GL11.glPushMatrix();
        GL11.glScaled(1.3, 1.3, 1.3);
        if (isOpen) {
            y -= 1.5f;
            x += 2.0f;
        }
        x /= (float)1.3;
        y /= (float)1.3;
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        hexColor(hexColor);
        GL11.glLineWidth(2.0f);
        if (isOpen) {
            GL11.glBegin(1);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glVertex2d((double)(x + 4.0f), (double)(y + 3.0f));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2d((double)(x + 4.0f), (double)(y + 3.0f));
            GL11.glVertex2d((double)x, (double)(y + 6.0f));
            GL11.glEnd();
        }
        else {
            GL11.glBegin(1);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex2d((double)(x + 3.0f), (double)(y + 4.0f));
            GL11.glVertex2d((double)(x + 6.0f), (double)y);
            GL11.glEnd();
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void hexColor(final int hexColor) {
        final float red = (hexColor >> 16 & 0xFF) / 255.0f;
        final float green = (hexColor >> 8 & 0xFF) / 255.0f;
        final float blue = (hexColor & 0xFF) / 255.0f;
        final float alpha = (hexColor >> 24 & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
}
