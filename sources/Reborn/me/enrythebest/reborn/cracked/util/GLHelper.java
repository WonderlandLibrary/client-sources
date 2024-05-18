package me.enrythebest.reborn.cracked.util;

import org.lwjgl.opengl.*;
import net.minecraft.src.*;

public class GLHelper
{
    protected static float zLevel;
    
    static {
        GLHelper.zLevel = 0.0f;
    }
    
    public static void drawLines(final AxisAlignedBB var0) {
        GL11.glBegin(1);
        GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
        GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
        GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
        GL11.glVertex3d(var0.maxX, var0.maxY, var0.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
        GL11.glVertex3d(var0.maxX, var0.maxY, var0.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(var0.minX, var0.maxY, var0.minZ);
        GL11.glVertex3d(var0.minX, var0.minY, var0.maxZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(var0.minX, var0.minY, var0.minZ);
        GL11.glVertex3d(var0.maxX, var0.minY, var0.maxZ);
        GL11.glEnd();
    }
    
    public static void drawBorderedRect(final int var0, final int var1, final int var2, final int var3, final float var4, final int var5) {
        final float var6 = (var5 >> 24 & 0xFF) / 255.0f;
        final float var7 = (var5 >> 16 & 0xFF) / 255.0f;
        final float var8 = (var5 >> 8 & 0xFF) / 255.0f;
        final float var9 = (var5 & 0xFF) / 255.0f;
        GL11.glLineWidth(var4);
        GL11.glColor4f(var7, var8, var9, var6);
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
    
    public static void drawOutlinedBoundingBox(final AxisAlignedBB var0) {
        final Tessellator var = Tessellator.instance;
        var.startDrawing(3);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.draw();
        var.startDrawing(3);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.draw();
        var.startDrawing(1);
        var.addVertex(var0.minX, var0.minY, var0.minZ);
        var.addVertex(var0.minX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.minZ);
        var.addVertex(var0.maxX, var0.maxY, var0.minZ);
        var.addVertex(var0.maxX, var0.minY, var0.maxZ);
        var.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        var.addVertex(var0.minX, var0.minY, var0.maxZ);
        var.addVertex(var0.minX, var0.maxY, var0.maxZ);
        var.draw();
    }
}
