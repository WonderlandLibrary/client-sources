package me.darkmagician6.morbid.util;

import org.lwjgl.opengl.*;

public class GLHelper
{
    protected static float zLevel;
    
    public static void drawLines(final aqx ax) {
        GL11.glBegin(1);
        GL11.glVertex3d(ax.d, ax.e, ax.f);
        GL11.glVertex3d(ax.a, ax.e, ax.c);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(ax.d, ax.b, ax.f);
        GL11.glVertex3d(ax.d, ax.e, ax.c);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(ax.a, ax.b, ax.f);
        GL11.glVertex3d(ax.d, ax.e, ax.f);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(ax.a, ax.b, ax.c);
        GL11.glVertex3d(ax.d, ax.e, ax.c);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(ax.a, ax.e, ax.c);
        GL11.glVertex3d(ax.a, ax.b, ax.f);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(ax.a, ax.b, ax.c);
        GL11.glVertex3d(ax.d, ax.b, ax.f);
        GL11.glEnd();
    }
    
    public static void drawBorderedRect(final int var0, final int var1, final int var2, final int var3, final float var4, final int var5) {
        final float alpha = (var5 >> 24 & 0xFF) / 255.0f;
        final float red = (var5 >> 16 & 0xFF) / 255.0f;
        final float green = (var5 >> 8 & 0xFF) / 255.0f;
        final float blue = (var5 & 0xFF) / 255.0f;
        GL11.glLineWidth(var4);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBegin(1);
        GL11.glVertex2d(var0, var1);
        GL11.glVertex2d(var0, var3);
        GL11.glVertex2d(var2, var3);
        GL11.glVertex2d(var2, var1);
        GL11.glVertex2d(var0, var1);
        GL11.glVertex2d(var2, var1);
        GL11.glVertex2d(var0, var3);
        GL11.glVertex2d(var2, var3);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
    }
    
    public static void drawOutlinedBoundingBox(final aqx par1AxisAlignedBB) {
        final bgd var2 = bgd.a;
        var2.b(3);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a();
        var2.b(3);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a();
        var2.b(1);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a();
    }
    
    static {
        GLHelper.zLevel = 0.0f;
    }
}
